package com.meetravel.global.aop;

import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import com.meetravel.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Controller 파라미터로 붙어서 {@link UserEntity}를 반환해주는 Resolver
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 어노테이션 & 파라미터 타입 체크
        return parameter.hasParameterAnnotation(UserSession.class) && UserEntity.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        @NotNull ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        @NotNull WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String accessToken = extractTokenFromRequest(request);
        return findUserByToken(accessToken);
    }

    private UserEntity findUserByToken(String accessToken) {
        String userId = jwtService.getUserId(accessToken);
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        return jwtService.getToken(request);
    }
}
