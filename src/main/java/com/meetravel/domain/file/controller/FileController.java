package com.meetravel.domain.file.controller;

import com.meetravel.domain.file.dto.FileUploadResponse;
import com.meetravel.domain.file.enums.FilePath;
import com.meetravel.domain.file.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final S3Service s3Service;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam FilePath filePath,
            @RequestPart MultipartFile file
    ) {
        FileUploadResponse response = s3Service.uploadFile(filePath, file);

        return ResponseEntity.ok(response);
    }
}
