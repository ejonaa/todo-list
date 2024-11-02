package com.crispy.crispy_be_challenge_ejona_aliaj.repository;

import com.crispy.crispy_be_challenge_ejona_aliaj.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    Page<ProjectEntity> findByUserId(Long userId, Pageable pageable);

    Optional<ProjectEntity> findByIdAndUserId(Long id, Long userId);
}
