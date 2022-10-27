package com.library.citadel_library.controller;

import com.library.citadel_library.dto.UserDTO;
import com.library.citadel_library.dto.requests.LoginRequest;
import com.library.citadel_library.dto.requests.RegisterRequest;
import com.library.citadel_library.dto.response.LoginResponse;
import com.library.citadel_library.security.jwt.JwtUtils;
import com.library.citadel_library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping()
@AllArgsConstructor
public class UserJwtController {

  private  UserService userService;

  private JwtUtils jwtUtils;

  private AuthenticationManager authManager;

  @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest request){

     UserDTO userDTO = userService.register(request);

     return new ResponseEntity<>(userDTO,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest loginRequest){

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        String token = jwtUtils.generateToken(authentication);

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }
}
