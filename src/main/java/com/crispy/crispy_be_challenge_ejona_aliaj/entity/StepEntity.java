package com.crispy.crispy_be_challenge_ejona_aliaj.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "T_STEP")
public class StepEntity extends AuditEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SEQ_T_STEP", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_T_STEP", sequenceName = "SEQ_T_STEP", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

}
