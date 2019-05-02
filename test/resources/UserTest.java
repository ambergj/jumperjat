package resources;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static User tester = new User("TesterNameOne", null);
    private static User tester2;
    private static User tester2same;

    @BeforeEach
    public void createTestUser(){
        UserTest.tester2 = new User("TesterNameTwo", null);
        UserTest.tester2same = new User("TesterNameTwo", null);
    }

    @org.junit.jupiter.api.Test
    void equalsTestTrue() {
        boolean testBoolean = tester2.equals(tester2);
        assertTrue(testBoolean);
    }

    @org.junit.jupiter.api.Test
    void equalsTestFalse() {
        boolean testBoolean = tester2.equals(tester);
        assertFalse(testBoolean);
    }

    @org.junit.jupiter.api.Test
    void getId() {
        int id = tester.getId();
        assertEquals(0, id);
    }

    @org.junit.jupiter.api.Test
    void getUsername() {
        assertEquals("TesterNameOne", tester.getUsername());
    }

    @org.junit.jupiter.api.Test
    void getOutStream() {
        assertNull(tester.getOutStream());
    }
}