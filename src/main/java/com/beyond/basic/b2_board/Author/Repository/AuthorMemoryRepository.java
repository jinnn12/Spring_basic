package com.beyond.basic.b2_board.Author.Repository;

import com.beyond.basic.b2_board.Author.Domain.Author;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository // 안에 @Component가 있음 (싱글톤객체로 만들어주는)
public class AuthorMemoryRepository {

    private List<Author> authorList = new ArrayList<>(); //rdb대신 메모리에 관리하겠다, 왜 static을 쓰지 않았지?
    public static Long id = 1L;

    public void save(Author author) {
        this.authorList.add(author);
        id++;
    }

    public List<Author> findAll() {
        return this.authorList;
    }

    public Optional<Author> findById(Long id) { // 있을수도 없을수도 있으므로 optional, ofNullable()
        return authorList.stream().filter(author -> author.getId().equals(id)).findFirst();
//        Author author = null;
//        for (Author a : this.authorList) {
//            if (a.getEmail().equals(id)) {
//                author = a;
//                break;
//            }
    }

    public Optional<Author> findByEmail(String email) { // 있을수도 없을수도 있으므로 optional
        return authorList.stream().filter(author -> author.getEmail().equals(email)).findFirst();
    }

    public void delete(Long id) {
//        id값으로 요소의 index값을 찾아 삭제 (id - 1? 이지 않을까, id는 1부터 시작, index는 0부터 시작이므로)
        for (int i = 0; i < this.authorList.size(); i++) {
            Long findId = this.authorList.get(i).getId();
            if (findId.equals(id)) {
                this.authorList.remove(i);
                break;
            }
        }

    }
}



