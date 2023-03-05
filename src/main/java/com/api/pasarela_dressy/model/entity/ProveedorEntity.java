package com.api.pasarela_dressy.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "proveedores")
public class ProveedorEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_proveedor;

    @Column(length = 100)
    private String nombre;

    @Column(length = 500)
    private String direccion;

    @Column(columnDefinition = "char(9)")
    private String telefono;

    @Column
    @CreationTimestamp
    private LocalDateTime creado_el;

    @Column
    private Boolean eliminado = false;

}
