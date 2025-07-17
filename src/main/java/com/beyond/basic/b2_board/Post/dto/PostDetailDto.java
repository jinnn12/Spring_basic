package com.beyond.basic.b2_board.Post.dto;

import com.beyond.basic.b2_board.Author.Domain.Author;
import com.beyond.basic.b2_board.Post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailDto {

    private Long id;
    private String title;
    private String contents;
    private String authorEmail; // 아이디로 글을 조회하는데 사용자에겐 이메일을 주겠다는 의미

//  1. 관계성 설정 하지 않았을 경우
//    public static PostDetailDto fromEntity(Post post, Author author) {
//        return PostDetailDto.builder()
//                .id(post.getId())
//                .title(post.getTitle())
//                .contents(post.getContents())
//                .authorEmail(author.getEmail())
//                .build();
//    }

    //    2. 관계성 설정을 하였을 경우
    public static PostDetailDto fromEntity(Post post) {

        return PostDetailDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .authorEmail(post.getAuthor().getEmail()) // post객체 안에 author가 있고 이 author를 들고 와서 author.getEmail, Post 안에서 Author 어떻게 정의했는지 보기
                .build();
    }
}
