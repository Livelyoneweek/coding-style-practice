package com.callbus.mission.entity;

import com.callbus.mission.dto.AccountType;
import com.callbus.mission.dto.Quit;
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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Quit quit;

    public Member(String nickName, AccountType accountType, String accountId,
                  String password, Quit quit) {
        this.nickName = nickName;
        this.accountType = accountType;
        this.accountId = accountId;
        this.password = password;
        this.quit = quit;
    }
}
