package com.example.springbootapi.model;

import lombok.*;

import javax.persistence.*;

// 어노테이션 : 코드에 대해 대략적인 어떤 기능을 하겠다 라는 메타정보를 나타내는 것
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String number;

    private String email;

    // MemberCreateRequest -> Member 로 변환
    public static Member toEntity(MemberCreateRequest request) {
        return Member.builder()
                .name(request.getName())
                .number(request.getNumber())
                .email(request.getEmail())
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }
}