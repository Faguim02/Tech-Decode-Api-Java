package com.techdecode.blog.service;

import com.techdecode.blog.dto.CommentDto;
import com.techdecode.blog.models.CommentModel;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.models.UserModel;
import com.techdecode.blog.models.exceptions.ForbiddenException;
import com.techdecode.blog.models.exceptions.NotFoundException;
import com.techdecode.blog.repository.CommentRepository;
import com.techdecode.blog.repository.PostRepository;
import com.techdecode.blog.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    public CommentDto createComment(CommentDto commentDto, String email) {
        CommentModel commentModel = new CommentModel();

        BeanUtils.copyProperties(commentDto, commentModel);

        UserModel userModel = (UserModel) this.userRepository.findByEmail(email);

        if (userModel == null) {
            throw new ForbiddenException("você não foi autorizado para isso");
        }

        commentModel.setUser(userModel);
        commentModel.setDate_at(this.generateDateActual());
        commentModel.setUsername(userModel.getName());

        CommentModel commentModelRes = this.commentRepository.save(commentModel);

        return new CommentDto(commentModelRes.getId(), commentModelRes.getUsername(), commentDto.comment(), commentDto.date_at(), commentDto.user(), commentDto.post());
    }

    public String deleteComment(String email, UUID comment_id) {
        Optional<CommentModel> commentModelOptional = this.commentRepository.findById(comment_id);

        if (commentModelOptional.isEmpty()) {
            throw new NotFoundException("comentario inexistente");
        }

        UserModel userModel = (UserModel) this.userRepository.findByEmail(email);

        if (!Objects.equals(userModel.getEmail(), email)) {
            throw new ForbiddenException("você não foi autorizado para isso");
        }

        this.commentRepository.delete(commentModelOptional.get());

        return "comentario deletado";
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
