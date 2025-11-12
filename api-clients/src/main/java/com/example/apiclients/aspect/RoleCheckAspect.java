package com.example.apiclients.aspect;

import com.example.apiclients.annotation.RequireRole;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
public class RoleCheckAspect {

    @Before("@annotation(com.example.apiclients.annotation.RequireRole)")
    public void checkRole(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequireRole requireRole = signature.getMethod().getAnnotation(RequireRole.class);

        ServletRequestAttributes attributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            throw new SecurityException("No request context available");
        }

        HttpServletRequest request = attributes.getRequest();
        String memberRole = request.getHeader("Member-Role");

        if (memberRole == null) {
            throw new SecurityException("Member-Role header not found");
        }

        String[] allowedRoles = requireRole.value();
        boolean hasRole = Arrays.stream(allowedRoles)
                .anyMatch(role -> role.equals(memberRole));

        if (!hasRole) {
            throw new SecurityException(
                "Access denied. Required roles: " + Arrays.toString(allowedRoles) +
                ", but user has: " + memberRole
            );
        }
    }
}
