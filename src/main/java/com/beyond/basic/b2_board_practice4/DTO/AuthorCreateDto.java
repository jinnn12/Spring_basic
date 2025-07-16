package com.beyond.basic.b2_board_practice4.DTO;

import com.beyond.basic.b2_board_practice4.Domain.Author;
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

    public Author authorToEntity() {
        return new Author(this.name, this.email, this.password);
    }

}
