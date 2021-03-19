package com.experis.humansvszombies.repositories;

import com.experis.humansvszombies.models.Kill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KillRepository extends JpaRepository<Kill, Long> {
}
