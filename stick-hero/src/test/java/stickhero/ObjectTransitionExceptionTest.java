package stickhero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectTransitionExceptionTest {
    @Test
    void testConstructor() {
        String errorMessage = "Test error message";

        // Create an instance of ObjectTransitionException
        ObjectTransitionException exception = new ObjectTransitionException(errorMessage);

        // Check if the exception message is set correctly
        assertEquals(errorMessage, exception.getMessage());
    }
}