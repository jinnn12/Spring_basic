package com.beyond.basic.b2_board.Author.DTO;

import com.beyond.basic.b2_board.Author.Domain.Author;
import com.beyond.basic.b2_board.Author.Domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Integer postCount;
    private LocalDateTime createdTime;
//    1개의 entity로만 dto가 조립되는 것이 아니기에 dto 계층에서 fromentity를 설계
//    public static AuthorDetailDto fromEntity (Author author, Integer postCount)
    public static AuthorDetailDto fromEntity (Author author) {
        return AuthorDetailDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .role(author.getRole())
                .postCount(author.getPostList().size())
                .createdTime(author.getCreatedTime()) // author안엔 없지만 BaseTimeEntity 상속하고 있으므로 가능
                .build();
    }

}
