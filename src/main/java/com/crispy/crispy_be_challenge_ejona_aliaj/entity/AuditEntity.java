package com.crispy.crispy_be_challenge_ejona_aliaj.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity {

    @Column(name = "CRT_TIME", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "ID_USR_CRT")
    @CreatedBy
    private Long createdBy;

    @Column(name = "MOD_TIME", nullable = false, updatable = false)
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column(name = "ID_USR_MOD")
    @LastModifiedBy
    private Long modifiedBy;

}
