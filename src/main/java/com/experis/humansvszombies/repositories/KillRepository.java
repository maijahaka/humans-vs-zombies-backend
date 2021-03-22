package com.experis.humansvszombies.repositories;

import com.experis.humansvszombies.models.Kill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KillRepository extends JpaRepository<Kill, Long> {
    List<Kill> findAllByGame_Id(long id);
}
