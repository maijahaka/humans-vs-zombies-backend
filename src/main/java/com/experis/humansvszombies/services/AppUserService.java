package com.experis.humansvszombies.services;

import com.experis.humansvszombies.models.AppUser;
import com.experis.humansvszombies.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    @Autowired
    AppUserRepository appUserRepository;

    public ResponseEntity<List<AppUser>> getUsers() {
        return new ResponseEntity<>(appUserRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<AppUser> getUserById(long id){
        HttpStatus status;
        AppUser user = new AppUser();
        if (appUserRepository.existsById(id)){
            status = HttpStatus.OK;
            user = appUserRepository.findById(id).get();
            return new ResponseEntity<>(user, status);
        }else
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(user, status);
    }

    public ResponseEntity<AppUser> deleteUser(long id){
        HttpStatus status;
        AppUser deletedUser = appUserRepository.findById(id).get();
        if (deletedUser != null) {
            appUserRepository.deleteById(id);
            status = HttpStatus.OK;
        }else
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(deletedUser, status);
    }

    public ResponseEntity<AppUser> updatedUser(long id, AppUser updateInfo){
        if (!appUserRepository.existsById(id) ||
            id != updateInfo.getId())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        AppUser updatedUser = appUserRepository.findById(id).get();
        if (updateInfo.getLastName() != null)
            updatedUser.setLastName(updateInfo.getLastName());
        if (updateInfo.getFirstName() != null)
            updatedUser.setFirstName(updateInfo.getFirstName());

        appUserRepository.save(updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    public ResponseEntity<AppUser> addUser(AppUser user){
        if (user.getFirstName() != null && user.getLastName() != null){
            user = appUserRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
