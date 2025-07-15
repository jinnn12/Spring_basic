package com.beyond.basic.b2_board_practice3.Domain;

import com.beyond.basic.b2_board_practice3.Repository.AuthorRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Author {
    private Long id;
    private String name;
    private String email;
    private String password;

    public Author(String name, String email, String password) {
        this.id = AuthorRepository.id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }


}
