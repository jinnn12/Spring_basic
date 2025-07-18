package com.beyond.basic.b2_board.Post.repository;

import com.beyond.basic.b2_board.Author.Domain.Author;
import com.beyond.basic.b2_board.Post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    select * from post where author_id = ? and title = ? (? 자리에 매개변수)
//    List<Post> findByAuthorIdAndTitle(Long id, String title);

//    select * from post where author_id = ? and title = ? order by createdTime desc;
//    List<Post> findByAuthorIdAndTitleOrderByCreatedTimeDesc(Long id, String title); // 나는 날짜로 정렬하겠다

//    findBy"컬럼명"이 기본이나, AuthorId도 조회 가능
//    List<Post> findByAuthorId(Long authorId);
//    보통은 객체로 찾는 것이 일반적이다
    List<Post> findByAuthor(Author author);

//    jpql을 사용한 일반 inner join
//    jpql는 기본적으로 LAZY 로딩을 지향하다보니, inner join으로 필터링은 하되 'post'객체만 조회 -> N+1문제 여전히 발생...
//    raw쿼리 : select 'p.*' from post p inner join author a on a.id = p.author_id;
    @Query("select p from Post p inner join p.author")
    List<Post> findAllJoin();


//    jpql을 사용한 fetch inner join
//    join시 post 뿐 아니라 author객체까지 한꺼번에 조립하여 조회 -> N+1 문제 해결
//    raw쿼리 : select '*' from post p inner join author a on a.id=p.aurthor_id
    @Query("select p from Post p inner join fetch p.author")
    List<Post> findAllFetchJoin();

//    paging 처리 + delyn 적용
//    Pageble : import com.beyond.basic.b2_board.Post.domain.Post;
//    Page 객체 안에 List<Post>, 전체페이지 수 등의 정보 포함
//    Pageable객체 안에는 페이지 size, 페이지 번호정렬기준 등이 포함
//    By가 where조건이라고 생각하면 된다
   Page<Post> findAllByDelYn(Pageable pageable, String delYn);



}
