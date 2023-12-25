package com.example.userservice.entity;

import com.example.userservice.common.config.SerializableObjectConverter;
import lombok.Data;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;

@Data
@Entity
@Table(name = "refresh_token")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenId;
    @Column(columnDefinition = "text")
    private String token;
    @Column(columnDefinition = "text")
    private String authentication;

    public OAuth2Authentication getAuthentication() {
        return SerializableObjectConverter.deserializeAuthentication(authentication);
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = SerializableObjectConverter.serializeAuthentication(authentication);
    }
}
