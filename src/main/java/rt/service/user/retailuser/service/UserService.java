package rt.service.user.retailuser.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rt.service.user.retailuser.entity.UserEntity;
import rt.service.user.retailuser.repository.UserRepository;
import rt.service.user.retailuser.service.api.UserServiceApi;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service
public class UserService implements UserServiceApi {

    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean checkUserExists(UserEntity user) {
        return false;
    }

    @Override
    public UserEntity getUser(UUID userId) {
        return null;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return List.of();
    }

    public UserEntity updateUser(UserEntity user) {
        return null;
    }
}
