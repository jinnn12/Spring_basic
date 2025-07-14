package com.beyond.basic.b2_board_practice.Service;

import com.beyond.basic.b2_board_practice.DTO.AuthorCreateDto;
import com.beyond.basic.b2_board_practice.DTO.AuthorUpdatePwDto;
import com.beyond.basic.b2_board_practice.Domain.Author;
import com.beyond.basic.b2_board_practice.Repository.AuthorMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorMemoryRepository authorMemoryRepository;

    public String save(AuthorCreateDto authorCreateDto) {
        Author author = new Author(authorCreateDto.getName(), authorCreateDto.getEmail(), authorCreateDto.getPassword());
        this.authorMemoryRepository.save(author);
        return "OK";
    }

    public List<Author> findAll() {
        return this.authorMemoryRepository.findAll();
    }

    public Author findById(Long id) {
        Optional<Author> optionalAuthor = authorMemoryRepository.findById(id);
        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("아이디업슴"));
    }

    public Author findByEmail(String email) {
        Optional<Author> optionalAuthor = authorMemoryRepository.findByEmail(email);
        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("이메일없음"));
    }

    public void updatePassword(AuthorUpdatePwDto authorUpdatePwDto) {
        Author author = authorMemoryRepository.findByEmail(authorUpdatePwDto.getEmail()).orElseThrow(() -> new NoSuchElementException("이메일없음"));
        author.updatePassword(authorUpdatePwDto.getPassword());
    }

    public void delete(Long id) {
        authorMemoryRepository.delete(id);
    }

}
