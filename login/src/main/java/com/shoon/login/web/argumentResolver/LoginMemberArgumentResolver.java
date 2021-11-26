package com.shoon.login.web.argumentResolver;

import com.shoon.login.domain.member.Member;
import com.shoon.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        // 어노테이션의 타입이 Login.class 인지 체크
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Login.class);
        // parameter 가 Member.class 인지 (자식클래스 포함) 체크
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getNestedParameterType());

        return hasParameterAnnotation && hasMemberType;
    }

    // 컨트롤러 호출 직전에 호출되어 필요한 파라미터 정보를 생성한다.
    // supportsParameter 가 true 값으로 확인되어야 resolveArgument 가 실행된다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if(session == null) return null;

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
