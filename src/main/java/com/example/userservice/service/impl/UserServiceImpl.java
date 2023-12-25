package com.example.userservice.service.impl;

import com.example.userservice.common.contstant.ErrorCode;
import com.example.userservice.dto.OrderDTO;
import com.example.userservice.dto.UserBasicDTO;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.exception.ServerException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * @author Chuob Bunthoeurn
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, OrderDTO> kafkaTemplate;

    @Override
    @Transactional(rollbackOn = Exception.class)
    //@CacheEvict(value = UserEntity.TABLE_NAME, allEntries = true)
    public void create(UserDTO userDTO) throws ServerException {

        String username = userDTO.getUsername();

        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(new UserEntity());
        if (!Objects.isNull(userEntity.getUsername())) {
            throw new ServerException(ErrorCode.E001.name(), ErrorCode.E001.getDesc());
        }
        UserMapper.INSTANCE.copyDtoToEntity(userDTO, userEntity);
        this.userRepository.save(userEntity);
        UserMapper.INSTANCE.copyEntityToDto(userEntity, userDTO);
    }

    @Override
    //@CachePut(value = "users", key="#id")
    public void update(UserDTO userDTO, Long id) {
    }

    @Override
    //@Cacheable(value="users", key = "#id")
    public UserDTO findOne(Long id) {
        return null;
    }

    @Override
    //@Cacheable(value="users")
    public List<UserDTO> findAll() {
        return UserMapper.INSTANCE.copyEntityListToDtoList(this.userRepository.findAll());
    }

    @Override
    //@Cacheable(value = "users")
    public Page<UserDTO> findWithPage(UserDTO userDTO) {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
            if (Objects.isNull(userEntity)) {
                throw new UsernameNotFoundException("User " + username + " was not found in the database");
            }
            return new UserBasicDTO(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }
}
