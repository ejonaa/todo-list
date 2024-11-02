package com.crispy.crispy_be_challenge_ejona_aliaj.repository;

import com.crispy.crispy_be_challenge_ejona_aliaj.entity.StepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<StepEntity, Long> {
}
