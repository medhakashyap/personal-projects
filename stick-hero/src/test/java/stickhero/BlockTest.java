package stickhero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {
    @Test
    void getRandomNumber() {
        for (int i = 0; i < 1000; i++) {
            int randomNumber = Blocks.getRandomNumber();
            assertTrue(randomNumber >= 0 && randomNumber <= 3, "Random number should be between 0 and 3");
        }
    }
}