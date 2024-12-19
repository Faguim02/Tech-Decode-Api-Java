package com.techdecode.blog.service;

import com.techdecode.blog.dto.CommentDto;
import com.techdecode.blog.models.CommentModel;
import com.techdecode.blog.models.exceptions.ForbiddenException;
import com.techdecode.blog.repository.CommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    CommentDto createComment(CommentDto commentDto) {
        CommentModel commentModel = new CommentModel();

        BeanUtils.copyProperties(commentDto, commentModel);

        CommentModel commentModelRes = this.commentRepository.save(commentModel);

        return new CommentDto(commentModelRes.getId(), commentDto.comment(), commentDto.date_at(), commentDto.user(), commentDto.post());
    }

    String deleteComment(UUID user_id, UUID comment_id) {
        Optional<CommentModel> commentModelOptional = this.commentRepository.findById(comment_id);
        CommentModel commentModel = commentModelOptional.get();

        if (!(commentModel.getUser().getId() == user_id)) {
            throw new ForbiddenException("você não foi autorizado para isso");
        }

        this.commentRepository.delete(commentModel);

        return "comentario deletado";
    }
}
