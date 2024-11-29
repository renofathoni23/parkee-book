package com.example.parkee_book_library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomPageResponse<T> {
    private List<T> data;
    private int page;
    private int size;
    private long totalData;
}

