package com.meetravel.global.utils;

import com.github.f4b6a3.uuid.alt.GUID;

import java.util.UUID;

public class UUIDGenerator {
    public static UUID newUUID() {
        return GUID.v7().toUUID();
    }

    protected UUIDGenerator() {}
}
