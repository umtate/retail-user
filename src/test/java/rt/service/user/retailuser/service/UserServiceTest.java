package rt.service.user.retailuser.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rt.service.user.retailuser.entity.UserEntity;
import rt.service.user.retailuser.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void whenCreateUser_verifyRepositoryCall() {
        var user = UserEntity.builder().userEmail("lKQp0@example.com").userName("test").build();

        service.createUser(user);

        verify(repository, times(1)).save(user);
    }

    @Test
    void whenCreateUser_thenReturnUserEntity() {

        var user = UserEntity.builder().userEmail("lKQp0@example.com").userName("test").build();

        when(repository.save(user)).thenReturn(UserEntity.builder().userId(UUID.randomUUID())
                .userName("test").userEmail("lKQp0@example.com").build());

        var result = service.createUser(user);

        assertNotNull(result);
        assertInstanceOf(UserEntity.class, result);
    }

    @Test
    void whenListUser_thenReturnUsers() {

        when(repository.findAll()).thenReturn(List.of(mock(UserEntity.class)));

        var result = service.getAllUsers();

        assertNotNull(result);
    }

    @Test
    void whenListUser_callRepository() {

        service.getAllUsers();

        verify(repository, times(1)).findAll();
    }


    @Test
    void whenGetUser_thenReturnUsers() {

        when(repository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(mock(UserEntity.class)));

        var result = service.getUser(UUID.randomUUID());

        assertNotNull(result);
    }

    @Test
    void whenGetUser_callRepository() {

        service.getUser(UUID.randomUUID());

        verify(repository, times(1)).findById(any(UUID.class));
    }

}
