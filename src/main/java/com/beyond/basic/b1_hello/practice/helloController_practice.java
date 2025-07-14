package com.beyond.basic.b1_hello.practice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;


@Controller
@RequestMapping("/hello_practice")

public class helloController_practice {
    //    get요청의 case들
//    case1. 서버거 사용자에게 단순 String 데이터 return, @ResponseBody가 존재할 때.
    @GetMapping("")
    @ResponseBody
    public String helloWorld_practice() {
        return "hello world";
    }

    //    case2. 서버가 사용자에게 String(json형식)의 데이터 리턴
//    기본적인 json parsing 방식인 ObjectMapper 활용
    @GetMapping("/json")
    @ResponseBody
    public String helloJson() throws JsonProcessingException {
        Hello h1 = new Hello("hong", "hong@naver.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(h1);
        return result;
    }

    //    Spring에서는 ObjectMapper가 없어도 메서드 타입, return 타입에 객체를 만들면 자동으로 parsing
    @GetMapping("/json2")
    @ResponseBody
    public Hello helloJson2() {
        Hello h2 = new Hello("hong2", "hong@naver.com");
        return h2;
    }

    //    case3. Parameter 방식을 통해 사용자로부터 값을 수신
//    Parameter : /member?id=1, /member?name=hong
    @GetMapping("/param")
    @ResponseBody
    public Hello param(@RequestParam(value = "name") String inputName) {
        System.out.println(inputName);
        Hello h3 = new Hello(inputName, "hong3@naver.com");
        return h3;
    }

    //    case4. PathVariable 방식을 통해 사용자로부터 값을 수신
//    PathVariable : /member/1, /member/2
//    PathVariable 방식은 url을 통해 자원의 구조를 명확히 할 수 있음 / 조금 더 Restful함
    @GetMapping("path/{inputId}")
    @ResponseBody
    public String pathVariable(@PathVariable Long inputId) {
        System.out.println(inputId);
        return "ok";
    }

    //    case5. parameter 2개 이상 형식
//    /hello/param2?name=hong&email=hong@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public String param2(@RequestParam(value = "name") String inputName,
                         @RequestParam(value = "email") String inputEmail) {
        System.out.println(inputName);
        System.out.println(inputEmail);
        return "ok";
    }

    //    case6. Parameter가 많아질 경우, 데이터바인딩을 통해 input값 처리
//    데이터바인딩 : Parameter를 사용하여 스프링에 '객체'로 만들어둠
//    데이터바인딩을 통해 코드 효율을 얻을 수 있다.
    @GetMapping("/param3")
    @ResponseBody
    public String param3(@ModelAttribute Hello hello) {
        System.out.println(hello);
        return "oK";
    }

    //    case7. 서버에서 화면을 return, 사용자로부터 넘어오는 input값을 활용하여 동적인 화면 생성
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "id") Long inputId, Model model) {
//        model 객체는 데이터를 화면에 전달해주는 역할을 한다. ((key, value) 형식)
//        helloworld2.html에서 <h3 th:text="${name}"><h3> 과 같이 thymeleaf 템플릿 엔진에서 html 요소에 동적으로 값을 삽입할 때 사용
        if (inputId == 1) {
            model.addAttribute("name", "hong1");
            model.addAttribute("email", "hong1@naver.com");
        } else if (inputId == 2) {
            model.addAttribute("name", "hong2");
            model.addAttribute("email", "hong2@daum.net");
        }
        return "helloworld2";
    }

    //    case7-1) PathVariable 형식으로 만들어보기
    @GetMapping("/model-path/{inputId}")
    public String modelPath(@PathVariable("inputId") Long inputId, Model model) {
        if (inputId == 1) {
            model.addAttribute("name", "kim");
            model.addAttribute("email", "kim@naver.com");
        } else if (inputId == 2) {
            model.addAttribute("name", "kim2");
            model.addAttribute("email", "kim2@naver.com");
        }
        return "helloworld2";
    }

    //    Post 요청의 case 2가지 (url 인코딩 방식 / multipart-formdata, json)
//    case1. text만 있는 form-data 형식
//    형식 : body에 name=xxx&email=xxx -> Parameter방식, Post 요청이므로 body에 넣어야 함
    @GetMapping("/form-view")
    public String formView() {
        return "form-view";
    }

    @PostMapping("/form-view")
    @ResponseBody
    public String formViewPost(@ModelAttribute Hello hello) {
        System.out.println(hello);
        return "OK";
    }

    //    case2-1) text와 file이 있는 form-data 형식(순수 html로 제출)
    @GetMapping("/form-file-view")
    public String formFileView() {
        return "form-file-view";
    }

    @PostMapping("/form-file-view")
    @ResponseBody
    public String formFileViewPost(@ModelAttribute Hello hello,
                                   @RequestParam(value = "photo") MultipartFile photo) {
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "OK";
    }

    //    case2-2. text와 file이 있는 form-data 형식 (js로 제출)
    @GetMapping("/axios-file-view")
    public String axiosFileView() {
        return "axios-file-view";
    }

    @PostMapping("/axios-file-view")
    @ResponseBody
    public String axiosFileViewPost(@RequestParam(value = "photo") MultipartFile photo, @ModelAttribute Hello hello) {

        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "jOK";
    }

    //    case3. text와 multi file이 있는 form-data 형식 (js로 제출)
    @GetMapping("/axios-multi-file-view")
    public String axiosMultiFileView() {
        return "axios-multi-file-view";
    }

    @PostMapping("/axios-multi-file-view")
    @ResponseBody
    public String axiosMultiFileViewPost(@ModelAttribute Hello hello,
                                         @RequestParam(value = "photos") List<MultipartFile> photos) {
        System.out.println(hello);
        for (int i = 0; i < photos.size(); i++) {
            System.out.println(photos.get(i).getOriginalFilename());
        }
        return "OK";
    }

    //    case4. json 데이터 처리 (제일 많이 사용)
    @GetMapping("/axios-json-view")
    public String axiosJsonView() {
        return "axios-json-view";
    }

    @PostMapping("/axios-json-view")
    @ResponseBody
    public String axiosJsonViewPost(@RequestBody Hello hello) {
        System.out.println(hello);
        return "oK";
    }

    //    case5. 중첩된 json 데이터 처리
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView() {
        return "axios-nested-json-view";
    }

    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedViewPost(@RequestBody Student student) {
        System.out.println(student);
        return "ok";
    }
}
