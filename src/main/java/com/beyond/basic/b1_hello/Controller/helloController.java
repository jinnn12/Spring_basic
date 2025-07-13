package com.beyond.basic.b1_hello.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

// component annotation을 통해 별도의 객체를 생성할 필요가 없는 싱글톤 객체 생성
// 어딘가 이 클래스의 객체를 딱 한 번 만듦

// Controller annotation을 통해 쉽게 사용자의 http request를 분석하고, http response를 생성한다
// Controller가 사용자에게 관문 역할
@Controller
// 클래스 차원에 url 매핑 시 RequestMapping 사용 (패턴 적용) ex) /hello/json , /hello/hello
@RequestMapping("/hello")

public class helloController {
    //    get요청의 case들
//    case1. 서버가 사용자에게 단순 String 데이터 return - @ResponseBody가 있을 때
    @GetMapping("") // 아래 메서드에 대한 서버의 end point를 설정
//    ResponseBody가 있으면 데이터를 주고
//    없으면 화면을 달라고 인식, return 타입이 String인 경우 서버는 templates폴더 밑에 helloworld.html을 찾아서 리턴
//    @ResponseBody / @RestController(Controller + ResponseBody)
    @ResponseBody
    public String helloWorld() {
        return "helloworld";
    }

    //    case2. 서버가 사용자에게 String(json형식)의 데이터 return
    @GetMapping("/json")
    @ResponseBody
    public Hello helloJson() throws JsonProcessingException {
//        직접 json으로 직렬화 할 필요 없이, return 타입에 객체가 있으면 자동으로 직렬화 된다.
        Hello h1 = new Hello("hong", "hong@naver.com");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String result = objectMapper.writeValueAsString(h1);
        return h1;
    }

    //    case3. Parameter 방식을 통해 사용자로부터 값을 수신
//    parameter의 형식 : /member?id=1 , member?name=hongildong
    @GetMapping("/param")
    @ResponseBody
    public Hello param(@RequestParam(value = "name") String inputName) {
        System.out.println(inputName);
//        {name:사용자가넣은이름, email:hong@naver.com}
        Hello h2 = new Hello(inputName, "hong@naver.com");
        return h2;
    }

    //    case4. pathvariable 방식을 통해 사용자로부터 값을 수신
//    pathvariable의 형식 : /member/1
//    pathvariable방식은 url을 통해 자원의 구조를 명확하게 표현할 때(가독성이 높게) 사용 (조금 더 restful 함)
    @GetMapping("/path/{inputId}")
    @ResponseBody
    public String path(@PathVariable Long inputId) {
//        별도의 형 변환 없이도, 매개변수에 타입 지정(Long) 시 자동 형변환(String으로) 시켜줌
//        long id = Long.parseLong(inputId);
        System.out.println(inputId); //??? 왜 id로 넣어도 콘솔에 출력이 되지?
        return "OK";
    }

    //    case5. parameter 2개 이상 형식 :
//    /hello/param2?name=hong&email=hong@naver.com // url 인코딩방식 -> body로 들어가고, 이건 head로 들어감
    @GetMapping("/param2")
    @ResponseBody
    public String param2(@RequestParam(value = "name") String inputName,
                         @RequestParam(value = "email") String inputEmail) {
        System.out.println(inputName);
        System.out.println(inputEmail);
        return "OK";
    }

    //    case6. parameter가 많아질 경우, 데이터바인딩을 통해 input값 처리
//    데이터바인딩 : parameter를 사용하여 스프링에서 '객체'로 생성해줌, param이 많이 넘어오니까 객체로 만들어서 사용하는 것이 코드 효율적
//    param3?name=hong&email=hong@naver.com
    @GetMapping("/param3")
    @ResponseBody
//    public String param3(Hello hello) {
//    @ModelAttribute를 써도 되고 안 써도 되는데 이 키워드를 써서 명시적으로 param 형식의 데이터를 받겠다라는 표현
    public String param3(@ModelAttribute Hello hello) {
        System.out.println(hello);
        return "OK";
    }

