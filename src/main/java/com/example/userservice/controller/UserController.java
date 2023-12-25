package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.exception.ServerException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.ResponseMessage;
import com.example.userservice.vo.request.UserCreateRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chuob Bunthoeurn
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseMessage<Object> create(@RequestBody UserCreateRequestVO userCreateRequestVO) throws ServerException {
        UserDTO userDTO = new UserDTO();
        UserMapper.INSTANCE.copyRequestCreateVoToDto(userCreateRequestVO, userDTO);
        this.userService.create(userDTO);
        return new ResponseMessage<>()
                .body(null)
                .success()
                .response();
    }

    @GetMapping
    public ResponseMessage<Object> findAll() {
        return new ResponseMessage<>()
                .body(this.userService.findAll())
                .success()
                .response();
    }

}
