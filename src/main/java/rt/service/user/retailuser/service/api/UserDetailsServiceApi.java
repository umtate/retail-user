package rt.service.user.retailuser.service.api;

import rt.service.user.retailuser.entity.AddressEntity;
import rt.service.user.retailuser.entity.UserDetailsEntity;
import rt.service.user.retailuser.model.UpdatePhoneNumberCommand;

import java.util.UUID;

public interface UserDetailsServiceApi {

    UserDetailsEntity addDetails(UserDetailsEntity details, UUID userId);

    UserDetailsEntity updateDetails(UserDetailsEntity details);

    UserDetailsEntity getDetails(UUID userId);

    void updateAddressDetails(AddressEntity address, UUID userId);

    void updatePhoneNumberDetails(UpdatePhoneNumberCommand phoneNumber, UUID userId);

}
