package com.beyond.basic.b2_board.Post.service;

import com.beyond.basic.b2_board.Author.Domain.Author;
import com.beyond.basic.b2_board.Author.Repository.AuthorRepository;
import com.beyond.basic.b2_board.Post.domain.Post;
import com.beyond.basic.b2_board.Post.dto.PostCreateDto;
import com.beyond.basic.b2_board.Post.dto.PostDetailDto;
import com.beyond.basic.b2_board.Post.dto.PostListDto;
import com.beyond.basic.b2_board.Post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Author author = authorRepository.findById(postCreateDto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("없는 사용자입니다."));
        postRepository.save(postCreateDto.toEntity(author));
    }

    public List<PostListDto> findAll() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(a -> PostListDto.fromEntity(a)).collect(Collectors.toList());
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
