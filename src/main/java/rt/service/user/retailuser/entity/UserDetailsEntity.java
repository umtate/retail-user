package rt.service.user.retailuser.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "user_detail")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressEntity address;

    private String phoneNumber;

}
