package rt.service.user.retailuser.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rt.service.user.retailuser.repository.UserRepository;
import rt.service.user.retailuser.service.api.UserVerificationServiceApi;

import java.util.UUID;

@AllArgsConstructor
@Service
public class UserVerificationService implements UserVerificationServiceApi {

    private final UserRepository repository;

    @Override
    public Boolean checkUserExistsByEmail(String userEmail) {
        return repository.findByUserEmail(userEmail) != null;
    }

    @Override
    public Boolean checkUserExistsById(UUID userId) {
        return repository.findById(userId).isPresent();
    }
}
