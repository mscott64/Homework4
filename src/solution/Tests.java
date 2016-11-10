package solution;

import static solution.StaticMethods.binarySearch;
import static solution.StaticMethods.stringMap;
import static solution.StaticMethods.didWin;
import static solution.StaticMethods.sumDigits;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Tests {

    @Test
    public void testBinarySearch() {
        assertEquals(true, binarySearch(new int[]{1, 3, 5, 7, 9, 11}, 7));
        assertEquals(true, binarySearch(new int[]{1}, 1));
        assertEquals(true, binarySearch(new int[]{1, 3, 5, 7, 9, 11}, 11));
        assertEquals(true, binarySearch(new int[]{1, 3, 5, 7, 9, 11}, 1));
        assertEquals(true, binarySearch(new int[]{1, 3, 5, 7, 9, 11}, 5));
        assertEquals(false, binarySearch(new int[]{1, 3, 5, 7, 9, 11}, 12));
        assertEquals(false, binarySearch(new int[]{1, 3, 5, 7, 9, 11}, 6));
        assertEquals(false, binarySearch(new int[]{1, 3, 5, 7, 9, 11}, -1));

    }

    @Test
    public void testStringMap() {
        assertEquals(new HashMap<String, String>(), stringMap(new String[]{}));
        Map<String, String> map = new HashMap<String, String>();
        map.put("TestKey", "TestValue");
        assertEquals(map, stringMap(new String[]{"TestKey", "TestValue", "Hanging value"}));
        map.put("1", "2");
        assertEquals(map, stringMap(new String[]{"TestKey", "TestValue", "1", "2", "Hanging value"}));
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
        game = new char[][]{
                {'O', 'X', 'O'},
                {'X', 'O', 'X'},
                {'O', 'X', 'O'}
        };
        assertEquals(false, didWin(game, 'X'));
        assertEquals(true, didWin(game, 'O'));
    }

    @Test
    public void testSumDigits() {
        assertEquals(1, sumDigits(1));
        assertEquals(1, sumDigits(11));
        assertEquals(7, sumDigits(142124));
    }
}
