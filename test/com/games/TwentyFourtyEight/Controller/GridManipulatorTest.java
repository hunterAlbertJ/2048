package com.games.TwentyFourtyEight.Controller;

import com.games.TwentyFourtyEight.controller.GridManipulator;
import org.junit.Test;

import static org.junit.Assert.*;

public class GridManipulatorTest {

    @Test
    public void shouldReturnBoolean_whenGameIsValid() {
        GridManipulator manipulator = new GridManipulator();
        assertTrue(manipulator.isGameValid());
    }

    @Test
    public void shouldReturnBoolean_whenGameNotIsValid() {
        GridManipulator manipulator = new GridManipulator();
        assertEquals(true, manipulator.isGameValid());
    }

    @Test
    public void shouldReturnNewBoard_whenBoardIsNull() {
        GridManipulator manipulator = new GridManipulator();

    }

}