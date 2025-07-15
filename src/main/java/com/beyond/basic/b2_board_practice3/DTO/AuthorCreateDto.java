package com.beyond.basic.b2_board_practice3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthorCreateDto {
    private String name;
    private String email;
    private String password;
}
