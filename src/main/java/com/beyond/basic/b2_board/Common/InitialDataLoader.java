package com.beyond.basic.b2_board.Common;

import com.beyond.basic.b2_board.Author.Domain.Author;
import com.beyond.basic.b2_board.Author.Domain.Role;
import com.beyond.basic.b2_board.Author.Repository.AuthorRepository;
import com.beyond.basic.b2_board.Post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//CommandLineRunner를 구현함으로써 해당 컴포넌트가 스프링빈(싱글톤 객체)으로 등록되는 시점에 run 메서드 자동 실행
@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (authorRepository.findByEmail("admin@naver.com").isPresent()) {
            return;
        }
        Author author = Author.builder()
                .email("admin@naver.com")
                .role(Role.ADMIN)
                .password(passwordEncoder.encode("12341234"))
                .build();

        authorRepository.save(author);
    }
}
