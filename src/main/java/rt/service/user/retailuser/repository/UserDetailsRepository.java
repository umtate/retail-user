package rt.service.user.retailuser.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rt.service.user.retailuser.entity.UserDetailsEntity;

import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, UUID> {

    UserDetailsEntity findByUserUserId(UUID userId);
}
