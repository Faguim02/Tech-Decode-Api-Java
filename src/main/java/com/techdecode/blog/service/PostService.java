package com.techdecode.blog.service;

import com.techdecode.blog.dto.PostDto;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.models.exceptions.ConflictException;
import com.techdecode.blog.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
