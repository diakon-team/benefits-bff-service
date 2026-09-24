package com.niknastacy.service;

import com.niknastacy.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final S3Client s3Client;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String endpoint;

    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;

    public FileUploadResponse uploadUserFile(String userId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Файл не должен быть пустым");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Размер файла превышает 20 МБ");
        }

        String fileId = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename() != null ? file.getOriginalFilename() : "file";
        String sanitizedFilename = originalFilename.replaceAll("[^a-zA-Z0-9а-яА-ЯёЁ.\\-_]", "_");
        String s3Key = String.format("users/%s/%s_%s", userId, fileId, sanitizedFilename);

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        } catch (IOException e) {
            log.error("Ошибка при чтении файла", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось обработать файл");
        } catch (Exception e) {
            log.error("Ошибка загрузки в S3", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка загрузки файла в хранилище");
        }

        String fileUrl = String.format("%s/%s/%s", endpoint, bucketName, s3Key);

        return FileUploadResponse.builder()
                .fileId(fileId)
                .fileUrl(fileUrl)
                .originalName(originalFilename)
                .build();
    }
}
