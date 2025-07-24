package com.beyond.basic.b2_board.Post.service;

import com.beyond.basic.b2_board.Author.Domain.Author;
import com.beyond.basic.b2_board.Author.Repository.AuthorRepository;
import com.beyond.basic.b2_board.Post.domain.Post;
import com.beyond.basic.b2_board.Post.dto.PostCreateDto;
import com.beyond.basic.b2_board.Post.dto.PostDetailDto;
import com.beyond.basic.b2_board.Post.dto.PostListDto;
import com.beyond.basic.b2_board.Post.dto.PostSearchDto;
import com.beyond.basic.b2_board.Post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional

public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository; // 두개의 싱글톤이 주입이 될 수 있다.

    // 서비스를 호출할 때 예외처리까지 해주는 놈이기 때문에 가져오면 참 좋은데... 순환참조이슈가 생길 수 있다.
    // 즉 Repository를 가져와서 조립을 해주면 좋을 것 같다. 정답은 아니나, 문제를 덜 일으킨다.
    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostCreateDto postCreateDto) {
//        authorId가 실제 있는지 없는지 검증필요, 이제는 할 필요가 없다. author를 넣을거잖아?
//        author를 save하면 JPA가 id만 쏙 빼서 DB에 저장할 것
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 저장은 filter에서
        String email = authentication.getName(); // claims의 subject : email / claims에 id를 설정했다면 getName 시 id가 나오는거고,,
        System.out.println(email);
//        Author author = authorRepository.findById(postCreateDto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("없는 사용자입니다.")); // 원래는 직접 authorId 넣어줘야 하는데, 이제는 로그인하여 Authentication에서 알아서 만들어준다
        Author author = authorRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("없는 사용자입니다."));
        LocalDateTime appointmentTime = null;
        if (postCreateDto.getAppointment().equals("Y")) {
            if (postCreateDto.getAppointmentTime() == null || postCreateDto.getAppointmentTime().isEmpty()) {
                throw new IllegalArgumentException("시간정보가 비어있습니다");
            }
            System.out.println("확인 : " + postCreateDto.getAppointment());
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm"); // 기본적으로 LocalDateTime은 초까지 세팅해야 하는데 타임포매터로 직접 원하는 입력값을 커스텀
            appointmentTime = LocalDateTime.parse(postCreateDto.getAppointmentTime(), dateTimeFormatter);
        }
        postRepository.save(postCreateDto.toEntity(author, appointmentTime));
    }

//    PostList를 조회할 때 참조관계에 있는 author까지 조회하게 되므로, N(author 쿼리) + 1(Postlist 조회쿼리) 문제 발생
//    jpa는 기본방향성이 fetch LAZY이므로 참조하는시점에 쿼리를 내보내게 되어 N+1문제 발생 -> 직접 JOIN 쿼리 작성 -> fetch join 흐름으로 발전
    public Page<PostListDto> findAll(Pageable pageable, PostSearchDto postSearchDto) {
//        List<Post> postList = postRepository.findAll();
//        List<Post> postList = postRepository.findAllJoin();
//        List<Post> postList = postRepository.findAllFetchJoin();

//        그냥 findAll
//        List<Post> postList = postRepository.findAll();
//        return postList.stream().map(a -> PostListDto.fromEntity(a)).collect(Collectors.toList());

//        페이지 처리 findAll 호출
//        Page 내부적으로 stream api 있다??
//        검색을 위해 Specification 객체 스프링에서 제공
//        Specification객체는 복잡한 쿼리를 명세를 이용하여 정의하는 방식으로, 쿼리를 쉽게 생성 (직접하려면 분기처리 if elseif .. )
//        Page<Post> postList = postRepository.findAllByDelYnAndAppointment(pageable, "N", "N"); And뭐And뭐And뭐... 존나길잖아? Specification 쓰자
        Specification<Post> specification = new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                Root 객체 : 엔티티의 속성을 접근하기 위한 객체, CriteriaBuilder 객체 : 쿼리를 생성하기 위한 객체, Predicate : & 조건 & 조건 .. 이런거
                List<Predicate> predicateList = new ArrayList<>(); // 검색조건을 List에 하나씩 담아보자
//                select * from post where del_yn="N" and
                predicateList.add(criteriaBuilder.equal(root.get("delYn"), "N")); // query객체=()
//                select * from post where del_yn="N" and appointment="N"
                predicateList.add(criteriaBuilder.equal(root.get("appointment"), "N"));
//                select * from post where del_yn="N" and appointment="N" and category inputCategory and title like "%inputTitle%"
                if (postSearchDto.getCategory() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("category"), postSearchDto.getCategory()));
                }
                if (postSearchDto.getTitle() != null) {
                    predicateList.add(criteriaBuilder.like(root.get("title"), "%"+postSearchDto.getTitle()+"%"));
                }
                Predicate[] predicateArr = new Predicate[predicateList.size()];
                for (int i = 0; i < predicateList.size(); i++) {
                    predicateArr[i] = predicateList.get(i);
                }

//                위의 검색 조건들을 하나(한줄)의 Predicate 객체로 만들어서 리턴
                Predicate predicate = criteriaBuilder.and(predicateArr);
                return predicate;
            }

        };
        Page<Post> postList = postRepository.findAll(specification, pageable);
        return postList.map(a -> PostListDto.fromEntity(a));
    }

    public PostDetailDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없는 아이디입니다.")); // EntityNotFound..!
//        1.엔티티간의 관계성 설정을 하지 않았을 경우
//        Author author = authorRepository.findById(post.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));
//        return PostDetailDto.fromEntity(post, author); // 어차피 post를 조회하는 것이 author를 조회하는거자나

//        2.엔터티간 관계성 설정을 통해 author 객체를 쉽게 조회하는 경우
        return PostDetailDto.fromEntity(post);
    }








}
