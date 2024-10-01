package com.meetravel.domain.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class FileUploadResponse {
    @Schema(description = "업로드된 파일 URL")
    private final String fileUrl;

    public FileUploadResponse(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
