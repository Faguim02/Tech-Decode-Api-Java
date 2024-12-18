package com.techdecode.blog.service;

import com.techdecode.blog.dto.PostDto;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.models.exceptions.ConflictException;
import com.techdecode.blog.models.exceptions.NotFoundException;
import com.techdecode.blog.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostDto createPost(PostDto postDto) {

        if (this.postRepository.findByTitle(postDto.title()) != null) {
            throw new ConflictException("essa notícia já existe");
        }

        PostModel postModel = new PostModel();
        BeanUtils.copyProperties(postDto, postModel);

        PostModel postModelRes = this.postRepository.save(postModel);
        return new PostDto(postModelRes.getId(), postModelRes.getTitle(), postModelRes.getBannerUrl(), postModelRes.getDescription(), postModelRes.getFont(), postModelRes.getDate_at(), null, null);
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
}
