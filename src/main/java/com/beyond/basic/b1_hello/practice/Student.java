package com.beyond.basic.b1_hello.practice;

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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Score {
        private String subject;
        private Long point;
    }
}
