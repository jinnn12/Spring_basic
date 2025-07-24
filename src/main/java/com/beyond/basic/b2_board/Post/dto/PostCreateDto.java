package com.beyond.basic.b2_board.Post.dto;

import com.beyond.basic.b2_board.Author.Domain.Author;
import com.beyond.basic.b2_board.Post.domain.Post;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PostCreateDto {
    @NotEmpty
    private String title;
    private String contents;
    @Builder.Default
    private String appointment = "N"; // N으로 깔고, Y로 넘어올 때 예약 로직 이행
//    시간정보는 직접 LocalDateTime으로 형변환 하는 경우가 많음
    private String appointmentTime; // 값을 안 쓰게 되면 null~
    private String category;

    public Post toEntity(Author author, LocalDateTime appointmentTime) {
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
//                .authorId(this.authorId)
                .author(author) // 객체를 찾아다가 넣어주기
                .delYn("N")
                .appointment(this.appointment)
                .appointmentTime(appointmentTime)
                .category(this.category)
                .build();
    }
}
