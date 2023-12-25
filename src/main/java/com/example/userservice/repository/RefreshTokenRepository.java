package com.example.userservice.repository;

import com.example.userservice.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long>, JpaSpecificationExecutor<RefreshTokenEntity> {

    @Query(value = "select r from RefreshTokenEntity r where r.tokenId = :extractTokenKey")
    Optional<RefreshTokenEntity> findByTokenId(@Param("extractTokenKey") String extractTokenKey);
}
