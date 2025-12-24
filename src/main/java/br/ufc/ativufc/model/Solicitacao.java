package br.ufc.ativufc.model;

import br.ufc.ativufc.model.enums.Status;
import br.ufc.ativufc.model.enums.TipoParticipacao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "solicitacoes_creditacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Discente discente;

    @ManyToOne(optional = false)
    private SubtipoAtividade subTipoAtividade;

    @ManyToOne(optional = false)
    private Instituicao instituicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoParticipacao tipoParticipacao;

    @Column(nullable = false)
    private Integer cargaHorariaTotal;

    @Column(nullable = true)
    private Integer horasAproveitadas;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private LocalDate dataSolicitacao = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDENTE;

    @Column(length = 350)
    private String observacao;

    @Column(length = 350)
    private String observacaoResponsavel;

    @OneToOne(mappedBy = "solicitacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Comprovante comprovante;
}
