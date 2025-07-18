package com.beyond.basic.b2_board.Author.Domain;

import com.beyond.basic.b2_board.Common.BaseTimeEntity;
import com.beyond.basic.b2_board.Post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// JPA를 사용할 경우 @Entity는 반드시 붙여야 하는 어노테이션
// JPA의 EntityManager에게 객체를 위임하기 위한 어노테이션
// EntityManager는 영속성컨텍스트(엔터티(객체)의 현재상황)를 통해 DB 데이터 관리
@Entity
// @Builder를 통해 유연하게 객체 생성 가능 (n개 필드가 늘어날 때마다 생성자가 n개 생기므로)
@Builder

public class Author extends BaseTimeEntity {
    @Id // @Id : PK로 설정하겠다는 의미
//    IDENTITY : auto_increment / auto : id생성전략을 jpa에게 자동설정하도록 위임
    @GeneratedValue(strategy = GenerationType.IDENTITY) // @Id, @GeneratedValue(strategy = GenerationType.IDENTITY) 외우기
    private Long id; // wrapper클래스로 하는 것이 일반적임
//    컬럼에 별다른 설정이 없을 경우 Default varchar(255)

    private String name;
    @Column(length = 50, unique = true, nullable = false)
    private String email;
//    @Column(name = "pw") : 되도록이면 컬럼명과 변수명을 일치시키는 것이 개발의 혼선을 줄일 수 있음
    private String password;
    @Enumerated(EnumType.STRING) // Enum 타입 할거면 이렇게 꼭 붙이기
    @Builder.Default // 빌더패턴에서 변수 초기화(디폴트값)시 @Builder.Default 필수
    private Role role = Role.USER; // 초기값 세팅하는 경우 @Builder에서 이 초기값을 무시한다, 이때 @Builder.Default 사용, 초기값 무시하지마!
//    컬럼명에 케멀케이스 사용하게 되면, DB에는 'created_time'으로 컬럼 생성 (JPA 룰)

//    OneToMany는 완전히 선택사항, default가 LAZY, 하다보면 헷갈릴 수 있으니 fetch = FetchType.LAZY 다 쓰자
//    mappedBy에는 ManyToOne(Post) 쪽의 변수 명을 문자열로 지정. fk관리를 반대편(post)쪽에서 한다는 의미 -> (fk의 주인)연관관계의주인 설정
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Builder.Default // 빌더패턴에선 초기화 진행 시 필수
    List<Post> postList = new ArrayList<>(); // OneToMany쓸 땐 초기화 필수


//    public Author(String name, String email, String password) {
////        this.id = AuthorMemoryRepository.id; // static이니까
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }
//
//    public Author(String name, String email, String password, Role role) {
////        this.id = AuthorMemoryRepository.id; // static이니까
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }

    // setter가 없으므로 메서드를 하나 만들어서 원본데이터에 접근하여 변경할 수 있도록 해줘야 함.
    // 즉 setter 대신
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

//    public AuthorDetailDto detailFromEntity() {
//        return new AuthorDetailDto(this.id, this.name, this.email);
//    }
//
//    public AuthorListDto listFromEntity() {
//        return new AuthorListDto(this.id, this.name, this.password);
//    }

}
