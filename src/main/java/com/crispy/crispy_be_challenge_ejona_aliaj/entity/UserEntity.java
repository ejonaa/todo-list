package com.crispy.crispy_be_challenge_ejona_aliaj.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "T_USER")
public class UserEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SEQ_T_USER", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_T_USER", sequenceName = "SEQ_T_USER", allocationSize = 1)
    private Long id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD_HASH")
    private String passwordHash;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DELETED")
    private Boolean deleted;

    @Column(name = "CRT_TIME")
    private LocalDateTime createdDate;

    @Column(name = "MOD_TIME")
    private LocalDateTime modifiedDate;

}
