//package com.beyond.basic.b2_board.Controller;
//// 사용자와 커뮤니케이션 : Controller
//
//import com.beyond.basic.b2_board.DTO.AuthorCreateDto;
//import com.beyond.basic.b2_board.DTO.AuthorDetailDto;
//import com.beyond.basic.b2_board.DTO.AuthorListDto;
//import com.beyond.basic.b2_board.DTO.AuthorUpdatePwDto;
//import com.beyond.basic.b2_board.Domain.Author;
//import com.beyond.basic.b2_board.Service.AuthorService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@RestController // @Controller + @ResponseBody (데이터만 주는 방식을 사용할 때 쓴다)
//@RequiredArgsConstructor
//@RequestMapping("/author")
//public class AuthorController {
//    private final AuthorService authorService;
//
//    //    회원가입 : 회원가입을 위한 클래스 DTO를 따로 만들어야 한다, author 객체는 Long id, 가입일시 등
////    사용자가 입력할 필요가 없는 것이 많기 때문이다. long id, 가입일시 등은 DB에 저장되는 용도
////    DTO를 만들어서 매개변수로 받고, 이를 다시 Author 객체에 넣어줘서 DB에 저장해야 함 // /author/create
//    @PostMapping("/create")
////    <String> body부의 타입
//    public ResponseEntity<?> save(@RequestBody AuthorCreateDto authorCreateDto) { // ?는 모든 객체
////        try {
////            this.authorService.save(authorCreateDto);
////            ResponseEntity<String> response = new ResponseEntity<>("OK", HttpStatus.CREATED); // CREATED : 201
////            return response;
////        } catch (IllegalArgumentException e) {
////            e.printStackTrace();
//////            생성자 매개변수 body 부분의 객체와 header부에 상태코드
////            ResponseEntity<String> response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
////            return response;
////        }
////    }
////        @controllerAdvice가 없었으면 위와 같이 개별적인 예외처리가 필요하나, 이제는 전역적인 예외처리가 가능
//        this.authorService.save(authorCreateDto);
//        ResponseEntity<String> response = new ResponseEntity<>("OK", HttpStatus.CREATED); // CREATED : 201
//        return response;
//    }
//
//
//    //    회원목록조회 : /author/list
//    @GetMapping("/list")
//    public List<AuthorListDto> findList() {
//        return authorService.findAll2();
//    }
//
//    //    회원상세조회 (Id로 조회) : /author/detail/1
////    서버에서 별도의 try catch를 하지 않으면, 에러 발생 시 500에러 + 스프링의 포맷으로 에러를 리턴
//    @GetMapping("/detail/{id}")
//    public ResponseEntity<?> findById(@PathVariable Long id) {
//        try {
//            return new ResponseEntity<>(authorService.findById(id), HttpStatus.OK);
////            ResponseEntity<String> response = new ResponseEntity<>("OK", HttpStatus.OK); // 조회는 보통 oK
////            return response;
//        } catch (NoSuchElementException e) {
//            e.printStackTrace();
//            ResponseEntity<String> response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//            System.out.println(response);
//            return response;
//        }
//    }
//
////    @GetMapping("/detail/{inputId}")
////    public AuthorDetailDto findById(@PathVariable Long inputId) {
////        try {
////            return authorService.findByDtoId(inputId); // 관례상 메서드명에는 dto를 사용하지 않는다
////        } catch (IllegalArgumentException e) {
////            e.printStackTrace();
////            throw new NoSuchElementException();
////        }
////    }
//
//
//    //    비밀번호수정 : email로 password 수정 -> json, /author/updatepw
////    get:조회, post:등록, patch:부분수정, put:대체, delete:삭제
//    @PatchMapping("/updatepassword")
//    public void updatePw(@RequestBody AuthorUpdatePwDto authorUpdatePwDto) {
//        authorService.updatePassword(authorUpdatePwDto);
//    }
//
//
//    //    회원탈퇴(삭제) : /author/delete/1 , 실무에서는 실제로 delete라고 해놓고 patch로 진행
//    @DeleteMapping("/delete/{inputId}")
//    public void delete(@PathVariable Long inputId) {
//        authorService.delete(inputId);
//    }
//}
