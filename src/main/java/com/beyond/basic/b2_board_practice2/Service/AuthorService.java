//package com.beyond.basic.b2_board_practice2.Service;
//
//import com.beyond.basic.b2_board_practice2.DTO.AuthorCreateDto;
//import com.beyond.basic.b2_board_practice2.DTO.AuthorPwUpdateDto;
//import com.beyond.basic.b2_board_practice2.Repository.AuthorRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import com.beyond.basic.b2_board_practice2.Domain.Author;
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class AuthorService {
//    private final AuthorRepository authorRepository;
//
//    public String save(AuthorCreateDto authorCreateDto) {
//        if (authorRepository.findByEmail(authorCreateDto.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
//        }
//        Author author = new Author(authorCreateDto.getName(), authorCreateDto.getEmail(), authorCreateDto.getEmail());
//        authorRepository.save(author);
//        return "OK";
//    }
//
//    public List<Author> findAll() {
//        return this.authorRepository.findAll();
//    }
//
//    public Author findById(Long id) {
//        Optional<Author> optionalAuthor = authorRepository.findById(id);
//        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("아이디 없음"));
//    }
//
//    public Author findByEmail(String email) {
//        Optional<Author> optionalAuthor = authorRepository.findByEmail(email);
//        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("이메일 없음"));
//    }
//
//    public void updatepassword(AuthorPwUpdateDto authorPwUpdateDto) {
//        Author author = authorRepository.findByEmail(authorPwUpdateDto.getEmail()).orElseThrow(() -> new NoSuchElementException("이메일 없음"));
//        author.updatePassword(authorPwUpdateDto.getPassword());
//    }
//
//    public void delete(Long id) {
//        authorRepository.delete(id);
//    }
//
//}
