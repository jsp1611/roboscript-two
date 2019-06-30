package com.singular.cw.robo;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author jon
 */
public class RoboScriptTest {

    @Test
    public void executeWithNoMovement() {
        String grid = RoboScript.execute("");
        assertThat(grid, equalTo("*"));
    }

    @Test
    public void executeWithSingleF() {
        String grid = RoboScript.execute("F");
        assertThat(grid, equalTo("**"));
    }

    @Test
    public void executeWithAboutTurnWithRAndF() {
        String grid = RoboScript.execute("RRF");
        assertThat(grid, equalTo("**"));
    }

    @Test
    public void executeWithAboutTurnWithLAndF() {
        String grid = RoboScript.execute("LLF");
        assertThat(grid, equalTo("**"));
    }

    @Test
    public void executeWithFourF() {
        String grid = RoboScript.execute("FFFF");
        assertThat(grid, equalTo("*****"));
    }

    @Test
    public void drawASquare() {
        String grid = RoboScript.execute("FFFFLFFFFLFFFFLFFFF");
        String expected =   "*****\r\n" +
                            "*   *\r\n" +
                            "*   *\r\n" +
                            "*   *\r\n" +
                            "*****";
        assertThat(grid, equalTo(expected));
    }

    @Test
    public void drawABiggerSquare() {
        String grid = RoboScript.execute("FFFFFLFFFFFLFFFFFLFFFFFL");
        String expected = "******\r\n" +
                          "*    *\r\n" +
                          "*    *\r\n" +
                          "*    *\r\n" +
                          "*    *\r\n" +
                          "******";
        assertThat(grid, equalTo(expected));
    }

    @Test
    public void drawALoop() {
        String grid = RoboScript.execute("LFFFFFRFFFRFFFRFFFFFFF");
        String expected = "    ****\r\n" +
                          "    *  *\r\n" +
                          "    *  *\r\n" +
                          "********\r\n" +
                          "    *   \r\n" +
                          "    *   ";
        assertThat(grid, equalTo(expected));
    }

    @Test
    public void reverseASquareAndSecondPass() {
        String grid = RoboScript.execute("RFFFFRFFFFRFFFFRFFFFRFFFFRFFFFRFFFF");
        String expected =   "*****\r\n" +
                            "*   *\r\n" +
                            "*   *\r\n" +
                            "*   *\r\n" +
                            "*****";
        assertThat(grid, equalTo(expected));
    }

    @Test
    public void cross() {
        String grid = RoboScript.execute("FFFFRRFFLFFFF");
        String expected =   "*****\r\n" +
                            "  *  \r\n" +
                            "  *  \r\n" +
                            "  *  \r\n" +
                            "  *  ";
        assertThat(grid, equalTo(expected));
    }

    @Test
    public void commandWithNumSimple() {
        String grid = RoboScript.execute("F1");
        assertThat(grid, equalTo("**"));
    }

    @Test
    public void commandMultiple() {
        String grid = RoboScript.execute("F4");
        assertThat(grid, equalTo("*****"));
    }

    @Test
    public void drawALoopWithNums() {
        String grid = RoboScript.execute("LF5RF3RF3RF7");
        String expected = "    ****\r\n" +
                          "    *  *\r\n" +
                          "    *  *\r\n" +
                          "********\r\n" +
                          "    *   \r\n" +
                          "    *   ";
        assertThat(grid, equalTo(expected));
    }

    @Test
    public void spiral() {
        String grid = RoboScript.execute("F2LF3LF4LF5LF6LF7LF8LF9LF10");
        String expected = "*********  \r\n" +
                          "*       *  \r\n" +
                          "* ***** *  \r\n" +
                          "* *   * *  \r\n" +
                          "* *   * *  \r\n" +
                          "* * *** *  \r\n" +
                          "* *     *  \r\n" +
                          "* *******  \r\n" +
                          "*          \r\n" +
                          "***********";
        assertThat(grid, equalTo(expected));
    }

    @Test
    public void longTrail() {
        String grid = RoboScript.execute("F22");
        String expected = "***********************";
        assertThat(grid, equalTo(expected));
    }
}
