package com.api.pasarela_dressy.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "detalle_entrada")
public class DetalleEntradaEntity implements Serializable
{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_detalle_entrada;

    @Column
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_entrada", nullable = false)
    private EntradaEntity entrada;

    @ManyToOne
    @JoinColumn(name = "id_talla", nullable = false)
    private TallaEntity talla;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoEntity producto;
}
