package com.beyond.basic.b2_board.Author.Service;

import com.beyond.basic.b2_board.Author.DTO.*;
import com.beyond.basic.b2_board.Author.Domain.Author;
//import com.beyond.basic.b2_board.Repository.AuthorJdbcRepository;
//import com.beyond.basic.b2_board.Repository.AuthorMemoryRepository;
import com.beyond.basic.b2_board.Author.Repository.AuthorRepository;
import com.beyond.basic.b2_board.Post.domain.Post;
import com.beyond.basic.b2_board.Post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

// transactional 추가설명필요
@Service // == @Component로 대체 가능(트랜잭션 처리가 없는 경우)
@RequiredArgsConstructor
// 스프링에서 '메서드 단위'로 트랜잭션처리(commit)를 하고, 만약 예외(unchecked)발생 시 자동 롤백 처리 지원
@Transactional // 예외처리를 정말 제대로 해줘야 '롤백(서비스 계층)'이 일어남.
public class AuthorService {
////    의존성 주입(DI) 방법1. Autowired 어노테이션 사용 -> 필드 주입 (싱글톤을 갖다가 쓰겠다), 서비스가 1개가 아니라 여러개일수도 있잖아
////    final 키워드를 쓸 수 없다, 다형성 구형 불가, 순환참조안됨
//    @Autowired : 자동으로 주입해주겠다
//    private AuthorRepositoryInterface authorRepository;

////    의존성주입(DI) 방법2. 생성자 주입 방식(가장많이쓰는방식)
////    장점 1) final키워드 사용가능, final을 통해 상수 사용 가능 (안정성 up)
////    장점 2) 다형성 구현 가능 (인터페이스 두고 클래스 설계)
//    private final AuthorMemoryRepositoryInterface authorMemoryRepository;
//    @Autowired
//    public AuthorService(AuthorMemoryRepositoryInterface authorRepository) {
//        this.authorRepository = authorRepository;
//    }
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
//    private final AuthorMemoryRepository authorMemoryRepository;
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    //        객체 조립은 서비스 담당
    public void save(AuthorCreateDto authorCreateDto) {
//        이메일 중복 검증 (DB를 뒤져야 하므로 프론트에서 못함)
        if (authorRepository.findByEmail(authorCreateDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

////        비밀번호 길이 검증 (프론트에서 할 수 있음)
//        if (authorCreateDto.getPassword().length() <= 6) {
//            throw new IllegalArgumentException("길이가 짧습니다.");
//        }
//        Author author = new Author(authorCreateDto.getName(), authorCreateDto.getEmail(), authorCreateDto.getPassword()); // 이건 왜 싱글톤이 안될까? 의문을 가져보자
//        toEntity패턴을 통해 Author 객체 조립을 공통화

        String encodedPassword = passwordEncoder.encode(authorCreateDto.getPassword()); // 암호화된 패스워드, .encode라는 메서드를 사용하기 위해서
        Author author = authorCreateDto.toEntity(encodedPassword); // 하나의 메서드로 정의하여 위와 동일하게 만듦, 코드효율성
//        Author dbAuthor = this.authorRepository.save(author); // 위와 같다, save 내에 리턴타입 자체가 Author이므로

//        OneToMany 내 cascading 테스트 : 회원이 생성될 때, 곧바로 '가입인사' 글을 생성하는 상황
//        방법 2가지. (Junction 테이블 있을 때, 1:N 을 1:1로 만들어줄 때 필요할 것)
//        방법 1. 직접 Post 객체 생성 후 저장 (피지컬로 post 객체 생성 후 저장)
        Post post = Post.builder()
                .title("안녕하세요")
                .contents(authorCreateDto.getName() + "입니다. 반갑습니다.")
//                .author(dbAuthor) // jpa의 영속성 컨텍스트의 이해가 필요하다
//                author 객체가 DB에 save 되는 순간 영속성 컨텍스트, EntityManager에 의해 author 객체에도 id값 생성하여 싱크 맞춰줌
                .author(author)
                .build();
//        postRepository.save(post);

//        방법 2. cascade 옵션 활용 (Repository 없이 저장을 할 수 있다고?!)
//        author가 저장되는 시점에 post가 persist 되게 하겠다
        author.getPostList().add(post);
        this.authorRepository.save(author); // 이런 순서여도 가능, author service에서의 cascade persist 덕분에 근데 작동원리는 EntityManager가 영속성 컨텍스트와 DB의 차이를 계속 트래킹 함

    }



//    public List<Author> findAll() {
//        return authorRepository.findAll();
//    }

//    public List<AuthorListDto> findList() {
//        List<AuthorListDto> dtoList = new ArrayList<>();
//        for (Author a : authorRepository.findAll()) {
////            AuthorListDto dto = new AuthorListDto(a.getId(), a.getName(), a.getPassword());
//            AuthorListDto dto = a.listFromEntity();
//            dtoList.add(dto);
//        }
//        return dtoList; // 한 줄로 끝낼 수 있다고?!?!
//    }

    //    트랜잭션이 필요없는경우, 아래와 같이 명시적으로 제외, 조회는 commit이 필요가 없다 ( 왜 ?? -> 연산작업이 필요가 없다)
// 왜?? -> 트랜잭션 처리 자체가 연산작업이 필요한데, 이를 붙이면 연산 작업이 일어나지 않으므로 성능이 향상된다.
    @Transactional(readOnly = true)
    public List<AuthorListDto> findAll2() {
        return authorRepository.findAll().stream().map(author -> AuthorListDto.listFromEntity(author))
                .collect(Collectors.toList());
    }

//    public Author findById(Long id) throws NoSuchElementException{
//        // Optional 객체, 예외는 다 서비스에서 터뜨려준다.
////        왜? 예외를 터뜨리는건 service에서 하기 때문 -> 스프링에서 예외는 롤백의 기준
////        예외처리는 Controller에서 함
//        Optional<Author> optionalAuthor = authorRepository.findById(id);
//        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("찾고자 하는 아이디가 없습니다."));
//    }

    @Transactional(readOnly = true)
    public AuthorDetailDto findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("아이디없음"));
    //        AuthorDetailDto dto = new AuthorDetailDto(author.getId(), author.getName(), author.getPassword());
    //        AuthorDetailDto dto = author.detailFromEntity();

//        연관관계설정 없이 직접 조회해서 count값 찾는 경우
//        List<Post> postList = postRepository.findByAuthor(author);
//        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author, postList.size());
//        OneToMany연관관계 설정을 통해 count값을 찾는 경우 (postcount값 쓸 때 이게 좋지 않을까?)
        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author);
        return dto;
    }

    public Author findByEmail(String email) {
        Optional<Author> optionalAuthor = authorRepository.findByEmail(email);
        return optionalAuthor.orElseThrow(() -> new NoSuchElementException("찾고자 하는 이메일이 없습니다."));
    }

    public void updatePassword(AuthorUpdatePwDto authorUpdatePwDto) {
       Author author = authorRepository.findByEmail(authorUpdatePwDto.getEmail()).orElseThrow(() -> new NoSuchElementException("찾고자 하는 이메일이 없습니다."));
//       dirty checking : 객체를 수정한 후에 별도의 update 쿼리를 DB에 발생시키지 않아도
//        authorRepository.save, 왜 안해도 되냐?
//       영속성 컨텍스트에 의해 객체의 변경사항 자동으로 DB반영된다. 하지만 add는 안됨, update에서만 가능

       author.updatePassword(authorUpdatePwDto.getPassword());
    }

    public void delete(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("없는 사용자입니다."));
        authorRepository.delete(author);
    }

    public Author doLogin(AuthorLoginDto authorLoginDto) {
//        비밀번호 일치여부 검증
//        boolean match = passwordEncoder.matches(dto.getPassword, author.getPassword); // dto.getPassword는 내부적으로 암호화 될거임
        Optional<Author> optionalAuthor = authorRepository.findByEmail(authorLoginDto.getEmail());
        boolean check = true;
        if (!optionalAuthor.isPresent()) { // 이메일이 없는 경우
            check = false;
        } else {
//            matches함수를 통해 암호화 되지 않은 값을 다시 암호화 하여 DB에 있는 password와 비교하여 검증
//            passwordEncoder.matches(raw Password, DB에 있는 Password)
            if (!passwordEncoder.matches(authorLoginDto.getPassword(), optionalAuthor.get().getPassword())) {
                check = false;
            }
        }
        if (!check) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        } // null인 경우 체크를 해줬음

        return optionalAuthor.get(); // Author 타입의 원본 리턴

    }
}
