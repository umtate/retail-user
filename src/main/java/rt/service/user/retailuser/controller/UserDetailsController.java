package rt.service.user.retailuser.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rt.service.user.retailuser.entity.AddressEntity;
import rt.service.user.retailuser.entity.UserDetailsEntity;
import rt.service.user.retailuser.model.UpdatePhoneNumberCommand;
import rt.service.user.retailuser.service.api.UserDetailsServiceApi;
import rt.service.user.retailuser.service.api.UserVerificationServiceApi;

import java.util.UUID;

@RestController
@RequestMapping("/{userId}/details")
@Validated
public class UserDetailsController {

    private final UserDetailsServiceApi service;
    private final UserVerificationServiceApi verificationApi;

    public UserDetailsController(UserDetailsServiceApi service, UserVerificationServiceApi verificationApi) {
        this.service = service;
        this.verificationApi = verificationApi;
    }

    @PostMapping(
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> addUserDetails(@PathVariable("userId") UUID userId, @RequestBody UserDetailsEntity userDetails) {

        if (!verificationApi.checkUserExistsById(userId))
            return ResponseEntity.status(404).body("User not found");

        service.addDetails(userDetails, userId);

        return ResponseEntity.ok("Details created");
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDetailsEntity> getUserDetails(@PathVariable("userId") UUID userId) {

        var result = service.getDetails(userId);

        return ResponseEntity.status(200).body(result);
    }

    @PutMapping(
            path = "/address",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> updateAddressDetails(@RequestBody AddressEntity address, @PathVariable("userId") UUID userId) {

        if (!verificationApi.checkUserExistsById(userId))
            return ResponseEntity.status(404).body("User not found");

        service.updateAddressDetails(address, userId);

        return ResponseEntity.ok("Address updated success");
    }

    @PutMapping(
            path = "/phone",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> updatePhoneNumberDetails(@RequestBody @Valid UpdatePhoneNumberCommand phoneNumber,
                                                           @PathVariable("userId") UUID userId) {

        if (!verificationApi.checkUserExistsById(userId))
            return ResponseEntity.status(404).body("User not found");

        service.updatePhoneNumberDetails(phoneNumber, userId);

        return ResponseEntity.ok("Phone number updated success");
    }
}
