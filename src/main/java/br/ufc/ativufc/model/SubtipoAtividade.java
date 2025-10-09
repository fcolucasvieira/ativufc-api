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
public class SubtipoAtividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricaoSubTipoAtividade;

    @Column(nullable = false)
    private Integer cargaHorariaMaxima;
}
