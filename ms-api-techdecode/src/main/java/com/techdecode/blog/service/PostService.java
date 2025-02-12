package com.techdecode.blog.service;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.dto.PostDto;
import com.techdecode.blog.dto.PostSmallDto;
import com.techdecode.blog.models.CategoryModel;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.models.exceptions.ConflictException;
import com.techdecode.blog.models.exceptions.NotFoundException;
import com.techdecode.blog.repository.CategoryRepository;
import com.techdecode.blog.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired S3Service s3Service;

    public PostDto createPost(PostDto postDto, MultipartFile photo) throws IOException {

        if (this.postRepository.findByTitle(postDto.title()) != null) {
            throw new ConflictException("essa notícia já existe");
        }

        String bucketName = "tech-decode";
        String key = "post/"+postDto.title();

        PostModel postModel = new PostModel();
        postModel.setDate_at(this.generateDateActual());
        BeanUtils.copyProperties(postDto, postModel);

        try {
            this.s3Service.uploadFile(bucketName, key, photo);
            String bannerUrl = this.s3Service.getUrl(bucketName, key);
            postModel.setBannerUrl(bannerUrl);
            PostModel postModelRes = this.postRepository.save(postModel);

            return new PostDto(postModelRes.getId(), postModelRes.getTitle(), postModelRes.getBannerUrl(), postModelRes.getDescription(), postModelRes.getFont(), postModelRes.getDate_at(), null, null);
        } catch (Exception e) {
            this.s3Service.deleteFile("post/"+postDto.title(),"tech-decode");
            throw new RuntimeException(e);
        }
    }

    public List<PostDto> findAllPost() {

        List<PostModel> postModels = this.postRepository.findAll();

        if (postModels.isEmpty()) {
            throw new NotFoundException("a lista de postagem está vazia");
        }

        return postModels.stream()
                .map(postModel -> new PostDto(postModel.getId(), postModel.getTitle(), postModel.getBannerUrl(), postModel.getDescription(), postModel.getFont(), postModel.getDate_at(), null, null))
                .toList();
    }

    public PostDto findPostById(UUID id) {
        Optional<PostModel> postModelOptional = this.postRepository.findById(id);

        if (postModelOptional.isEmpty()) {
            throw new NotFoundException("está postagem não existe mais");
        }

        PostModel postModel = postModelOptional.get();

        return new PostDto(
                postModel.getId(), postModel.getTitle(), postModel.getBannerUrl(), postModel.getDescription(), postModel.getFont(), postModel.getDate_at(), postModel.getComments(), postModel.getCategory()
        );
    }

    @Transactional
    public CategoryDto findPostByCategory(UUID id) {

        Optional<CategoryModel> categoryModelOptional = this.categoryRepository.findById(id);

        if (categoryModelOptional.isEmpty()) {
            throw new NotFoundException("nenhuma postagem associada a esta categoria");
        }

        CategoryModel categoryModel = categoryModelOptional.get();

        List<PostSmallDto> postSmallDtos = categoryModel.getPostModels().stream()
                .map(postModel -> new PostSmallDto(postModel.getId(), postModel.getTitle(), postModel.getBannerUrl(), postModel.getDate_at()))
                .toList();

        return new CategoryDto(categoryModel.getId(), categoryModel.getTitle(), postSmallDtos);
    }

    @Transactional
    public List<PostDto> searchPost(String search) {
        List<PostModel> postModels = this.postRepository.findByTitleContaining(search);
        return postModels.stream()
                .map(postModel -> new PostDto(postModel.getId(), postModel.getTitle(), postModel.getBannerUrl(), null, null, postModel.getDate_at(), null, null))
                .toList();
    }

    public PostDto updatePost(UUID id, PostDto postDto, MultipartFile photo) throws IOException {
        this.deletePost(id);
        return this.createPost(postDto, photo);
    }

    public String deletePost(UUID id) {

        Optional<PostModel> postModel = this.postRepository.findById(id);

        if (postModel.isEmpty()) {
            throw new NotFoundException("postagem inexistente");
        }

        if (!postModel.get().getComments().isEmpty()) {
            throw new ConflictException("Não é possivel deletar, pois essa noticia tem comentarios");
        }

        this.s3Service.deleteFile("post/"+postModel.get().getTitle(),"tech-decode");
        this.postRepository.deleteById(id);

        return "Postagem deletada";
    }

    private String generateDateActual() {
        // Obter a data atual
        LocalDate now = LocalDate.now();

        // Configurar o formato desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", new Locale("pt", "BR"));

        // Formatar a data
        return now.format(formatter);
    }
}
