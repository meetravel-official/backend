package com.meetravel.global.aop;

import com.meetravel.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller 파라미터로 붙어서 {@link UserEntity}를 반환해주는 Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Parameter(hidden = true)
public @interface UserSession {
}
