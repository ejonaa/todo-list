package com.crispy.crispy_be_challenge_ejona_aliaj.repository;

import com.crispy.crispy_be_challenge_ejona_aliaj.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<ProjectEntity, Long> {

    Page<ProjectEntity> findByUserId(Long userId, Pageable pageable);

    Optional<ProjectEntity> findByIdAndUserId(Long id, Long userId);

    Long deleteByIdAndUserId(Long id, Long userId);
}
