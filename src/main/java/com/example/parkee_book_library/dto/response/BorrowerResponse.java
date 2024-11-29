package com.example.parkee_book_library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerResponse {
    private Long id;
    private String name;
    private String email;
}

