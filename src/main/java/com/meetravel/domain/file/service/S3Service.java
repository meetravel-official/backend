package com.meetravel.domain.file.service;

import com.meetravel.domain.file.dto.FileUploadResponse;
import com.meetravel.domain.file.enums.FilePath;
import com.meetravel.global.cloud.s3.S3Properties;
import com.meetravel.global.constants.Urls;
import com.meetravel.global.utils.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;
    private final S3Properties s3Properties;

    @Transactional(readOnly = true)
    public FileUploadResponse uploadFile(FilePath filePath, MultipartFile file) {
        String fileName = filePath.getPath() + "/" + UUIDGenerator.newUUID();

        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(s3Properties.getBucket())
                            .key(fileName)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(
                            inputStream,
                            file.getSize()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new FileUploadResponse(Urls.BASE_CDN_URL + "/" + fileName);
    }
}
