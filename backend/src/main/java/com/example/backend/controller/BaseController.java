package com.example.backend.controller;

import com.example.backend.dto.GlobalApiResponse;

public class BaseController {
    public GlobalApiResponse successResponse(String message, Object data) {
        return GlobalApiResponse
                .builder()
                .status(true)
                .message(message)
                .data(data)
                .build();
    }

    public GlobalApiResponse failureResponse(String message, Object data) {
        return GlobalApiResponse
                .builder()
                .status(false)
                .message(message)
                .data(data)
                .build();
    }
}
