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
@Table(name = "entradas")
public class EntradaEntity implements Serializable
{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_entrada;

    @Column
    @CreationTimestamp
    private LocalDateTime creado_el;

    @Column
    private Boolean ejecutado = Boolean.FALSE;

    @Column
    private Boolean eliminado = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private ProveedorEntity proveedor;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private EmpleadoEntity empleado;

    @OneToMany(mappedBy = "entrada")
    private Set<DetalleEntradaEntity> detalleEntradaList;

}
