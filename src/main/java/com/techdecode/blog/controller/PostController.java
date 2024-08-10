package com.techdecode.blog.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.techdecode.blog.dtos.PostDto;
import com.techdecode.blog.dtos.PostsDto;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    private AmazonS3 amazonS3;

    @PostMapping("post")
    public ResponseEntity<Object> createPost(
            @RequestParam("bannerImage") @Valid MultipartFile bannerImage,
            @RequestParam("title") @Valid String title,
            @RequestParam("font") @Valid String font,
            @RequestParam("description") @Valid String description
            ) {

        try {

            String key = UUID.randomUUID() + bannerImage.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bannerImage.getSize());

            PutObjectRequest request = new PutObjectRequest("techdecode", key, bannerImage.getInputStream(), metadata);
            request.withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(request);

            var postModel = new PostModel();

            postModel.setTitle(title);
            postModel.setFont(font);
            postModel.setDescription(description);

            String imageUrl = "https://techdecode.s3.amazonaws.com/" + key;
            postModel.setBannerUrl(imageUrl);

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", new Locale("pt", "BR"));
            String date_at = localDate.format(formatter);
            postModel.setDate_at(date_at);

            return ResponseEntity.status(HttpStatus.CREATED).body(postRepository.save(postModel));

        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao fazer upload da imagem");
        }
    }

    @GetMapping("post")
    public ResponseEntity<List<PostsDto>> findAllPosts() {

        List<PostModel> postModels = postRepository.findAll();
        List<PostsDto> postsDto = postModels.stream()
                .map(post -> new PostsDto(post.getId(), post.getTitle(), post.getBannerUrl(), post.getDate_at()))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(postsDto);
    }

    @GetMapping("post/{id}")
    public ResponseEntity<Object> findOnePost(@PathVariable UUID id) {
        Optional<PostModel> post0 = postRepository.findById(id);

        if(post0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Noticía não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(post0);
    }

    @PutMapping("post/{id}")
    public ResponseEntity<Object> editPost(@PathVariable UUID id, @RequestBody @Valid PostDto postDto) {
        Optional<PostModel> post0 = postRepository.findById(id);

        if(post0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Noticía não encontrada");
        }
        var postModel = post0.get();
        BeanUtils.copyProperties(postDto, postModel);

        return ResponseEntity.status(HttpStatus.OK).body(postModel);
    }

    @DeleteMapping("post/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable UUID id) {
        Optional<PostModel> post0 = postRepository.findById(id);

        if(post0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Noticía não encontrada");
        }

        postRepository.delete(post0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Noticía deletada");
    }

    @GetMapping("post/search/{search}")
    public ResponseEntity<Object> searchPosts(@PathVariable @Valid String search) {
        List<PostModel> postModels = postRepository.findByTitleContaining(search);

        List<PostsDto> postsDto = postModels.stream()
                .map(post -> new PostsDto(post.getId(), post.getTitle(), post.getBannerUrl(), post.getDate_at()))
                .collect(Collectors.toList());

        if(postsDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma notícia encontrada");
        }

        return ResponseEntity.status(HttpStatus.OK).body(postsDto);
    }

}
