package com.beyond.basic.b2_board_practice.Repository;

import com.beyond.basic.b2_board_practice.Domain.Author;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.*;

@Repository
public class AuthorMemoryRepository {
    List<Author> authorList = new ArrayList<>();
    public static Long id = 1L;

    public void save(Author author) {
        authorList.add(author);
        id++;
    }

    public List<Author> findAll() {
        return this.authorList;
    }

    public Optional<Author> findById(Long id) {
        return authorList.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public Optional<Author> findByEmail(String email) {
        return authorList.stream().filter(a -> a.getEmail().equals(email)).findFirst();
    }

    public void delete(Long id) {
        for (int i = 0; i < authorList.size(); i++) {
            Long findId = authorList.get(i).getId();
            if (findId.equals(id)) {
                authorList.remove(i);
                break;
            }
        }
    }


}



