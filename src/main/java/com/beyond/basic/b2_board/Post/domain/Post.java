package com.beyond.basic.b2_board.Post.domain;

import com.beyond.basic.b2_board.Author.Domain.Author;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 3000)
    private String contents;
//    private Long authorId; 어차피 밑의 author 객체를 까보면 authorId가 있을거 아님?
//    fk설정 시 ManyToOne은 필수
//    ManyToOne에서 default fetch.EAGER(즉시로딩) : author객체를 사용하지 않아도 author테이블로 쿼리발생. fetch : 가져오다
//    그래서 일반적으로 'FetchType.LAZY(지연로딩)' 설정을 해준다. : author 객체를 사용하지 않는 한 author객체로 쿼리발생 X -> 성능이 당연히 높음
    @ManyToOne(fetch = FetchType.LAZY) // Post(n) : (1)Author -> ManyToOne, 관계성을 추가한 것이다.
    @JoinColumn(name = "author_id") // fk관계성 설정한 어노테이션, author라고 DB에 저장 못하니까 DB에 저장할 컬럼명
    private Author author;
}
