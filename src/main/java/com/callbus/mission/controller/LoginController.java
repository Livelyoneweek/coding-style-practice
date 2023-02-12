package com.callbus.mission.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/")
    public ResponseEntity<?> login() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/do")
    public ResponseEntity<?> doLogin() {
        return new ResponseEntity<>("Login 먼저 진행해주세요", HttpStatus.BAD_REQUEST);
    }
}
