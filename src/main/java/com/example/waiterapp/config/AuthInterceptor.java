package com.example.waiterapp.config;

import com.example.waiterapp.models.Cliente;
import com.example.waiterapp.services.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

import static java.util.Objects.isNull;

public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    ClienteService clienteService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final RequireAuthentication requireAuthentication = ((HandlerMethod)handler).getMethod().getAnnotation((RequireAuthentication.class));

        if(requireAuthentication == null){
            return true;
        }

        var basicAuth = request.getHeader("Authorization");

        byte[] decodedBytes = Base64.getDecoder().decode(basicAuth);
        String decodedString = new String(decodedBytes);
        String cpf = decodedString.split(":")[0];
        String password = decodedString.split(":")[1];

        Cliente cliente = clienteService.retornaClienteByCpf(cpf);

        if(isNull(cliente)) {
            response.setStatus(403);
            return false;
        } else if(clienteService.isClientAuthorized(cliente, password, cpf)) {
            return true;
        }

        response.setStatus(403);
        return false;
    }
}