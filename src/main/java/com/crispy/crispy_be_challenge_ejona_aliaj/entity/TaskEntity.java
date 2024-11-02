package com.crispy.crispy_be_challenge_ejona_aliaj.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "T_TASK")
public class TaskEntity extends AuditEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SEQ_T_TASK", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_T_TASK", sequenceName = "SEQ_T_TASK", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    @Column(name = "COMPLETED")
    private Boolean completed;

    @Column(name = "IMPORTANT")
    private Boolean important;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    private ProjectEntity project;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task", orphanRemoval = true)
    private List<StepEntity> steps;

    @Column(name = "USER_ID")
    private Long userId;

}
