package com.beyond.basic.b1_hello.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

// 모든 변수를 기준으로 getter가 없더라도 @Getter로 인해 알아서 생성됨
//@Getter // 클래스 내의 모든 변수를 대상으로 getter가 생성
@Data // getter, setter, toString 메서드까지 모두 만들어주는 어노테이션, setter가 있기 때문에 막 사용하면 안됨
@AllArgsConstructor // 모든 매개변수가 있는 생성자
// 기본생성자 + Getter의 조합은 parsing이 이루어지므로 보통은 기본생성자는 필수 요소이다.
// AllArgs, NoArgs, Getter -> 기본세트
// 혹은 AllArgs, NoArgs, Data -> Setter가 필요하다면,,,
@NoArgsConstructor // 기본생성자

public class Hello {
    private String name;
    private String email;
//    private MultipartFile photo; @RequestParam(value = "photo")MultiPartFile photo 대신 사용 가능

    @Override
    public String toString() {
        return "Hello{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
