package com.experis.humansvszombies.services;

import com.experis.humansvszombies.config.DefaultAuthenticationProvider;
import com.experis.humansvszombies.models.Kill;
import com.experis.humansvszombies.models.Player;
import com.experis.humansvszombies.models.wrappers.BiteCodeKillerWrapper;
import com.experis.humansvszombies.repositories.GameRepository;
import com.experis.humansvszombies.repositories.KillRepository;
import com.experis.humansvszombies.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class KillService {
    @Autowired
    private KillRepository killRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;

    public List<Kill> getAllKills(long id) {
        return killRepository.findAllByGame_Id(id);
    }

    public Kill getKill(long id){
        if (killRepository.existsById(id))
            return killRepository.findById(id).get();
        return null;
    }

    @Transactional
   public Kill addKill(BiteCodeKillerWrapper biteCodeKillWrapper, long gameId) {
        Player victim = playerRepository.findByBiteCode(biteCodeKillWrapper.getBiteCode());
        if (victim == null || victim.getGame().getId() != gameId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No player was found with the bite code entered.");
        }

        long killerId = biteCodeKillWrapper.getKillerId();
        Player killer = playerRepository.findById(killerId).get();

        if (killer.isHuman())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only zombies can kill human players.");
        if (!victim.isHuman())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The victim has already been turned into a zombie.");

        victim.setHuman(false);

        Kill kill = new Kill();
        kill.setLat(biteCodeKillWrapper.getLat());
        kill.setLng(biteCodeKillWrapper.getLng());
        kill.setKiller(killer);
        kill.setStory(biteCodeKillWrapper.getStory());
        kill.setVictim(victim);
        kill.setGame(gameRepository.findById(gameId).get());
        Kill storedKill = killRepository.save(kill);

        victim.setVictimOf(storedKill);
        killer.getKills().add(storedKill);

        return kill;
    }
}
