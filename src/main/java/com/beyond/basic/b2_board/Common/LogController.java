package com.beyond.basic.b2_board.Common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

// logback 객체 만드는 방법2.
@Slf4j
public class LogController {
////    logback 객체 만드는 방법1.
//    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log/test")
    public String logTest() {
//        기존의 system print는 성능이 떨어지므로 spring에서는 잘 사용되지 않음
//        이유 1. 성능이 떨어짐 / 이유 2. 로그분류 작업 불가
        System.out.println("hello world");

//        가장 많이 사용되는 로그 라이브러리 : logback (!)
//        로그레벨(프로젝트차원에 설정) : trace < debug < info < error
//        logger.trace("trace 로그 입니다.");
//        logger.debug("debug 로그 입니다.");
//        logger.info("info 로그 입니다.");
//        logger.error("error 로그 입니다.");
//        return "ok";

//        @Slf4j 선언 시, log 라는 변수로 logback객체 사용 가능
        log.info("slf4j 테스트");

        try {
            log.info("에러테스트 시작");
            throw new RuntimeException("에러 테스트");
        } catch (RuntimeException e) {
//            +보다, ,로 두 메시지를 출력하는 것이 성능이 더 좋음
            log.error("에러메시지 : ", e);
//            e.printStackTrace(); // 성능문제로 logback 쓰는 게 더 좋음
        }
        return "ok";
    }
}
