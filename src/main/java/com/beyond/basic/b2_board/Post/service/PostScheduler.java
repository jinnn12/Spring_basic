package com.beyond.basic.b2_board.Post.service;

import com.beyond.basic.b2_board.Post.domain.Post;
import com.beyond.basic.b2_board.Post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional // update -> insert 순으로 진행 돼야 하므로
// 스케줄러 서버를 2대 이상 운영할 경우 중복 스케줄이 돌아갈 여지가 있으므로, redis 등의 솔루션을 사용하여 스케줄 제어
public class PostScheduler {
    private final PostRepository postRepository;
//    cron의 각 자리는 "초 분 시간 일 월 요일"을 의미
//    * * * * * * : 매월, 매일, 매시간, 매분, 매초마다 돌아감
//    0 0 * * * * : 매월, 매일, 매시간, 0분, 0초에 돌아감
//    0 0 11 * * * : 매월, 매일, 11시, 0분, 0초에 돌아감
//    0 0/1 * * * * : 매월, 매일, 매시, 1분마다

    @Scheduled(cron = "0 0/1 * * * *")
    public void postScheduled() {
        log.info("===예약스케줄러 시작===");
        List<Post> posts = postRepository.findByAppointment("Y");
        LocalDateTime now = LocalDateTime.now(); // now 시간 내장 메서드
        for (Post p : posts) {
            if (p.getAppointmentTime().isBefore(now)) {
                p.updateAppointment("N");
            }
        }
        log.info("===예약스케줄러 끝===");
    }
}
