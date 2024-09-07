package rt.service.user.retailuser.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rt.service.user.retailuser.entity.AddressEntity;
import rt.service.user.retailuser.entity.UserDetailsEntity;
import rt.service.user.retailuser.entity.UserEntity;
import rt.service.user.retailuser.model.UpdatePhoneNumberCommand;
import rt.service.user.retailuser.repository.UserDetailsRepository;
import rt.service.user.retailuser.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @Mock
    private UserDetailsRepository repository;

    @InjectMocks
    private UserDetailsService service;


    @Test
    void whenListUserDetails_thenCallRepository() {
        var userDetails = UserDetailsEntity.builder().build();
        var id = UUID.randomUUID();
        when(repository.save(any(UserDetailsEntity.class))).thenReturn(mock(UserDetailsEntity.class));

        service.addDetails(userDetails, id);

        verify(repository, times(1)).save(any(UserDetailsEntity.class));

    }


    @Test
    void whenCreateUserDetails_thenReturnUserDetailsEntity() {

        var userDetails = UserDetailsEntity.builder().build();
        var id = UUID.randomUUID();
        when(repository.save(any(UserDetailsEntity.class))).thenReturn(mock(UserDetailsEntity.class));

        var result = service.addDetails(userDetails, id);

        assertNotNull(result);
        assertInstanceOf(UserDetailsEntity.class, result);
    }


    @Test
    void whenGetUserDetails_thenReturnUsersDetails() {

        when(repository.findByUserUserId(any(UUID.class))).thenReturn(mock(UserDetailsEntity.class));

        var result = service.getDetails(UUID.randomUUID());

        assertNotNull(result);
    }

    @Test
    void whenGetUserDetails_callRepository() {

        service.getDetails(UUID.randomUUID());

        verify(repository, times(1)).findByUserUserId(any(UUID.class));
    }

    @Test
    void whenUpdateAddress_callSaveMethodRepository() {
        var id = UUID.randomUUID();

        var address = AddressEntity.builder().addressId(UUID.randomUUID()).build();

        when(repository.findByUserUserId(any(UUID.class)))
                .thenReturn(UserDetailsEntity.builder().address(address).build());

        service.updateAddressDetails(address, id);

        InOrder inOrder = inOrder(repository);

        inOrder.verify(repository, times(1))
                .findByUserUserId(any(UUID.class));
        verify(repository, times(1))
                .save(any(UserDetailsEntity.class));


    }

    @Test
    void whenUpdatePhoneNumber_callSaveMethodRepository() {
        var id = UUID.randomUUID();
        var phoneNumber = UpdatePhoneNumberCommand.builder().phoneNumber("1234567890").build();
        when(repository.findByUserUserId(any(UUID.class)))
                .thenReturn(UserDetailsEntity.builder().build());

        service.updatePhoneNumberDetails(phoneNumber, id);

        InOrder inOrder = inOrder(repository);

        inOrder.verify(repository, times(1))
                .findByUserUserId(any(UUID.class));
        verify(repository, times(1))
                .save(any(UserDetailsEntity.class));


    }

}
