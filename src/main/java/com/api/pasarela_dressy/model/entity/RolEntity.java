package com.api.pasarela_dressy.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="roles")
public class RolEntity implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id_rol;

  @Column(unique = true, nullable = false, length = 30)
  private String nombre;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp()
  private LocalDateTime creado_el;

  @Column
  private Boolean eliminado = false;

  @OneToMany(mappedBy="role")
  private Set<AsignacionEntity> asignaciones;
}
