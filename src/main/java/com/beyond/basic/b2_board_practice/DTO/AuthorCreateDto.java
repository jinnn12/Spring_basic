package com.beyond.basic.b2_board_practice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthorCreateDto {
    private String name;
    private String email;
    private String password;
}
