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
@Table(name = "producto_talla")
public class ProductoTallaEntity implements Serializable
{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_producto_talla;

    @Column
    private Integer cantidad;

    @Column
    @CreationTimestamp
    private LocalDateTime creado_el;

    @Column
    private Boolean eliminado = false;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoEntity producto;

    @ManyToOne
    @JoinColumn(name = "id_talla", nullable = false)
    private TallaEntity talla;

}
