package GameStateStuff;

import static org.junit.Assert.*;

/**
 * Created by Antonivar on 2017-04-20.
 */
public class GameStateHandlerTest {
    private GameStateHandler gsm = new GameStateHandler();

    @org.junit.Test
    public void getCurrentState() throws Exception {
        assertEquals(0, gsm.getCurrentState());
        //gsm.setState(1); // need to pass these tests
        //assertEquals(1, gsm.getCurrentState());

    }

}