package com.beyond.basic.b2_board.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorListDto {
    private Long id;
    private String name;
    private String password;

}
