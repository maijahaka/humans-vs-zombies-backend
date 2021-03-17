package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.AppUser;
import com.experis.humansvszombies.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/*
 * Rest controller for user object api requests.
 * Calls the AppUserService class to handle the business logic.
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers(){
        return appUserService.getUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable long id){
        return appUserService.getUserById(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<AppUser> deleteUser(@PathVariable long id){
        return appUserService.deleteUser(id);
    }
    @PostMapping
    public ResponseEntity<AppUser> addUser(@RequestBody AppUser user){
        return appUserService.addUser(user);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable long id, @RequestBody AppUser user){ return appUserService.updatedUser(id, user);
    }
}
