package com.experis.humansvszombies.services;

import com.experis.humansvszombies.models.AppUser;
import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.repositories.AppUserRepository;
import com.experis.humansvszombies.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
/*
 * A service class that acts as an middle-man between user controller and user repository.
 */

@Service
public class AppUserService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    GameRepository gameRepository;

    //returns all users from user repository with Http status 200
    public ResponseEntity<List<AppUser>> getUsers() {
        return new ResponseEntity<>(appUserRepository.findAll(), HttpStatus.OK);
    }

    //returns a single user from the database with Http status 200 if one can be found.
    //if user with given the id is not found returns null and Http status 404
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

    public List<Game> getGamesByUserId(String userId) {
        return gameRepository.findAllByUserId(userId);
    }

    //deletes a user with given id from the database if one can be found.
    //returns the deleted user and http status 200, or null and http status 404 in case of invalid request.
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

    //updates a user in the database with given id and request body parameters.
    //checks that path variable id matches request body - if not return null and http status 400
    public ResponseEntity<AppUser> updatedUser(long id, AppUser updateInfo){
        if (!appUserRepository.existsById(id) ||
            id != updateInfo.getId())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        //get the user with id
        AppUser updatedUser = appUserRepository.findById(id).get();
        //if request isn't null update the values
        if (updateInfo.getLastName() != null)
            updatedUser.setLastName(updateInfo.getLastName());
        if (updateInfo.getFirstName() != null)
            updatedUser.setFirstName(updateInfo.getFirstName());
        //save the user and return it
        appUserRepository.save(updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //add user to database. Check that request body contains required parameters.
    //return added user and http status 201 if request was valid. Null and 400 if not.
    public ResponseEntity<AppUser> addUser(AppUser user){
        if (user.getFirstName() != null && user.getLastName() != null){
            user = appUserRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
