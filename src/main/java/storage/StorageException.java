package storage;

/**
 * Custom exception to handle file I/O and directory creation errors
 * within the Storage layer. Extends RuntimeException so it doesn't
 * force checked exception handling everywhere.
 */
public class StorageException extends RuntimeException {

    /**
     * Constructs a new StorageException with the specified detail message.
     *
     * @param message The detail message explaining the error.
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Constructs a new StorageException with the specified detail message and cause.
     *
     * @param message The detail message explaining the error.
     * @param cause   The underlying cause of the exception (e.g., IOException).
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}