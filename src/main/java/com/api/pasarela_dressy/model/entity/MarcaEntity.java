package com.api.pasarela_dressy.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "marcas")
public class MarcaEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_marca;

    @Column(length = 50, unique = true)
    private String nombre;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime creado_el;

    @Column()
    private Boolean eliminado = false;

    @OneToMany(mappedBy = "marca")
    private Set<ProductoEntity> productos;
}
