//package com.beyond.basic.b2_board_practice2.Controller;
//
//import com.beyond.basic.b2_board_practice2.DTO.AuthorCreateDto;
//import com.beyond.basic.b2_board_practice2.DTO.AuthorPwUpdateDto;
//import com.beyond.basic.b2_board_practice2.Domain.Author;
//import com.beyond.basic.b2_board_practice2.Service.AuthorService;
//import lombok.Data;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@RestController // Controller + ResponseBody
//@RequiredArgsConstructor
//@RequestMapping("/author")
//public class AuthorController {
//    private final AuthorService authorService;
//
//    //    회원가입 : /create
//    @PostMapping("/create")
//    public String save(@RequestBody AuthorCreateDto authorCreateDto) {
//        try {
//            return authorService.save(authorCreateDto);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            return e.getMessage();
//        }
//    }
//
//    //    회원전체조회 : /list
//    @GetMapping("/list")
//    public List<Author> findAll() {
//        return authorService.findAll();
//    }
//
//    //    회원아이디상세조회 : /list/1
//    @GetMapping("/list/{inputId}")
//    public Author findById(@PathVariable Long inputId) {
//        return authorService.findById(inputId);
//    }
//
//    //    회원비밀번호수정 : /updatepassword
//    @PatchMapping("/updatepassword")
//    public void patchPw(@RequestBody AuthorPwUpdateDto authorPwUpdateDto) {
//        authorService.updatepassword(authorPwUpdateDto);
//    }
//
//    //    회원삭제 : /delete/1
//    @DeleteMapping("/delete/{inputId}")
//    public void delete(@PathVariable Long inputId) {
//        authorService.delete(inputId);
//    }
//}
