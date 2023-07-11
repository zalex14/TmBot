package zalex14.shelter.exception;

/**
 * Validation exception
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super("Ошибка валидации: " + message);
    }
}
