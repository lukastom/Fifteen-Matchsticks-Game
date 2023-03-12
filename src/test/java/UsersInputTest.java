import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class UsersInputTest {

    //TODO: rewrite it all using Mockito.

    // Set input for System.in
    private void setInput(String input) {
        //String -> byte[] -> stream
        InputStream in = new ByteArrayInputStream(input.getBytes());
        //Replace default System.in with our stream
        System.setIn(in);
    }

    // Reset System.in to the default value
    private void resetInput() {
        System.setIn(System.in);
    }

    @Test
    void TestMinGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = min + System.lineSeparator();

        setInput(input);
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals(min, resultInt);
        //resetInput();
    }

    @Test
    void TestMaxGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = max + System.lineSeparator();

        setInput(input);
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals(max, resultInt);
        //resetInput();
    }

    @Test
    void TestWithinGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = "2" + System.lineSeparator();

        setInput(input);
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals(2, resultInt);
        resetInput();
    }

    @Test
    void TestUnderGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = "-5" + System.lineSeparator();

        setInput(input);
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals("You can not enter less then " + min + ", try again!", String.valueOf(resultInt));
        resetInput();
    }

    @Test
    void TestOverGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = "5" + System.lineSeparator();

        setInput(input);
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals("You can not enter more than " + max + ", try again!", resultInt);
        resetInput();
    }

    @Test
    void TestMismatchGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = "A" + System.lineSeparator();

        setInput(input);
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals("You have to enter a number, try again!", resultInt);
        resetInput();
    }
}