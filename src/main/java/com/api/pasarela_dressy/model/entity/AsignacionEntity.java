package com.api.pasarela_dressy.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@Table(name="asignaciones")
@Entity
public class AsignacionEntity implements Serializable
{
  @Serial
  private static final long serialVersionUID = 1L; 

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id_asignacion;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime creado_el;

  @Column
  private Boolean eliminado = false;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="id_rol", nullable = false)
  private RolEntity rol;

  @ManyToOne
  @JoinColumn(name="id_empleado", nullable = false)
  private EmpleadoEntity empleado;

}
