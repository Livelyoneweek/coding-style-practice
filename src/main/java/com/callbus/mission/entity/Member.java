package com.callbus.mission.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 12)
    private String nickName;

    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false, length = 15)
    private String accountId;

    @Column(nullable = false, length = 15)
    private String password;

    @Column(nullable = false)
    private Quit quit;
}
