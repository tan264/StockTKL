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
public class WatchListResponse {
    private int statusCode;
    private String message;

}


