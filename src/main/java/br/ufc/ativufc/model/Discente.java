package br.ufc.ativufc.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "discentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "matricula")
public class Discente {

    @Id
    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate ingressao;

    @ManyToOne(optional = false)
    private Curso curso;

    @Column(nullable = false)
    private Integer horasCumpridas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public int getHorasRestantes(){
        int restantes = curso.getTotalHorasComplementares() - horasCumpridas;
        return Math.max(restantes, 0);
    }
}
