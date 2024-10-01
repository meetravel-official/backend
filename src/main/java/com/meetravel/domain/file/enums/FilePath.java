package com.meetravel.domain.file.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FilePath {
    PROFILE("profiles");

    private final String path;
}
