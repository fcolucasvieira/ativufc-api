package br.ufc.ativufc.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comprovantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Comprovante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String caminho;

    @Column(nullable = false)
    private String nomeOriginal;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long tamanho;

    @Column(nullable = false)
    private LocalDateTime uploadAt;

    @OneToOne
    @JoinColumn(name = "solicitacao_id", unique = true, nullable = false)
    @JsonBackReference
    private Solicitacao solicitacao;
}
