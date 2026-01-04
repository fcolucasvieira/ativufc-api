package br.ufc.ativufc.model;

import br.ufc.ativufc.model.enums.Status;
import br.ufc.ativufc.model.enums.TipoParticipacao;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "solicitacoes")
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
    private Subtipo subtipo;

    @ManyToOne(optional = false)
    private Instituicao instituicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoParticipacao participacao;

    @Column(nullable = false)
    private Integer cargaHorariaSolicitada;

    // Alteração após o deferimento
    @Column(nullable = false)
    private Integer cargaHorariaAproveitada = 0;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private LocalDate dataSolicitacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDENTE;

    @Column(length = 350)
    private String observacaoDiscente;

    @Column(length = 350)
    private String observacaoResponsavel;

    @OneToOne(mappedBy = "solicitacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Comprovante comprovante;

    @PrePersist
    public void prePersist() {
        if(dataSolicitacao == null)
            dataSolicitacao = LocalDate.now();
    }
}
