package rt.service.user.retailuser.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rt.service.user.retailuser.entity.UserEntity;
import rt.service.user.retailuser.service.api.UserServiceApi;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
@Validated
public class UserController {

    private final UserServiceApi service;

    public UserController(UserServiceApi service) {
        this.service = service;
    }

    @PostMapping( consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UUID> createUser(@RequestBody @Valid UserEntity user ) {

         if(service.checkUserExists(user))
             return ResponseEntity.status(HttpStatusCode.valueOf(409)).build();

        var result = service.createUser(user);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(result.getUserId());
    }

}
