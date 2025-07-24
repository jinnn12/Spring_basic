package com.beyond.basic.b3_servlet;

import com.beyond.basic.b1_hello.practice.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

// 서블릿은 사용자의 request를 쉽게 처리하고, 사용자에게 response를 쉽게 조립해주는 기술이다
// 서블릿에서는 url 매핑을 메서드 단위가 아닌 클래스 단위로 지정한다

// controller가 없이도 HttpServletRequest, Response를 활용해 사용자와 인터페이싱 할 수 있다를 보면 된다.

// 컨트롤러와 차이점 : json을 직접 조립하여 넣어줘야 함
@WebServlet("/servlet/get")
public class ServletRestGet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Hello hello = new Hello();
        hello.setName("Hong");
        hello.setEmail("hong@naver.com");
//        Header
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(hello); // 두개만 기억해 writeValueAsString, readValue

//        Body
        PrintWriter printWriter = resp.getWriter();
        printWriter.print("abc");
        printWriter.flush();
    }

}
