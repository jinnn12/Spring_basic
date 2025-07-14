package com.beyond.basic.b2_board.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthorUpdatePwDto {

    private String email;
    private String password;
}
