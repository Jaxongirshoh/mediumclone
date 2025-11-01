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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    private static final Logger LOG = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LOG.error("access denied",accessDeniedException);
        response.setHeader("Content-Type","application/json");
        response.setStatus(403);
        ServletOutputStream outputStream = response.getOutputStream();
        ErrorData errorData = new ErrorData("access denied");
        BaseResponse<Void> baseResponse = new BaseResponse<>(errorData);
        objectMapper.writeValue(outputStream,baseResponse);
    }
}
