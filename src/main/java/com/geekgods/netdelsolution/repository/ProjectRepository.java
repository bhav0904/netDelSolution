package com.geekgods.netdelsolution.repository;

import com.geekgods.netdelsolution.domain.Project;
import com.geekgods.netdelsolution.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {


}
