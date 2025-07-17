package com.beyond.basic.b2_board.Author.DTO;

import com.beyond.basic.b2_board.Author.Domain.Author;
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

    public static AuthorListDto listFromEntity (Author author) {
        return new AuthorListDto(author.getId(), author.getName(), author.getPassword());
    }

}
