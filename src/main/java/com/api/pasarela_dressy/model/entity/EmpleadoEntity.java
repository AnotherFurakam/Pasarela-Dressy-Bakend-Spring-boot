package com.api.pasarela_dressy.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@Entity
@Table(name = "empleados")
public class EmpleadoEntity
    implements Serializable, UserDetails
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_empleado;

    @Column(length = 50, nullable = false)
    private String nombres;

    @Column(length = 60, nullable = false)
    private String apellido_pat;

    @Column(length = 60, nullable = false)
    private String apellido_mat;

    @Column(length = 8, unique = true, nullable = false)
    private String dni;

    @Column(length = 9)
    private String numero_cel;

    @Column(length = 60, nullable = false, unique = true)
    private String correo;

    @Column(name = "contrasenia_hash", length = 300, nullable = false)
    private String contrasenia;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp()
    private LocalDateTime creado_el;

    @Column(length = 300, nullable = false)
    private String direccion;

    @Column
    private Boolean activo = true;

    @Column
    private Boolean eliminado = false;

    @OneToMany(mappedBy = "empleado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AsignacionEntity> asignaciones;

    @OneToMany(mappedBy = "empleado")
    private Set<EntradaEntity> entradas;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return asignaciones.stream()
            .map(a -> new SimpleGrantedAuthority(a.getRol().getNombre()))
            .collect(Collectors.toList());
    }

    @Override
    public String getUsername()
    {
        return correo;
    }

    @Override
    public String getPassword()
    {
        return contrasenia;
    }


    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return activo;
    }
}