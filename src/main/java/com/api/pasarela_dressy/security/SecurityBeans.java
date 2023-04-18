package com.api.pasarela_dressy.security;

import com.api.pasarela_dressy.model.entity.AsignacionEntity;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import com.api.pasarela_dressy.repository.AsignacionRepository;
import com.api.pasarela_dressy.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class SecurityBeans
{
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Bean
    public UserDetailsService userDetailsService()
    {
        return username -> {
            EmpleadoEntity empleado = empleadoRepository.getOneByCorreo(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

            Set<AsignacionEntity> asignaciones = asignacionRepository.getByEmpleadoSet(empleado);
            empleado.setAsignaciones(asignaciones);
            return empleado;
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
