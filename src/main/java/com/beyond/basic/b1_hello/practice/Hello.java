package com.beyond.basic.b1_hello.practice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor // 모든 매개변수가 있는 생성자
// AllArgsConstructor, NoArgs, Getter -> 기본세트
// + setter 원하면 AllArgsConstructor, NoArgsConstructor, Data
@NoArgsConstructor

public class Hello {
//    모든 변수를 기준으로 getter가 없더라도 @Getter로 인해 알아서 생성됨
//    @Data : getter, setter, toString 메서드 모두 만들어주는 어노테이션, setter가 있으므로 함부로 사용 금지

    private String name;

    private String email;


}
