//package com.beyond.basic.b2_board_practice3.Repository;
//
//import com.beyond.basic.b2_board_practice3.Domain.Author;
//import org.springframework.stereotype.Repository;
//import java.util.*;
//
//@Repository
//
//public class AuthorRepository {
//    List<Author> authorList = new ArrayList<>();
//    public static Long id = 1L;
//
//    public void save(Author author) {
//        authorList.add(author);
//        id++;
//    }
//
//    public List<Author> findAll() {
//        return this.authorList;
//    }
//
//    public Optional<Author> findById(Long id) {
//        return authorList.stream().filter(author -> author.getId().equals(id)).findFirst();
//    }
//
//    public Optional<Author> findByEmail(String email) {
//        return authorList.stream().filter(author -> author.getEmail().equals(email)).findFirst();
//    }
//
//    public void delete(Long id) {
//        for (int i = 0; i < authorList.size(); i++) {
//            Long findId = authorList.get(i).getId();
//            if (findId.equals(id)) {
//                authorList.remove(i);
//                break;
//            }
//        }
//    }
//}