    //    case7. 서버에서 화면을 return, 사용자로부터 넘어오는 input값을 활용하여 동적인 화면 생성 (앞서 @ResponseBody를 뺀 경우로 해봤음)
//    서버에서 화면을 렌더링 해주는 SSR(화면 + 데이터) 방식 (CSR은 서버 -> 데이터만)
//    model이라는 요소에 data를 넣고 return + helloworld2.html + 타임리프로 view, controller ?
//    mvc(model, view, controller)패턴이라고도 함
//    화면 + 데이터를 주는 서버의 방식(legacy) : SSR방식, JSP/thymeleaf
//    데이터만 주는 서버의 방식 : rest api 방식 -> @RestController : rest api에서 활용하는 controller
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "id") Long inputId, Model model) {
//        model 객체는 데이터를 화면에 전달해주는 역할을 한다 ((Key, Value) 형식 / addAttribute : 속성을 더하겠다)
//        name이라는 key에 hong이라는 value를 key:value 형태로 화면에 전달
//        타임리프(thymeleaf) : 화면 + 데이터 구현하는 기술
        if (inputId == 1) {
            model.addAttribute("name", "hong");
            model.addAttribute("email", "hong@naver.com");
        } else if (inputId == 2) {
            model.addAttribute("name", "hong2");
            model.addAttribute("email", "hong2@naver.com");
        }
        return "helloworld2"; // @ResponseBody가 없으니 문자열.html을 찾으려고 함(화면을 찾으려고) 그래서 이 놈이 띄어쓰기가 되면 안됨!
    }

    //    Post요청의 case 2가지 : url 인코딩 방식 또는 multipart-formdata, json
//    case1. text만 있는 form-data 형식
//    형식 : body에 name=xxx&email=xxx -> parameter방식 / post니까 body에 넣는게..
    @GetMapping("/form-view")
    public String formView() {
        return "form-view";
    }

    @PostMapping("/form-view") // GetMapping과 url이 같더라도 post,get 다르기 때문에 괜찮음
    @ResponseBody
//    get요청에 url에 파라미터방식과 동일한 데이터 형식이므로, RequestParam 또는 데이터바인딩 방식 가능
//    public String formViewPost(Hello hello) {
    public String formViewPost(@ModelAttribute Hello hello) {
        System.out.println(hello);
        return "OK";
    }

//    case2-1. text와 file이 있는 form-data 형식 (순수 html로 제출)
    @GetMapping("/form-file-view")
    public String formFileView() {
        return "form-file-view";
    }

    @PostMapping("/form-file-view")
    @ResponseBody
    public String formFileViewPost(@ModelAttribute Hello hello,
                                   @RequestParam(value = "photo")MultipartFile photo) { // Hello
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "OK";
    }
//    case2-2. text와 file이 있는 form-data 형식 (js로 제출)
    @GetMapping("/axios-file-view")
    public String axiosFileView() {
        return "axios-file-view";
    }

//    case3. text와 multi file이 있는 form-data 형식(js로 제출)
    @GetMapping("/axios-multi-file-view")
    public String axiosMultiFileView() {
    return "axios-multi-file-view";
}

    @PostMapping("/axios-multi-file-view")
    @ResponseBody
    public String axiosMultiFileViewPost(@ModelAttribute Hello hello,
                                   @RequestParam(value = "photos") List<MultipartFile> photos) { // Hello
        System.out.println(hello);
        for (int i = 0; i < photos.size(); i++) {
            System.out.println(photos.get(i).getOriginalFilename());
        }
        return "OK";
    }

//    case4. json 데이터 처리 (젤 많이 사용됨)
    @GetMapping("/axios-json-view")
    public String axiosJsonView() {
        return "axios-json-view";
    }
    @PostMapping("/axios-json-view")
    @ResponseBody
//    @RequestBody : json형식으로 데이터가 들어올 때 '객체'로 자동 '파싱'할 때 쓰는 것
    public String axiosJsonViewPost(@RequestBody Hello hello){ // file형태는 ModelAttribute, json형식은 RequestBody
        System.out.println(hello);
        return "OK";
    }

//    case5. 중첩된 json 데이터 처리
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView() {
    return "axios-nested-json-view";
}
    @PostMapping("/axios-nested-json-view")
    @ResponseBody
//    @RequestBody : json형식으로 데이터가 들어올 때 '객체'로 자동 '파싱'할 때 쓰는 것
    public String axiosNestedJsonViewView(@RequestBody Student student){ // file형태는 ModelAttribute, json형식은 RequestBody
        System.out.println(student);
        return "ok";
    }

//    case6. json(text) + file 같이 처리할 때 : 텍스트구조가 복잡(계층구조를 갖고있다)하여 피치 못하게 json 구조를 사용해야 하는 경우
//    데이터형식 : hello={name:"xx", email:"xxx@naver.com"}&photo=image.jpg
//    ..결론 : 단순 json구조가 아닌, multipart-formdata 구조 안에 json을 넣는 구조
//    @GetMapping("/axios-json-file-view")
//    public String axiosJsonFileView() {
//    return "axios-nested-json-view";
//}
//    @PostMapping("/axios-json-file-view")
//    @ResponseBody
//    public String axiosJsonFileView(){
//        System.out.println();
//        return "ok";
//    }

}
