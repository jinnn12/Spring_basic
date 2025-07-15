//package com.beyond.basic.b2_board.Service;
//
//import com.beyond.basic.b2_board.DTO.AuthorCreateDto;
//import com.beyond.basic.b2_board.DTO.AuthorDetailDto;
//import com.beyond.basic.b2_board.DTO.AuthorListDto;
//import com.beyond.basic.b2_board.DTO.AuthorUpdatePwDto;
//import com.beyond.basic.b2_board.Domain.Author;
//import com.beyond.basic.b2_board.Repository.AuthorJdbcRepository;
//import com.beyond.basic.b2_board.Repository.AuthorMemoryRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.swing.text.html.Option;
//import java.util.*;
//import java.util.stream.Collectors;
//
//// transactional 추가설명필요
//@Service // == @Component로 대체 가능(트랜잭션 처리가 없는 경우)
//@RequiredArgsConstructor
//// 스프링에서 '메서드 단위'로 트랜잭션처리를 하고, 만약 예외(unchecked)발생 시 자동 롤백 처리 지원
//@Transactional // 예외처리를 정말 제대로 해줘야 '롤백(서비스 계층)'이 일어남.
//public class AuthorService {
//////    의존성 주입(DI) 방법1. Autowired 어노테이션 사용 -> 필드 주입 (싱글톤을 갖다가 쓰겠다), 서비스가 1개가 아니라 여러개일수도 있잖아
//////    final 키워드를 쓸 수 없다, 다형성 구형 불가, 순환참조안됨
////    @Autowired : 자동으로 주입해주겠다
////    private AuthorRepositoryInterface authorRepository;
//
//////    의존성주입(DI) 방법2. 생성자 주입 방식(가장많이쓰는방식)
//////    장점 1) final키워드 사용가능, final을 통해 상수 사용 가능 (안정성 up)
//////    장점 2) 다형성 구현 가능 (인터페이스 두고 클래스 설계)
////    private final AuthorMemoryRepositoryInterface authorMemoryRepository;
////    @Autowired
////    public AuthorService(AuthorMemoryRepositoryInterface authorRepository) {
////        this.authorRepository = authorRepository;
////    }
//////    장점 3) 순환참조방지 (컴파일타임에 check), (R - S - C 순서대로 싱글톤객체가 생성이 안될때?)
////    private final AuthorRepositoryInterface authorRepository;
//////    객체로 만들어지는 시점에 생성자(new)가 만들어지는데 스프링에서 authorRepository 객체를 매개변수로 주입
/////     생성자가 1개 밖에 없을 때에는 Autowired 생략 가능, 즉 밑의 경우는 생략이 가능, 결론은 까먹지말고 붙이자!
////    @Autowired : 자동으로 주입해주겠다, (AuthorMemoryRepository authorRepository)를
////    public AuthorService(AuthorMemoryRepository authorRepository) {
////        this.authorRepository = authorRepository;
////    }
//
////    의존성주입(DI) 방법3. RequiredArgs 어노테이션 사용
////    -> 반드시 초기화 되어야 하는 필드(final 등)을 대상으로 생성자를 자동 생성해주는 어노테이션
////    -> 다형성 설계는 불가하다
////    private final AuthorMemoryRepository authorMemoryRepository;
//    private final AuthorJdbcRepository authorRepository;
//
//    //        객체 조립은 서비스 담당
//    public void save(AuthorCreateDto authorCreateDto) {
////        이메일 중복 검증 (DB를 뒤져야 하므로 프론트에서 못함)
//        if (authorRepository.findByEmail(authorCreateDto.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
//        }
//////        비밀번호 길이 검증 (프론트에서 할 수 있음)
////        if (authorCreateDto.getPassword().length() <= 6) {
////            throw new IllegalArgumentException("길이가 짧습니다.");
////        }
////        Author author = new Author(authorCreateDto.getName(), authorCreateDto.getEmail(), authorCreateDto.getPassword()); // 이건 왜 싱글톤이 안될까? 의문을 가져보자
////        toEntity패턴을 통해 Author 객체 조립을 공통화
//        Author author = authorCreateDto.authorToEntity(); // 하나의 메서드로 정의하여 위와 동일하게 만듦, 코드효율성
//        this.authorRepository.save(author);
//    }
//
//    public List<Author> findAll() {
//        return authorRepository.findAll();
//    }
//
////    트랜잭션이 필요없는경우, 아래와 같이 명시적으로 제외
//    @Transactional(readOnly = true)
//    public List<AuthorListDto> findAll2() {
//        return authorRepository.findAll().stream().map(a -> a.listFromEntity())
//                .collect(Collectors.toList());
//    }
//
//    public List<AuthorListDto> findList() {
//        List<AuthorListDto> dtoList = new ArrayList<>();
//        for (Author a : authorRepository.findAll()) {
////            AuthorListDto dto = new AuthorListDto(a.getId(), a.getName(), a.getPassword());
//            AuthorListDto dto = a.listFromEntity();
//            dtoList.add(dto);
//        }
//        return dtoList; // 한 줄로 끝낼 수 있다고?!?!
//    }
//
//    //    트랜잭션이 필요없는경우, 아래와 같이 명시적으로 제외
//    @Transactional(readOnly = true)
//    public Author findById(Long id) throws NoSuchElementException{
//        // Optional 객체, 예외는 다 서비스에서 터뜨려준다.
////        왜? 예외를 터뜨리는건 service에서 하기 때문 -> 스프링에서 예외는 롤백의 기준
////        예외처리는 Controller에서 함
//        Optional<Author> optionalAuthor = authorRepository.findById(id);
//        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("찾고자 하는 아이디가 없습니다."));
//    }
//
//    public Author findByEmail(String email) {
//        Optional<Author> optionalAuthor = authorRepository.findByEmail(email);
//        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("찾고자 하는 이메일이 없습니다."));
//    }
//
//    public AuthorDetailDto findByDtoId(Long id) {
//        Author author = authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("아이디없음"));
////        AuthorDetailDto dto = new AuthorDetailDto(author.getId(), author.getName(), author.getPassword());
////        AuthorDetailDto dto = author.detailFromEntity();
//        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author);
//        return dto;
//    }
//
//    public void updatePassword(AuthorUpdatePwDto authorUpdatePwDto) {
//       Author author = authorRepository.findByEmail(authorUpdatePwDto.getEmail()).orElseThrow(() -> new NoSuchElementException("찾고자 하는 이메일이 없습니다."));
//       author.updatePassword(authorUpdatePwDto.getPassword());
//    }
//
//    public void delete(Long id) {
//        authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("없는 사용자입니다."));
//        authorRepository.delete(id);
//    }
//
//
//
//}
