package com.example.stocktkl.payload.response;

import com.example.stocktkl.model.enum_class.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private int statusCode;
    private String message;
    private Object data;




    public  MessageResponse buildResponse(StatusCode statusCode, String message) {
        return MessageResponse.builder()
                .statusCode(statusCode.code)
                .message(message)
                .build();
    }
}

