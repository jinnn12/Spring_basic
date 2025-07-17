package com.beyond.basic.b2_board.Author.Repository;

import com.beyond.basic.b2_board.Author.Domain.Author;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorJpaRepository {
    @Autowired // 다형성, 재할당 필요 없으니 이렇게 써도 됨
    private EntityManager entityManager;

    public void save(Author author) {
//        persist : 순수JPA에서 데이터를 insert하는 메서드
        entityManager.persist(author);
    }

    public List<Author> findAll() {
//        순수 Jpa에서는 제한된 메서드 제공으로 인해 jpql을 사용하여 직접 쿼리를 작성하는 경우가 많다.
//        jpql : jpql문법은 문자열 형식의 raw쿼리가 아닌, 객체지향쿼리문이다.
//        jpql 작성 규칙 : DB 테이블명/컬럼명이 아니라, "엔티티명/필드명"을 기준으로 사용, 별칭(alias)를 활용
//        엔티티(클래스) : Author / alias : a / a.name, a.email
        List<Author> authorList = entityManager.createQuery("select a from Author a", Author.class).getResultList();
        return authorList;
    }

    public Optional<Author> findById(Long id) { // 있을수도 없을수도 있으므로 optional, ofNullable()
//        find : pk로 조회하는 경우에 select문 자동완성 (자동으로 select * from author where id=?) 이 붙어 있음
        Author author = entityManager.find(Author.class, id);
        return Optional.ofNullable(author);
    }

    public Optional<Author> findByEmail(String email) {
        Author author = null;
        try {
            author = entityManager.createQuery("select a from Author a where a.email = :email", Author.class)
                    .setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
        return Optional.ofNullable(author);
    }

    public void delete(Long id) {
//        remove(객체) : 객체삭제
        Author author = entityManager.find(Author.class, id);
        if (author != null) { // 영속성컨텍스트를 활용해 있나없나 확인?
            entityManager.remove(author);
        }
    }
}
