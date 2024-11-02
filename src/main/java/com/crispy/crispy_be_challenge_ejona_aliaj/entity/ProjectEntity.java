package com.crispy.crispy_be_challenge_ejona_aliaj.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "T_PROJECT")
public class ProjectEntity extends AuditEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SEQ_T_PROJECT", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_T_PROJECT", sequenceName = "SEQ_T_PROJECT", allocationSize = 1)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    private List<TaskEntity> tasks;

    @Column(name = "USER_ID")
    private Long userId;

}
