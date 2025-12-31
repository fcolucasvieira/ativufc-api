package br.ufc.ativufc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subtipos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Subtipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String descricao;

    @Column(nullable = false)
    private Integer horasMin;

    @Column(nullable = false)
    private Integer horasMax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;
}
