package com.example.waiterapp.config;

import com.example.waiterapp.models.Cliente;
import com.example.waiterapp.models.Garcom;
import com.example.waiterapp.services.ClienteService;
import com.example.waiterapp.services.GarcomService;

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
    GarcomService garcomService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
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
                Garcom garcom = garcomService.retornaGarcomByCpf(cpf);
                if (garcom != null && garcomService.isWaiterAuthorized(garcom, password, cpf)) {
                    return true;
                }

                response.setStatus(403);
                return false;
            } else if(clienteService.isClientAuthorized(cliente, password, cpf)) {
                return true;
            }

            response.setStatus(403);
            return false;
        } catch (Exception e) {
            logger.error("Error on AuthInterceptor: ", e);
            return true;
        }
    }
}
