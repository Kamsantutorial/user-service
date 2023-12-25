package com.example.userservice.service;

import com.example.userservice.exception.ServerException;
import org.springframework.data.domain.Page;

/**
 * @author Chuob Bunthoeurn
 */
public interface BaseService<T> {
    void create(T dto) throws ServerException;

    void update(T dto, Long id);

    T findOne(Long id);

    Page<T> findWithPage(T dto);
}
