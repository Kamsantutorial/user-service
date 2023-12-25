package com.example.userservice.mapper;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.vo.request.UserCreateRequestVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Chuob Bunthoeurn
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    void copyEntityToDto(UserEntity userEntity, @MappingTarget UserDTO userDTO);
    UserDTO copyEntityToDto(UserEntity userEntity);
    void copyDtoToEntity(UserDTO userDTO, @MappingTarget UserEntity userEntity);
    void copyRequestCreateVoToDto(UserCreateRequestVO userCreateRequestVO, @MappingTarget  UserDTO userDTO);
    List<UserDTO> copyEntityListToDtoList(List<UserEntity> list);

}
