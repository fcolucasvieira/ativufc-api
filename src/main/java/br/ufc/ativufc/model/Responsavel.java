package br.ufc.ativufc.model;

import br.ufc.ativufc.model.enums.Cargo;
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

    @ManyToOne(optional = false)
    private Instituicao instituicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
