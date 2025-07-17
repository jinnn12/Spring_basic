package com.beyond.basic.b2_board.Author.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CommonDto {
    private Object result; // 원하는 요소 집어넣기, 모든 객체를 넣을 수 있는 Object 사용
    private int statusCode;
    private String statusMessage;
}
