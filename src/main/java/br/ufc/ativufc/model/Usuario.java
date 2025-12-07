package br.ufc.ativufc.model;

import br.ufc.ativufc.model.enums.Perfil;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private Perfil perfil;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @OneToOne(mappedBy = "usuario")
    private Discente discente;

    @OneToOne(mappedBy = "usuario")
    private Responsavel responsavel;
}
