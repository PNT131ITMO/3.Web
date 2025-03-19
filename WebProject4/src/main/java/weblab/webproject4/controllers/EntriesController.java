package weblab.webproject4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weblab.webproject4.DTO.EntryDTO;
import weblab.webproject4.entities.Entry;
import weblab.webproject4.entities.User;
import weblab.webproject4.repositories.EntryRepository;
import weblab.webproject4.security.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/webproject4/api/entries")
public class EntriesController {
    @Autowired
    private UserService userService;
    @Autowired
    private EntryRepository entryRepository;

    @GetMapping
    ResponseEntity<?> getUserEntries(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.findByUser(user));
    }

    @PostMapping
    ResponseEntity<?> addEntry(@Validated @RequestBody EntryDTO entryDTO, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.save(new Entry(entryDTO.getX(), entryDTO.getY(), entryDTO.getR(), user)));
    }
    @DeleteMapping
    ResponseEntity<?> deleteUserEntries(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.deleteByUser(user));
    }
}
