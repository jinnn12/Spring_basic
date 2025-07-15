package com.beyond.basic.b2_board_practice3.Controller;

import com.beyond.basic.b2_board_practice3.DTO.AuthorCreateDto;
import com.beyond.basic.b2_board_practice3.Domain.Author;
import com.beyond.basic.b2_board_practice3.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    //    회원가입 : /create
    @PostMapping("/create")
    public void save(@RequestBody AuthorCreateDto authorCreateDto) {
        authorService.save(authorCreateDto);
    }

    //    회원목록전체조회 : /list
    @GetMapping("/list")
    public List<Author> findAll() {
        return authorService.findAll();
    }

    //    회원아이디로조회 : /list/1
    @GetMapping("/list/{inputId}")
    public Author findById(@PathVariable Long inputId) {
        return authorService.findById(inputId);
    }

    //    회원비밀번호수정 : /updatepassword
    @PatchMapping("/updatepassword")
    public void updatePassword() {

    }

    //    회원정보삭제 : /delete/1
    @DeleteMapping("/delete/{inputId}")
    public void delete(@PathVariable Long inputId) {
        authorService.delete(inputId);
    }
}
