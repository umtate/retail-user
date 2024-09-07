package rt.service.user.retailuser.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rt.service.user.retailuser.entity.UserEntity;
import rt.service.user.retailuser.service.api.UserServiceApi;
import rt.service.user.retailuser.service.api.UserVerificationServiceApi;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
@Validated
public class UserController {

    private final UserServiceApi service;
    public final UserVerificationServiceApi verification;

    public UserController(UserServiceApi service, UserVerificationServiceApi verification) {
        this.service = service;
        this.verification = verification;
    }

    @PostMapping( consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> createUser(@RequestBody @Valid UserEntity user ) {

         if(verification.checkUserExistsByEmail(user.getUserEmail()))
             return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("User already exists");

        var result = service.createUser(user);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(String.valueOf(result.getUserId()));
    }

    @GetMapping( produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserEntity>> listUsers() {

        var result = service.getAllUsers();

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserEntity> getUser(@PathVariable("userId") UUID userId) {

        var result = service.getUser(userId);

        return ResponseEntity.ok(result);
    }

}
