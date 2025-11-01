package dev.wisespirit.mediumclone.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.wisespirit.mediumclone.model.BaseResponse;
import dev.wisespirit.mediumclone.model.ErrorData;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        LOG.error("authentication error ",authException);
        response.setHeader("Content-Type","application/json");
        response.setStatus(401);
        ServletOutputStream outputStream = response.getOutputStream();
        ErrorData errorData = new ErrorData("unauthorized");
        BaseResponse<Void> responseBody = new BaseResponse<>(errorData);
        objectMapper.writeValue(outputStream,responseBody);
    }
}
