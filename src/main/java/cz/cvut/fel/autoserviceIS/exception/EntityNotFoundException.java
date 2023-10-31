package cz.cvut.fel.autoserviceIS.exception;

public class EntityNotFoundException extends Throwable {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException create(String resourceName, Object identifier) {
        return new EntityNotFoundException(resourceName + " identifier: " + identifier + " was not found.");
    }
}
