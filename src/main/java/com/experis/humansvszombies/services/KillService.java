package com.experis.humansvszombies.services;

import com.experis.humansvszombies.models.Kill;
import com.experis.humansvszombies.models.Player;
import com.experis.humansvszombies.repositories.KillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KillService {
    @Autowired
    private KillRepository killRepository;

    public List<Kill> getAllKills(long id) {
        return killRepository.findAllByGame_Id(id);
    }

    public Kill getKill(long id){
        if (killRepository.existsById(id))
            return killRepository.findById(id).get();
        return null;
    }
}
