package com.beyond.basic.b2_board.Author.Repository;

import com.beyond.basic.b2_board.Author.Domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Spring data JPA를 사용하기 위해서는 JpaRepository를 상속 해야 되고, 상속 시 extends JpaRepository(Entity명, PK타입)
// JpaRepository를 상속함으로써 JpaRepository의 주요기능(각종 CRUD 기능이 사전 구현)을 상속
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    //    save, findAll, findById, delete 등은 사전에 구현돼 있음.
    //    그 외에 다른 컬럼으로 조회할 때는 findBy + 컬럼명 형식으로 선언만하면 실행시점에 자동 구현.
    Optional<Author> findByEmail(String email);

}
