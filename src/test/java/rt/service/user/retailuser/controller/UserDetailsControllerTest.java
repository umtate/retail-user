package rt.service.user.retailuser.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RestController;
import rt.service.user.retailuser.entity.AddressEntity;
import rt.service.user.retailuser.entity.UserDetailsEntity;
import rt.service.user.retailuser.entity.UserEntity;
import rt.service.user.retailuser.model.UpdatePhoneNumberCommand;
import rt.service.user.retailuser.service.api.UserDetailsServiceApi;
import rt.service.user.retailuser.service.api.UserServiceApi;
import rt.service.user.retailuser.service.api.UserVerificationServiceApi;

import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserDetailsController.class)
public class UserDetailsControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserDetailsServiceApi service;

    @MockBean
    UserVerificationServiceApi verificationApi;

    @MockBean
    UserServiceApi userService;

    private final Supplier<AddressEntity> addressSupplier = () -> AddressEntity.builder()
            .addressLine1("123 Main Street")
            .addressLine2("Apt 1")
            .city("New York")
            .state("NY")
            .postalCode("10001")
            .build();

    private final Supplier<UserDetailsEntity> userDetailsSupplier = () -> UserDetailsEntity
            .builder()
            .address(addressSupplier.get())
            .phoneNumber("1234567890")
            .build();


    @Test
    void isAnnotatedWithRestController() {
        assertTrue(UserDetailsController.class.isAnnotationPresent(RestController.class));
    }

    @Test
    void whenAddUserDetails_andUserDoesNotExist_thenBadRequest() throws Exception {
        var serialized = objectMapper.writeValueAsString(userDetailsSupplier.get());

        when(verificationApi.checkUserExistsById(any(UUID.class)))
                .thenReturn(false);

        var request = MockMvcRequestBuilders.post("/{userId}/details", UUID.randomUUID())
                .contentType("application/json")
                .content(serialized);

        var result = mockMvc.perform(request);

        result.andExpect(status().is4xxClientError()).andReturn();

    }


    @Test
    void whenAddUserDetails_andUserExists_thenCreated() throws Exception {
        var serialized = objectMapper.writeValueAsString(userDetailsSupplier.get());

        when(verificationApi.checkUserExistsById(any(UUID.class)))
                .thenReturn(true);

        var request = MockMvcRequestBuilders.post("/{userId}/details", UUID.randomUUID())
                .contentType("application/json")
                .content(serialized);

        var result = mockMvc.perform(request);

        result.andExpect(status().isOk()).andReturn();
    }

    @Test
    void whenGetUserDetails_thenReturnUserDetails() throws Exception {

        var id = UUID.randomUUID().toString();

        var request = MockMvcRequestBuilders.get("/{userId}/details", id)
                .contentType("application/json");

        when(service.getDetails(any(UUID.class))).thenReturn(mock(UserDetailsEntity.class));

        var result = mockMvc.perform(request);

        result.andExpect(status().isOk()).andReturn();
    }


    @Test
    void whenUpdateUserAddress_andUserDoesNotExist_thenBadRequest() throws Exception {
        var serialized = objectMapper.writeValueAsString(addressSupplier.get());

        when(verificationApi.checkUserExistsById(any(UUID.class)))
                .thenReturn(false);

        var request = MockMvcRequestBuilders.put("/{userId}/details/address", UUID.randomUUID())
                .contentType("application/json")
                .content(serialized);

        var result = mockMvc.perform(request);

        result.andExpect(status().is4xxClientError()).andReturn();

    }

    @Test
    void whenUpdateAddress_andUserExists_thenUpdate() throws Exception {
        var serialized = objectMapper.writeValueAsString(addressSupplier.get());

        when(verificationApi.checkUserExistsById(any(UUID.class)))
                .thenReturn(true);

        var request = MockMvcRequestBuilders.put("/{userId}/details/address", UUID.randomUUID())
                .contentType("application/json")
                .content(serialized);

        var result = mockMvc.perform(request);

        result.andExpect(status().isOk()).andReturn();
    }


    @Test
    void whenUpdatePhoneNumber_andUserDoesNotExist_thenBadRequest() throws Exception {
        var serialized = objectMapper.writeValueAsString(UpdatePhoneNumberCommand.builder().phoneNumber("1234567890").build());

        when(verificationApi.checkUserExistsById(any(UUID.class)))
                .thenReturn(false);

        var request = MockMvcRequestBuilders.put("/{userId}/details/phone", UUID.randomUUID())
                .contentType("application/json")
                .content(serialized);

        var result = mockMvc.perform(request);

        result.andExpect(status().is4xxClientError()).andReturn();

    }

    @Test
    void whenUpdatePhoneNumber_andUserExists_thenUpdate() throws Exception {
        var serialized = objectMapper.writeValueAsString(UpdatePhoneNumberCommand.builder().phoneNumber("1234567890").build());

        when(verificationApi.checkUserExistsById(any(UUID.class)))
                .thenReturn(true);

        var request = MockMvcRequestBuilders.put("/{userId}/details/phone", UUID.randomUUID())
                .contentType("application/json")
                .content(serialized);

        var result = mockMvc.perform(request);

        result.andExpect(status().isOk()).andReturn();
    }


}
