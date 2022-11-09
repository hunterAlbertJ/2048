package com.games.TwentyFourtyEight.Controller;

import com.games.TwentyFourtyEight.controller.Controller;
import junit.framework.TestCase;
import org.junit.Test;

import java.awt.event.KeyEvent;
import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void testTyped_shouldReturnFalse_withValidKeyEvent() {
        Controller testController = new Controller();
        assertFalse(testController.typed(KeyEvent.VK_DOWN));
    }
}