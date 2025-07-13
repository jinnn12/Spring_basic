package com.beyond.basic.b1_hello.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private String email;
    private List<Score> scores;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data // 여기도 어노테이션 깔아줘야 함을 잊지 말자
    private static class Score {
        private String subject;
        private int point;
    }

}
