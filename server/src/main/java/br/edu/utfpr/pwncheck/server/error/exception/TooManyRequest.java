package br.edu.utfpr.pwncheck.server.error.exception;

public class TooManyRequest extends RuntimeException {
    public TooManyRequest(String message) {
        super(message);
    }
}
