package rt.service.user.retailuser.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rt.service.user.retailuser.entity.UserEntity;
import rt.service.user.retailuser.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserVerificationServiceTest {

    private static final String EMAIL = "lKQp0@example.com";
    private static final UUID USER_ID = UUID.randomUUID();

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserVerificationService service;

    @Test
    void whenCheckUserExistsByEmail_andUserExists_thenReturnTrue() {

        when(repository.findByUserEmail(any(String.class))).thenReturn(mock(UserEntity.class));

        var result = service.checkUserExistsByEmail(EMAIL);

        assertTrue(result);
    }

    @Test
    void whenCheckUserExistsByEmail_andDoesNotUserExists_thenReturnFalse() {

        when(repository.findByUserEmail(any(String.class))).thenReturn(null);

        var result = service.checkUserExistsByEmail(EMAIL);

        assertFalse(result);
    }

    @Test
    void whenCheckUserExistsByEmail_callRepository() {

        when(repository.findByUserEmail(any(String.class))).thenReturn(null);

        service.checkUserExistsByEmail(EMAIL);

        verify(repository, times(1)).findByUserEmail(any(String.class));
    }


    @Test
    void whenCheckUserExistsById_andUserExists_thenReturnTrue() {

        when(repository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(mock(UserEntity.class)));

        var result = service.checkUserExistsById(USER_ID);

        assertTrue(result);
    }

    @Test
    void whenCheckUserExistsById_andDoesNotUserExists_thenReturnFalse() {

        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var result = service.checkUserExistsById(USER_ID);

        assertFalse(result);
    }

    @Test
    void whenCheckUserExistsById_callRepository() {

        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        service.checkUserExistsById(USER_ID);

        verify(repository, times(1))
                .findById(any(UUID.class));
    }
}
