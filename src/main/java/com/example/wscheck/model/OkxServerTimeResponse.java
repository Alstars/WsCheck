package com.example.wscheck.model;

import lombok.Data;

import java.util.List;

@Data
public class OkxServerTimeResponse {
    private String code;
    private String msg;
    private List<String> data;
}
