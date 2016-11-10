package solution;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * All of these methods have bugs. Use tests to find the bugs and fix them.
 */
public class StaticMethods {

    /**
     * This method should implement the binary search algorithm
     * for a list of sorted integers and some integer value.
     * This method should return true if value is one of the
     * values in arr.
     *
     * A binary search begins by comparing the middle element of the
     * array with the target value. If the target value matches the
     * middle element, its position in the array is returned. If
     * the target value is less than or greater than the middle element,
     * the search continues in the lower or upper half of the array,
     * respectively, eliminating the other half from consideration.
     */
    public static boolean binarySearch(int[] arr, int value) {
        if(arr == null) {
            return false;
        }
        int begin = 0;
        int end = arr.length;
        int mid;
        while (begin <= end) {
            mid = getMiddleIndex(begin, end);
            if (arr[mid] == value) {
                return true;
            } else if(arr[mid] > value) {
                begin = mid + 1;
            } else { // arr[mid] < value
                end = mid - 1;
            }
        }
        return false;
    }

    // Helper method for the binarySearch algorithm. This returns
    // the middle index between begin and end.
    private static int getMiddleIndex(int begin, int end) {
        return (begin + end)/2;
    }

    /**
     * This method takes an array of strings. Each adjacent pair of strings makes
     * a key-value pair. If there are an odd number of strings, the last string is ignored.
     * For example {"a", "b", "c", "d"} should create the map {a:b, c:d} using the format key:value.
     */
    public static Map<String, String> stringMap(String[] arr) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (arr == null) {
            return map;
        }
        for (int i = 0; i < arr.length - 2; i++) {
            map.put(arr[i], arr[i+1]);
        }
        return map;
    }

    /**
     * This method takes in a 3x3 array of characters and a character called player (which should be an 'X' or 'O').
     * Returns true if player has won the game of tic-tac-toe. Winning means that the player has three in a row.
     *
     *  X | O | X
     *  O | X |
     *  X |   | O    X wins!
     */
    public static boolean didWin(char[][] game, char player) {
        if (game == null) {
            return false;
        }
        boolean hasMiddle = game[1][1] == 'X';
        for (int i = 0, j = 2; i <= 2; i++, j--) {
            // Check for a vertical win
            if (player == game[0][i] && player == game[1][i] && player == game[2][i]) {
                return true;
            }
            // Check for a horizontal win
            if (player == game[i][0] && player == game[i][1] && player == game[i][2]) {
                return true;
            }
            // Check along the diagonals
            if (hasMiddle) {
                if (game[0][i] == player && game[2][j] == player) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    /**
     * This method computes the sum of the unique digits in a positive number.
     * sumDigits(12) => 3
     * sumDigits(121) => 3
     * sumDigits(1421) => 7
     */
    public static int sumDigits(int value) {
        if (value < 0) {
            return -1;
        }
        Set<Integer> digits = new HashSet<Integer>();
        while (value != 0) {
            digits.add(value % 2);
            value /= 10;
        }
        int sum = 0;
        for (int i : digits) {
            sum += 1;
        }
        return sum;
    }
}
