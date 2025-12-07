package br.ufc.ativufc.model.enums;

public enum TipoParticipacao {
    ORGANIZADOR("Membro da Equipe de Trabalho / Organizador"),
    OUVINTE("Público Atendido / Ouvinte / Espectador"),
    PALESTRANTE("Palestrante / Ministrante");

    private final String label;

    TipoParticipacao(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
