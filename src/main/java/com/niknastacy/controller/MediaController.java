package com.niknastacy.controller;

import com.niknastacy.dto.FileUploadResponse;
import com.niknastacy.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/bff/media")
@RequiredArgsConstructor
public class MediaController {

    private final FileStorageService fileStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> uploadFile(
            Principal principal,
            @RequestParam("file") MultipartFile file
    ) {
        String userId = principal.getName();
        FileUploadResponse response = fileStorageService.uploadUserFile(userId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
