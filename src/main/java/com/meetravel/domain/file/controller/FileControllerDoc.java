package com.meetravel.domain.file.controller;

import com.meetravel.domain.file.dto.FileUploadResponse;
import com.meetravel.domain.file.enums.FilePath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings(value = "unused")
@Tag(name = "채팅방")
public interface FileControllerDoc {
    @Operation(
            summary = "파일 업로드",
            description = """
                    [operation]
                    - 파일을 업로드 합니다.
                    """
    )
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<FileUploadResponse> uploadFile(
            @Parameter(description = "파일 저장 경로")
            @RequestParam FilePath filePath,
            @Parameter(description = "파일")
            @RequestPart MultipartFile file
    );
}
