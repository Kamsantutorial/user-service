package com.example.userservice.entity;

import com.example.userservice.common.config.SerializableObjectConverter;
import lombok.Data;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;

@Data
@Entity
@Table(name = "access_token")
public class AccessTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenId;
    @Column(columnDefinition = "text")
    private String token;
    private String authenticationId;
    private String username;
    private String clientId;
    @Column(columnDefinition = "text")
    private String authentication;
    @Column(columnDefinition = "text")
    private String refreshToken;


    public OAuth2Authentication getAuthentication() {
        return SerializableObjectConverter.deserializeAuthentication(authentication);
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = SerializableObjectConverter.serializeAuthentication(authentication);
    }
}
