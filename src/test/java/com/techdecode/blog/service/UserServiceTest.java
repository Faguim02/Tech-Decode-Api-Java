package com.techdecode.blog.service;

import com.techdecode.blog.dto.UserDto;
import com.techdecode.blog.models.UserModel;
import com.techdecode.blog.models.roles.UserRole;
import com.techdecode.blog.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@DisplayName("test: user services")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @DisplayName("method test: findAllUser")
    @Nested
    class FindAllUser {
        @DisplayName("should return list users")
        @Test
        void shouldReturnUsers() {
            //data
            List<UserModel> userModels = List.of(
                    new UserModel()
            );
            //mock
            Mockito.when(userRepository.findByUserRole(Mockito.any(UserRole.class))).thenReturn(userModels);
            List<UserDto> users = userService.findAllUser();
            //assertions
            Assertions.assertEquals(1, users.size());
        }
    }
}
