package com.shoon.login;

import com.shoon.login.web.argumentResolver.LoginMemberArgumentResolver;
import com.shoon.login.web.filter.LogFilter;
import com.shoon.login.web.filter.LoginCheckFilter;
import com.shoon.login.web.interceptor.LogInterceptor;
import com.shoon.login.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final LogInterceptor logInterceptor;
    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(loginCheckInterceptor)
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/member/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error");
    }

    // @Bean
    public FilterRegistrationBean logFilter() {
        // @WebFilter 를 통해 필터 등록이 가능하지만 순서적용이 안된다.
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        // 필터에 LogFilter 등록
        registrationBean.setFilter(new LogFilter());
        // 필터 적용 순서
        registrationBean.setOrder(1);
        // 적용될 URL
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    // @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginCheckFilter());
        registrationBean.setOrder(2);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}
