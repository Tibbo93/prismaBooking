package it.prisma.prismabooking.utils;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
