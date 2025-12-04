package br.ufc.ativufc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "responsaveis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "siape")
public class Responsavel {

    @Id
    @Column(nullable = false, unique = true)
    private String siape;

    @Column(nullable = false)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
