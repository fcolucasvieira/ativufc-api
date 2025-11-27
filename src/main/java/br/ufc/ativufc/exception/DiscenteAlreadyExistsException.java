package br.ufc.ativufc.exception;

public class DiscenteAlreadyExistsException extends RuntimeException {
    public DiscenteAlreadyExistsException() {
        super("Discente com esta matrícula já existe");
    }
}
