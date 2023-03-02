package com.api.pasarela_dressy.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class ProductoEntity implements Serializable
{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_producto;

    @Column(length = 300, nullable = false)
    private String nombre;

    @Column(length = 500, nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private BigDecimal precio_oferta;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime creado_el;

    @Column(length = 30, nullable = false)
    private String sku;

    @Column
    private Integer stock;

    @Column
    private Boolean activo = true;

    @Column
    private Boolean eliminado = false;

    @ManyToOne
    @JoinColumn(name="id_categoria", nullable = false)
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name="id_marca", nullable = false)
    private MarcaEntity marca;
}
