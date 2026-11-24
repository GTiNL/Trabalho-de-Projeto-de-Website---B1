package com.escola.horarios.usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Manda para /login quem tentar abrir qualquer pagina sem estar logado. */
@Configuration
public class LoginObrigatorio implements WebMvcConfigurer, HandlerInterceptor {
    @Override
    public void addInterceptors(InterceptorRegistry r) {
        r.addInterceptor(this).excludePathPatterns("/login", "/cadastro", "/resetar", "/css/**", "/error");
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object h) throws Exception {
        if (req.getSession().getAttribute("email") != null) return true;
        res.sendRedirect("/login");
        return false;
    }
}
