//package com.beyond.basic.b2_board.DTO;
//
//import com.beyond.basic.b2_board.Domain.Author;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Data // DTO 계층은 data의 안정성이 entity만큼 중요하지 않으므로 setter도 일반적으로 추가한다.
//@AllArgsConstructor
//@NoArgsConstructor
//
//public class AuthorCreateDto {
//    private String name;
//    private String email;
//    private String password;
//
//    public Author authorToEntity() {
//        return new Author(this.name, this.email, this.password);
//    }
//}
