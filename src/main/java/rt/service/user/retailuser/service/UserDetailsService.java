package rt.service.user.retailuser.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import rt.service.user.retailuser.entity.AddressEntity;
import rt.service.user.retailuser.entity.UserDetailsEntity;
import rt.service.user.retailuser.entity.UserEntity;
import rt.service.user.retailuser.model.UpdatePhoneNumberCommand;
import rt.service.user.retailuser.repository.UserDetailsRepository;
import rt.service.user.retailuser.service.api.UserDetailsServiceApi;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserDetailsService implements UserDetailsServiceApi {

    private final UserDetailsRepository repository;

    @Override
    public UserDetailsEntity addDetails(UserDetailsEntity details, UUID userId) {

        var userDetails = new UserDetailsEntity();
        BeanUtils.copyProperties(details, userDetails);
        userDetails.setUser(UserEntity.builder().userId(userId).build());
        return repository.save(userDetails);
    }

    @Override
    public UserDetailsEntity updateDetails(UserDetailsEntity details) {
        return null;
    }

    @Override
    public UserDetailsEntity getDetails(UUID userId) {
        return repository.findByUserUserId(userId);
    }

    @Override
    public void updateAddressDetails(AddressEntity address, UUID userId) {
        var details = repository.findByUserUserId(userId);
        var addressId = details.getAddress().getAddressId();
        address.setAddressId(addressId);
        details.setAddress(address);
        repository.save(details);
    }

    @Override
    public void updatePhoneNumberDetails(UpdatePhoneNumberCommand command, UUID userId) {
        var details = repository.findByUserUserId(userId);
        details.setPhoneNumber(command.getPhoneNumber());
        repository.save(details);
    }
}
