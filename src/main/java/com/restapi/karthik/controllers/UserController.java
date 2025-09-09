package com.restapi.karthik.controllers;

import com.restapi.karthik.dtos.ChangePasswordRequest;
import com.restapi.karthik.dtos.RegisterUserRequest;
import com.restapi.karthik.dtos.UpdateUserRequest;
import com.restapi.karthik.dtos.UserDTO;
import com.restapi.karthik.entities.Role;
import com.restapi.karthik.entities.User;
import com.restapi.karthik.mappers.UserMapper;
import com.restapi.karthik.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;



@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    public final UserRepository userRepository;
    public final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping

    public Iterable<UserDTO> getUsers(
//            @RequestHeader(required = false,name = "x-auth-token") String authToken,
            @RequestParam(required = false,defaultValue = "",name = "sort") String sortBy) {

//        System.out.print(authToken);
        if(!Set.of("name","email").contains(sortBy))
            sortBy="name";

        return userRepository.findAll(Sort.by(sortBy).descending())
                .stream()
                .map(userMapper::toUserDTO)
                .toList();

    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id){
        var user =  userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(userMapper.toUserDTO(user));


    }
    @PostMapping
    public ResponseEntity<?>createUser(
           @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder

    ){
        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(
                    Map.of("email", "email is already exists")
            );

        }
        var user  = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);


        var userDto = userMapper.toUserDTO(user);
       var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable(name = "id") int id,
            @RequestBody UpdateUserRequest request
    ){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        userMapper.updateUser(request,user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteUser(
            @PathVariable(name = "id")int id


    ){
        var user = userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();

    }
    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable int id,
            @RequestBody ChangePasswordRequest request
    )
    {
        var user = userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }
        if(!user.getPassword().equals(request.getOldPassword())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

}
