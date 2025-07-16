//package com.beyond.basic.b2_board.Domain;
//
//import com.beyond.basic.b2_board.DTO.AuthorCreateDto;
//import com.beyond.basic.b2_board.DTO.AuthorDetailDto;
//import com.beyond.basic.b2_board.DTO.AuthorListDto;
//import com.beyond.basic.b2_board.Repository.AuthorMemoryRepository;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//// JPA를 사용할 경우 @Entity는 반드시 붙여야 하는 어노테이션
//// JPA의 EntityManager에게 객체를 위임하기 위한 어노테이션
//// EntityManager는 영속성컨텍스트(엔터티(객체)의 현재상황)를 통해 DB 데이터 관리
//@Entity
//
//public class Author {
//    @Id // @Id : PK로 설정하겠다는 의미
////    IDENTITY : auto_increment / auto : id생성전략을 jpa에게 자동설정하도록 위임
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // @Id, @GeneratedValue(strategy = GenerationType.IDENTITY) 외우기
//    private Long id; // wrapper클래스로 하는 것이 일반적임
////    컬럼에 별다른 설정이 없을 경우 Default varchar(255)
//
//    private String name;
//    @Column(length = 50, unique = true, nullable = false)
//    private String email;
////    @Column(name = "pw") : 되도록이면 컬럼명과 변수명을 일치시키는 것이 개발의 혼선을 줄일 수 있음
//    private String password;
////    private String test;
////    private String test2;
//
//    public Author(String name, String email, String password) {
////        this.id = AuthorMemoryRepository.id; // static이니까
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }
//
//    // setter가 없으므로 메서드를 하나 만들어서 원본데이터에 접근하여 변경할 수 있도록 해줘야 함.
//    // 즉 setter 대신
//    public void updatePassword(String newPassword) {
//        this.password = newPassword;
//    }
//
////    public AuthorDetailDto detailFromEntity() {
////        return new AuthorDetailDto(this.id, this.name, this.email);
////    }
////
////    public AuthorListDto listFromEntity() {
////        return new AuthorListDto(this.id, this.name, this.password);
////    }
//
//}
