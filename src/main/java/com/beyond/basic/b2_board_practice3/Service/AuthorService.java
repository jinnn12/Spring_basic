//package com.beyond.basic.b2_board_practice3.Service;
//
//import com.beyond.basic.b2_board_practice3.DTO.AuthorCreateDto;
//import com.beyond.basic.b2_board_practice3.DTO.AuthorUpdatePwDto;
//import com.beyond.basic.b2_board_practice3.Domain.Author;
//import com.beyond.basic.b2_board_practice3.Repository.AuthorRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class AuthorService {
//    private final AuthorRepository authorRepository;
//
//    public void save(AuthorCreateDto authorCreateDto) {
//        Author author = new Author(authorCreateDto.getName(), authorCreateDto.getEmail(), authorCreateDto.getPassword());
//        authorRepository.save(author);
//    }
//
//    public List<Author> findAll() {
//        return authorRepository.findAll();
//    }
//
//    public Author findById(Long id) {
//        Optional<Author> optionalAuthor = authorRepository.findById(id);
//        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("조회하고자 하는 아이디가 없음"));
//    }
//
//    public Author findByEmail(String email) {
//        Optional<Author> optionalAuthor = authorRepository.findByEmail(email);
//        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("조회하고자 하는 이메일이 없음"));
//    }
//
//    public void updatePassword(AuthorUpdatePwDto authorUpdatePwDto) {
//        Author author = authorRepository.findByEmail(authorUpdatePwDto.getEmail()).orElseThrow(() -> new NoSuchElementException("이메일 없음"));
//        author.updatePassword(authorUpdatePwDto.getPassword());
//    }
//
//    public void delete(Long id) {
//        authorRepository.delete(id);
//    }
//
//}
