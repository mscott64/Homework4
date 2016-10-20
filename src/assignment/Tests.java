package assignment;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static solution.StaticMethods.*;

public class Tests {

    @Test
    public void testBinarySearch() {
        assertEquals(true, binarySearch(new int[]{1, 3, 5, 7, 9, 11}, 7));
        assertEquals(true, binarySearch(new int[]{1}, 1));

    }

    @Test
    public void testStringMap() {
        assertEquals(new HashMap<String, String>(), stringMap(new String[]{}));
        Map<String, String> map = new HashMap<String, String>();
        map.put("TestKey", "TestValue");
        assertEquals(map, stringMap(new String[]{"TestKey", "TestValue", "Hanging value"}));
    }

    @Test
    public void testDidWin() {
        char[][] game = new char[][]{
                {'X', 'O', 'X'},
                {'O', 'X', 'O'},
                {'X', 'O', 'X'}
        };
        assertEquals(true, didWin(game, 'X'));
        assertEquals(false, didWin(game, 'O'));
    }

    @Test
    public void testSumDigits() {
        assertEquals(1, sumDigits(1));
        assertEquals(1, sumDigits(11));
    }
}
