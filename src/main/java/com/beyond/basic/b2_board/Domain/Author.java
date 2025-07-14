package com.beyond.basic.b2_board.Domain;

import com.beyond.basic.b2_board.Repository.AuthorMemoryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Author {
    private Long id; // wrapper클래스로 하는 것이 일반적임
    private String name;
    private String email;
    private String password;

    public Author(String name, String email, String password) {
        this.id = AuthorMemoryRepository.id; // static이니까
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // setter가 없으므로 메서드를 하나 만들어서 원본데이터에 접근하여 변경할 수 있도록 해줘야 함.
    // 즉 setter 대신
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
