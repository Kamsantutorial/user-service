package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.exception.ServerException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Chuob Bunthoeurn
 */
public interface UserService extends BaseService<UserDTO>, UserDetailsService  {
    void create(UserDTO userDTO) throws ServerException;
    void update(UserDTO userDTO, Long id);
    List<UserDTO> findAll();
    Page<UserDTO> findWithPage(UserDTO userDTO);
}
