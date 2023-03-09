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
@Table(name = "imagenes")
public class ImagenEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_imagen;

    @Column(length = 400, nullable = false)
    private String url;

    @Column()
    @CreationTimestamp
    private LocalDateTime creado_el;

    @ManyToOne()
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoEntity producto;
}