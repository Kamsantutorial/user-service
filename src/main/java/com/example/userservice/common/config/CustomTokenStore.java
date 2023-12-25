package com.example.userservice.common.config;

import com.example.userservice.entity.AccessTokenEntity;
import com.example.userservice.entity.RefreshTokenEntity;
import com.example.userservice.repository.AccessTokenRepository;
import com.example.userservice.repository.RefreshTokenRepository;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CustomTokenStore implements TokenStore {

    private final AccessTokenRepository cbAccessTokenRepository;

    private final RefreshTokenRepository cbRefreshTokenRepository;

    public CustomTokenStore(AccessTokenRepository cbAccessTokenRepository, RefreshTokenRepository cbRefreshTokenRepository){
        this.cbAccessTokenRepository = cbAccessTokenRepository;
        this.cbRefreshTokenRepository = cbRefreshTokenRepository;
    }

    private final AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken accessToken) {
        return readAuthentication(accessToken.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        Optional<AccessTokenEntity> accessToken = cbAccessTokenRepository.findByTokenId(extractTokenKey(token));
        return accessToken.map(AccessTokenEntity::getAuthentication).orElse(null);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String refreshToken = null;
        if (accessToken.getRefreshToken() != null) {
            refreshToken = accessToken.getRefreshToken().getValue();
        }

        if (readAccessToken(accessToken.getValue()) != null) {
            this.removeAccessToken(accessToken);
        }

        AccessTokenEntity cat =  new AccessTokenEntity();
       // cat.setId(UUID.randomUUID() +UUID.randomUUID().toString());
        cat.setTokenId(extractTokenKey(accessToken.getValue()));
        cat.setToken(SerializableObjectConverter.serializeAccessToken(accessToken));
        cat.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        cat.setUsername(authentication.isClientOnly() ? null : authentication.getName());
        cat.setClientId(authentication.getOAuth2Request().getClientId());
        cat.setAuthentication(authentication);
        cat.setRefreshToken(extractTokenKey(refreshToken));

        cbAccessTokenRepository.save(cat);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        Optional<AccessTokenEntity> accessToken = cbAccessTokenRepository.findByTokenId(extractTokenKey(tokenValue));
        return accessToken.map(accessTokenEntity -> SerializableObjectConverter.deserializeAccessToken(accessTokenEntity.getToken())).orElse(null);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {
        Optional<AccessTokenEntity> accessToken = cbAccessTokenRepository.findByTokenId(extractTokenKey(oAuth2AccessToken.getValue()));
        accessToken.ifPresent(cbAccessTokenRepository::delete);
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        RefreshTokenEntity crt = new RefreshTokenEntity();
        //crt.setId(UUID.randomUUID() +UUID.randomUUID().toString());
        crt.setTokenId(extractTokenKey(refreshToken.getValue()));
        crt.setToken(SerializableObjectConverter.serializeRefreshToken(refreshToken));
        crt.setAuthentication(authentication);
        cbRefreshTokenRepository.save(crt);
    }


    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        Optional<RefreshTokenEntity> refreshToken = cbRefreshTokenRepository.findByTokenId(extractTokenKey(tokenValue));
        return refreshToken.map(refreshTokenEntity -> SerializableObjectConverter.deserializeRefreshToken(refreshTokenEntity.getToken())).orElse(null);
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken refreshToken) {
        Optional<RefreshTokenEntity> rtk = cbRefreshTokenRepository.findByTokenId(extractTokenKey(refreshToken.getValue()));
        return rtk.map(RefreshTokenEntity::getAuthentication).orElse(null);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
        Optional<RefreshTokenEntity> rtk = cbRefreshTokenRepository.findByTokenId(extractTokenKey(refreshToken.getValue()));
        rtk.ifPresent(cbRefreshTokenRepository::delete);
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        Optional<AccessTokenEntity> token = cbAccessTokenRepository.findByRefreshToken(extractTokenKey(refreshToken.getValue()));
        token.ifPresent(cbAccessTokenRepository::delete);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;
        String authenticationId = authenticationKeyGenerator.extractKey(authentication);
        Optional<AccessTokenEntity> token = cbAccessTokenRepository.findByAuthenticationId(authenticationId);

        if(token.isPresent()) {
            accessToken = SerializableObjectConverter.deserializeAccessToken(token.get().getToken());
            if(accessToken != null && !authenticationId.equals(this.authenticationKeyGenerator.extractKey(this.readAuthentication(accessToken)))) {
                this.removeAccessToken(accessToken);
                this.storeAccessToken(accessToken, authentication);
            }
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        Collection<OAuth2AccessToken> tokens = new ArrayList<>();
        List<AccessTokenEntity> result = cbAccessTokenRepository.findByClientIdAndUsername(clientId, userName);
        result.forEach(e-> tokens.add(SerializableObjectConverter.deserializeAccessToken(e.getToken())));
        return tokens;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        Collection<OAuth2AccessToken> tokens = new ArrayList<>();
        List<AccessTokenEntity> result = cbAccessTokenRepository.findByClientId(clientId);
        result.forEach(e-> tokens.add(SerializableObjectConverter.deserializeAccessToken(e.getToken())));
        return tokens;
    }

    private String extractTokenKey(String value) {
        if(value == null) {
            return null;
        } else {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var5) {
                throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
            }

            byte[] e = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return String.format("%032x", new BigInteger(1, e));
        }
    }
}