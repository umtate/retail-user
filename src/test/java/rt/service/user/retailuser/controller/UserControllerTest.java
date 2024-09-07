package rt.service.user.retailuser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RestController;
import rt.service.user.retailuser.entity.UserEntity;
import rt.service.user.retailuser.service.api.UserServiceApi;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserServiceApi service;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void isAnnotatedWithRestController() {

        assertTrue(UserController.class.isAnnotationPresent(RestController.class));
    }

    @Test
    void whenAddingUser_andUserNameIsNull_thenThrow() throws Exception {
        var user = UserEntity
                .builder()
                .userEmail("lKQp0@example.com")
                .build();

        var serialized = objectMapper.writeValueAsString(user);

        var request = MockMvcRequestBuilders.post("/users")
                .contentType("application/json")
                .content(serialized);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenAddingUser_andUserEmailIsNull_thenThrow() throws Exception {
        var user = UserEntity
                .builder()
                .userEmail(null)
                .userName("test")
                .build();

        var serialized = objectMapper.writeValueAsString(user);

        var request = MockMvcRequestBuilders.post("/users")
                .contentType("application/json")
                .content(serialized);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenAddingUser_andAllInputsAreValid_thenCreated() throws Exception {

        var user = UserEntity
                .builder()
                .userEmail("lKQp0@example.com")
                .userName("test")
                .build();

        var serialized = objectMapper.writeValueAsString(user);

        var request = MockMvcRequestBuilders.post("/users")
                .contentType("application/json")
                .content(serialized);

        when(service.createUser(any(UserEntity.class))).thenReturn(UserEntity.builder().userId(UUID.randomUUID()).build());

        var result = mockMvc.perform(request);

        result.andExpect(status().isCreated()).andReturn();
    }

    @Test
    void whenAddingUser_andUserAlreadyExists_thenReturnClientError() throws Exception {
        var user = UserEntity
                .builder()
                .userEmail("lKQp0@example.com")
                .userName("test")
                .build();

        var serialized = objectMapper.writeValueAsString(user);

        var request = MockMvcRequestBuilders.post("/users")
                .contentType("application/json")
                .content(serialized);

        when(service.checkUserExists(any(UserEntity.class))).thenReturn(true);

        var result = mockMvc.perform(request);

        result.andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    void whenGetUsers_thenReturnListOfUsers() throws Exception {

        var request = MockMvcRequestBuilders.get("/users")
                .contentType("application/json");

        when(service.getAllUsers()).thenReturn(List.of(mock(UserEntity.class)));

        var result = mockMvc.perform(request);

        result.andExpect(status().isOk()).andReturn();
    }

    @Test
    void whenGetUser_thenReturnUser() throws Exception {

        var id = UUID.randomUUID().toString();

        var request = MockMvcRequestBuilders.get("/users/" + id)
                .contentType("application/json");

        when(service.getUser(any(UUID.class))).thenReturn(mock(UserEntity.class));

        var result = mockMvc.perform(request);

        result.andExpect(status().isOk()).andReturn();
    }
}



