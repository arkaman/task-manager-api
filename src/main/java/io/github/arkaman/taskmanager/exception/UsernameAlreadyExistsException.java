package io.github.arkaman.taskmanager.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("Username already taken");
    }
}
