package rt.service.user.retailuser.service.api;

import rt.service.user.retailuser.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserServiceApi {

    UserEntity createUser(UserEntity user);

    UserEntity getUser(UUID userId);

    List<UserEntity> getAllUsers();
}
