package com.example.userservice.repository;

import com.example.userservice.entity.AccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, Long>, JpaSpecificationExecutor<AccessTokenEntity> {

    @Query(value = "select a from AccessTokenEntity a where a.refreshToken = :extractTokenKey")
    Optional<AccessTokenEntity> findByRefreshToken(String extractTokenKey);

    @Query(value = "select a from AccessTokenEntity a where a.authenticationId = :authenticationId")
    Optional<AccessTokenEntity> findByAuthenticationId(String authenticationId);

    @Query(value = "select a from AccessTokenEntity a where a.clientId = :clientId and a.username = :username")
    List<AccessTokenEntity> findByClientIdAndUsername(@Param("clientId") String clientId, @Param("username") String username);

    @Query(value = "select a from AccessTokenEntity a where a.clientId = :clientId")
    List<AccessTokenEntity> findByClientId(@Param("clientId") String clientId);


    @Query(value = "select a from AccessTokenEntity a where a.tokenId = :extractTokenKey")
    Optional<AccessTokenEntity> findByTokenId(@Param("extractTokenKey") String extractTokenKey);
}
