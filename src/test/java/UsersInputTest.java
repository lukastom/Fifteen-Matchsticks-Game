import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class UsersInputTest {

    // Set input for System.in
    private void setInput(String input) {
        //String -> byte[] -> stream
        InputStream in = new ByteArrayInputStream(input.getBytes());
        //Replace default System.in with our stream
        System.setIn(in);
    }

    // Set output for System.out
    private final OutputStream output = new ByteArrayOutputStream();

    public void setOutput() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    // Reset System.in and System.out to the default value
    void resetInputOutput() {
        ScannerSystemInSingleton.setInstance(null);
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Minimal value in general MinMaxNumber")
    void TestMinGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = String.valueOf(min) + System.lineSeparator();

        setInput(input);
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals(min, resultInt);
    }

    @Test
    @DisplayName("Maximal value in general MinMaxNumber")
    void TestMaxGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = String.valueOf(max) + System.lineSeparator();

        setInput(input);
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals(max, resultInt);
    }


    @Test
    @DisplayName("Value within range in general MinMaxNumber")
    void TestWithinGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = "2" + System.lineSeparator();

        setInput(input);
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals(2, resultInt);
    }

    @Test
    @DisplayName("Value below range in general MinMaxNumber")
    void TestUnderGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = "-5" + System.lineSeparator() + "2" + System.lineSeparator();

        setInput(input);
        setOutput();
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals(prompt + System.lineSeparator() + "You can not enter less then " + min + ", try again!" + System.lineSeparator(), output.toString());
    }

    @Test
    @DisplayName("Value over range in general MinMaxNumber")
    void TestOverGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = "5" + System.lineSeparator() + "2" + System.lineSeparator();

        setInput(input);
        setOutput();
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals(prompt + System.lineSeparator() + "You can not enter more than " + max + ", try again!" + System.lineSeparator(), output.toString());
    }

    @Test
    @DisplayName("Wrong type value in general MinMaxNumber")
    void TestMismatchGeneralMinMaxNumber() {
        UsersInput testUsersInput = new UsersInput();

        int min = 1;
        int max = 3;
        String prompt = "Enter a number between " + min + " and " + max + ":";
        String input = "A" + System.lineSeparator() + "2" + System.lineSeparator();;

        setInput(input);
        setOutput();
        int resultInt = testUsersInput.MinMaxNumber(prompt, min, max);
        assertEquals(prompt + System.lineSeparator() + "You have to enter a number, try again!" + System.lineSeparator(), output.toString());
    }
}