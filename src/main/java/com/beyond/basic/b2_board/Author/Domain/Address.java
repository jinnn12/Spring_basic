package com.beyond.basic.b2_board.Author.Domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Address {
    @Id // @Id : PK로 설정하겠다는 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String street;
    private String zipCode;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", unique = true)
    private Author author;
}
