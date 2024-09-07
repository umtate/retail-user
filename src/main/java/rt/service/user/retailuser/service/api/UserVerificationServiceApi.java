package rt.service.user.retailuser.service.api;

import rt.service.user.retailuser.entity.UserEntity;

import java.util.UUID;

public interface UserVerificationServiceApi {

    Boolean checkUserExistsByEmail(String userEmail);

    Boolean checkUserExistsById(UUID userId);

}
