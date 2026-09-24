package com.niknastacy.repository;

import com.niknastacy.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {
    List<Application> findAllByUserId(String userId);

    Optional<Application> findByIdAndUserId(String id, String userId);
}
