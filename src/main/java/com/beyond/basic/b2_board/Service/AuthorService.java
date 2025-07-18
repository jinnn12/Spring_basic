package com.beyond.basic.b2_board.Service;

import com.beyond.basic.b2_board.DTO.AuthorCreateDto;
import com.beyond.basic.b2_board.DTO.AuthorDetailDto;
import com.beyond.basic.b2_board.DTO.AuthorListDto;
import com.beyond.basic.b2_board.DTO.AuthorUpdatePwDto;
import com.beyond.basic.b2_board.Domain.Author;
import com.beyond.basic.b2_board.Repository.AuthorMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

// transactional 추가설명필요
@Service // == @Component로 대체 가능(트랜잭션 처리가 없는 경우)
@RequiredArgsConstructor
public class AuthorService {
////    의존성 주입(DI) 방법1. Autowired 어노테이션 사용 -> 필드 주입 (싱글톤을 갖다가 쓰겠다), 서비스가 1개가 아니라 여러개일수도 있잖아
////    final 키워드를 쓸 수 없다, 다형성 구형 불가, 순환참조안됨
//    @Autowired : 자동으로 주입해주겠다
//    private AuthorRepositoryInterface authorRepository;

////    의존성주입(DI) 방법2. 생성자 주입 방식(가장많이쓰는방식)
////    장점 1) final키워드 사용가능, final을 통해 상수 사용 가능 (안정성 up)
////    장점 2) 다형성 구현 가능
////    장점 3) 순환참조방지 (컴파일타임에 check), (R - S - C 순서대로 싱글톤객체가 생성이 안될때?)
//    private final AuthorRepositoryInterface authorRepository;
////    객체로 만들어지는 시점에 생성자(new)가 만들어지는데 스프링에서 authorRepository 객체를 매개변수로 주입
///     생성자가 1개 밖에 없을 때에는 Autowired 생략 가능, 즉 밑의 경우는 생략이 가능, 결론은 까먹지말고 붙이자!
//    @Autowired : 자동으로 주입해주겠다, (AuthorMemoryRepository authorRepository)를
//    public AuthorService(AuthorMemoryRepository authorRepository) {
//        this.authorRepository = authorRepository;
//    }

//    의존성주입(DI) 방법3. RequiredArgs 어노테이션 사용
//    -> 반드시 초기화 되어야 하는 필드(final 등)을 대상으로 생성자를 자동 생성해주는 어노테이션
//    -> 다형성 설계는 불가하다
    private final AuthorMemoryRepository authorMemoryRepository;

    //        객체 조립은 서비스 담당
    public void save(AuthorCreateDto authorCreateDto) {
//        이메일 중복 검증 (DB를 뒤져야 하므로 프론트에서 못함)
        if (authorMemoryRepository.findByEmail(authorCreateDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
////        비밀번호 길이 검증 (프론트에서 할 수 있음)
//        if (authorCreateDto.getPassword().length() <= 6) {
//            throw new IllegalArgumentException("길이가 짧습니다.");
//        }
        Author author = new Author(authorCreateDto.getName(), authorCreateDto.getEmail(), authorCreateDto.getPassword()); // 이건 왜 싱글톤이 안될까? 의문을 가져보자
        this.authorMemoryRepository.save(author);
    }

    public List<Author> findAll() {
        return authorMemoryRepository.findAll();
    }

    public List<AuthorListDto> findList() {
        List<AuthorListDto> dtoList = new ArrayList<>();
        for (Author a : authorMemoryRepository.findAll()) {
            AuthorListDto dto = new AuthorListDto(a.getId(), a.getName(), a.getPassword());
            dtoList.add(dto);
        }
        return dtoList; // 한 줄로 끝낼 수 있다고?!?!
    }

    public Author findById(Long id) throws NoSuchElementException{
        // Optional 객체, 예외는 다 서비스에서 터뜨려준다.
//        왜? 예외를 터뜨리는건 service에서 하기 때문 -> 스프링에서 예외는 롤백의 기준
//        예외처리는 Controller에서 함
        Optional<Author> optionalAuthor = authorMemoryRepository.findById(id);
        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("찾고자 하는 아이디가 없습니다."));
    }

    public AuthorDetailDto findByDtoId(Long id) {
        Author author = authorMemoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("아이디없음"));
        AuthorDetailDto dto = new AuthorDetailDto(author.getId(), author.getName(), author.getPassword());
        return dto;
    }

    public void updatePassword(AuthorUpdatePwDto authorUpdatePwDto) {
       Author author = authorMemoryRepository.findByEmail(authorUpdatePwDto.getEmail()).orElseThrow(() -> new NoSuchElementException("찾고자 하는 이메일이 없습니다."));
       author.updatePassword(authorUpdatePwDto.getPassword());
    }

    public void delete(Long id) {
        authorMemoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("없는 사용자입니다."));
        authorMemoryRepository.delete(id);
    }



}
