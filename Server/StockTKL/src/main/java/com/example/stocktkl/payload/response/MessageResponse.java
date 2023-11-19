package com.example.stocktkl.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private int statusCode;  // Trường status code
    private String message;
    private Object data;

    public enum StatusCode {
        OK(200, "OK"),
        CREATED(201, "Created"),
        ACCEPTED(202, "Accepted"),
        BAD_REQUEST(400, "Bad Request"),
        UNAUTHORIZED(401, "Unauthorized"),
        FORBIDDEN(403, "Forbidden"),
        NOT_FOUND(404, "Not Found"),
        INTERNAL_SERVER_ERROR(500, "Internal Server Error");

        private final int code;

        StatusCode(int code, String reason) {
            this.code = code;
        }
    }

    public static MessageResponse buildResponse(StatusCode statusCode, String message) {
        return MessageResponse.builder()
                .statusCode(statusCode.code)
                .message(message)
                .build();
    }
}

