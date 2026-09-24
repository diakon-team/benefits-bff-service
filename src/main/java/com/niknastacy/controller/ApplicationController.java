package com.niknastacy.controller;

import com.niknastacy.dto.ApplicationCardResponse;
import com.niknastacy.dto.SubmitApplicationRequest;
import com.niknastacy.dto.SubmitApplicationResponse;
import com.niknastacy.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bff/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/list")
    public ResponseEntity<List<ApplicationCardResponse>> getApplicationsList(Principal principal) {
        String userId = principal.getName();

        List<ApplicationCardResponse> applications = applicationService.getUserApplications(userId);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<SubmitApplicationResponse> submitApplication(
            @PathVariable("id") String id,
            @RequestBody SubmitApplicationRequest request,
            Principal principal
    ) {
        String userId = principal.getName();
        SubmitApplicationResponse response = applicationService.submitApplication(id, userId, request);
        return ResponseEntity.ok(response);
    }


}