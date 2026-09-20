package com.niknastacy.controller;

import com.niknastacy.dto.UserProfileResponse;
import com.niknastacy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/bff/user")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserService userService;

    @GetMapping("/profile")
    ResponseEntity<UserProfileResponse> getProfile(Principal principal) {
        String userId = principal.getName();
        UserProfileResponse profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(profile);
    }

}
