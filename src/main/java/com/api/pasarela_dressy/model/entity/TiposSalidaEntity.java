package com.api.pasarela_dressy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipos_salida")
public class TiposSalidaEntity implements Serializable
{

    @Serial
    private static final long serialVersionUID = -3078685509697638959L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_tipo_salida;
    
    @Column(length = 50, nullable = false, unique = true)
    private String nombre;
    
    @CreationTimestamp
    private LocalDateTime creado_el;
    
    @Column
    private Boolean eliminado = Boolean.FALSE;
}
