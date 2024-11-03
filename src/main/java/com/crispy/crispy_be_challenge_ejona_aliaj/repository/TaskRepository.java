package com.crispy.crispy_be_challenge_ejona_aliaj.repository;

import com.crispy.crispy_be_challenge_ejona_aliaj.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findByIdAndProjectIdAndUserId(Long id, Long projectId, Long userId);

}
