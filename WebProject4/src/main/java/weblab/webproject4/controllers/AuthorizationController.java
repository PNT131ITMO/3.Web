package weblab.webproject4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weblab.webproject4.DTO.JwtDTO;
import weblab.webproject4.DTO.UserDTO;
import weblab.webproject4.entities.User;
import weblab.webproject4.security.jwt.JwtUtils;
import weblab.webproject4.security.services.UserService;

@RestController
@RequestMapping("/webproject4/api/auth")
public class AuthorizationController {
    @Autowired
    JwtUtils utils;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    ResponseEntity<?> login(@Validated @RequestBody UserDTO userDTO) {
        try {
            User user = (User) userService.loadUserByUsername(userDTO.getUsername());
            if (user == null) {
                throw new IllegalArgumentException();
            } else if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                throw new IllegalAccessException();
            } else {
                String jwt = utils.generateJwtToken(userDTO.getUsername());
                return ResponseEntity.ok(new JwtDTO(userDTO.getUsername(), jwt));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return ResponseEntity.badRequest().body("Login or password is incorrect!");
        }
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@Validated @RequestBody UserDTO userDTO) {
        try {
            if (userService.loadUserByUsername(userDTO.getUsername()) != null){
                throw new IllegalArgumentException();
            }
            userService.addUser(new User(
                    userDTO.getUsername(),
                    passwordEncoder.encode(userDTO.getPassword())
            ));
            return ResponseEntity.ok().body(userDTO.getUsername());
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            return ResponseEntity.badRequest().body("This username " + userDTO.getUsername() + " already exists!");
        }
    }
}
