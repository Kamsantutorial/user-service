package com.example.userservice.common.config;

import com.example.userservice.entity.RoleEntity;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice(assignableTypes = {CheckTokenEndpoint.class})
public class OAuthCheckTokenResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        if (body instanceof Exception) {
            // If check_token fails, response is an Exception instead of Map, so skip the method.
            return body;
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> res = (Map<String, Object>) body;
        UserEntity user = this.userRepository.findByUsername(res.get("user_name").toString()).orElse(new UserEntity());
        res.put("id", user.getId());
        res.put("authorities", user.getRoles().stream().map(RoleEntity::getRoleName).collect(Collectors.toList()));

        // modify response as you please

        return res;
    }

}