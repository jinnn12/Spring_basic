package com.beyond.basic.b2_board_practice.Controller;

import com.beyond.basic.b2_board_practice.DTO.AuthorCreateDto;
import com.beyond.basic.b2_board_practice.DTO.AuthorUpdatePwDto;
import com.beyond.basic.b2_board_practice.Domain.Author;
import com.beyond.basic.b2_board_practice.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor

public class AuthorController {
    private final AuthorService authorService;

    //    회원가입 : /create
    @PostMapping("/create")
    public String save(@RequestBody AuthorCreateDto authorCreateDto) {
        try {
            return authorService.save(authorCreateDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return e.getMessage();

        }
    }

    //    회원전체조회 : /list
    @GetMapping("/list")
    public List<Author> findAll() {
        return this.authorService.findAll();
    }

    //    회원상세조회 : /detail/{id}
    @GetMapping("/detail/{inputId}")
    public Author detail(@PathVariable Long inputId) {
        return authorService.findById(inputId);
    }

    //    비밀번호수정 : email로 password 수정 -> json, /author/updatepassword
    @PatchMapping("/updatepw")
    public void updatePassword(@RequestBody AuthorUpdatePwDto authorUpdatePwDto) {
        authorService.updatePassword(authorUpdatePwDto);
    }

    //    회원탈퇴(삭제) : /author/delete/1 , 실무에서는 실제로 delete라고 해놓고 patch로 진행
    @DeleteMapping("/detail/{inputId}")
    public void delete(@PathVariable Long inputId) {
        authorService.delete(inputId);
    }
}

