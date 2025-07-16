package com.beyond.basic.b2_board_practice4.Service;

import com.beyond.basic.b2_board_practice4.DTO.AuthorCreateDto;
import com.beyond.basic.b2_board_practice4.DTO.AuthorDetailDto;
import com.beyond.basic.b2_board_practice4.DTO.AuthorListDto;
import com.beyond.basic.b2_board_practice4.DTO.AuthorUpdatePwDto;
import com.beyond.basic.b2_board_practice4.Domain.Author;
import com.beyond.basic.b2_board_practice4.Repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    //    회원가입
    public void save(AuthorCreateDto authorCreateDto) {
        if (authorRepository.findByEmail(authorCreateDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Author author = authorCreateDto.authorToEntity();
        authorRepository.save(author);
    }

    //    회원전체조회
    public List<AuthorListDto> findAll() {
        return authorRepository.findAll().stream()
                .map(author -> AuthorListDto.listFromEntity(author))
                .collect(Collectors.toList());
    }

    //    회원단건조회 (id로)
    public AuthorDetailDto findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("아이디 없습니다."));
        AuthorDetailDto authorDetailDto = AuthorDetailDto.authorDetailDto(author);
        return authorDetailDto;
//        Optional<Author> optionalAuthor = authorRepository.findById(id);
//        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
    }

    //    회원비밀번호수정
    public void updatePassword(AuthorUpdatePwDto authorUpdatePwDto) {
        Author author = authorRepository.findByEmail(authorUpdatePwDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("조회되는 이메일이 없습니다."));
        author.updatePassword(authorUpdatePwDto.getPassword());
    }

//    회원탈퇴
    public void delete(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("없는 아이디 입니다."));
        authorRepository.delete(author);
    }
}
