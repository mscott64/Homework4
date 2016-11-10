package solution;

import java.util.*;

/**
 * Mastermind is a code-breaking game. The codebreaker tries to guess the pattern (called code in this class),
 * in both order and color, within ten turns. Each guess is made by entering a series of characters where each
 * character represents a color.
 *
 * The code-maker (in this case the computer) provides feedback by returning a count of the number of right
 * and wrong colors in the pattern. An element is right if it is correct in both color and position.
 * An element is wrong if it is the correct color code peg placed in the wrong position.
 * If there are duplicate colours in the guess, they cannot all be counted in the feedback unless
 * they correspond to the same number of duplicate colours in the hidden code.
 */
public class Mastermind {

    private static final int CODE_LENGTH = 4;
    private static final int TOTAL_GUESSES = 10;
    private static final String AVAILABLE_COLORS = "BGPRWY";
    private static final int NUM_COLORS = AVAILABLE_COLORS.length();

    private char[] code;
    private char[] guessChars;
    private int guesses;
    private Random rand;
    private Scanner scanner;
    private Feedback fb;
    private List<Character> remaining;

    public Mastermind() {
        scanner = new Scanner(System.in);
        code = new char[CODE_LENGTH];
        guessChars = new char[CODE_LENGTH];
        fb = new Feedback();
        remaining = new ArrayList<Character>();
        guesses = TOTAL_GUESSES;
        rand = new Random();
        generateCode();
        System.out.println("Welcome to Mastermind! You get 10 tries to guess the code.");
        System.out.println("There are 6 possible colors in the code red(R), blue(B), green(G),\n" +
                "yellow(Y), white(W), and purple(P). Good luck!");
        System.out.println("Type QUIT at any point to end a game.");
    }

    /**
     * Generate a new code and reset the number of guesses.
     */
    private void generateCode() {
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = rand.nextInt(NUM_COLORS);
            code[i] = AVAILABLE_COLORS.charAt(index);
        }
        guesses = TOTAL_GUESSES;
        fb.reset();
    }

    /**
     * Determine the feedback for a given pattern.
     */
    private void testPattern(String guess) throws Exception {
        if (guess.length() != CODE_LENGTH) {
            throw new Exception("Invalid guess: " + guess);
        }
        // End the game
        if (guess.equals("QUIT")) {
            guesses = 0;
            return;
        }
        // Clear old data
        fb.reset();
        remaining.clear();
        // Iterate through the guess characters.
        for (int i = 0; i < CODE_LENGTH; i++) {
            char curr = guess.charAt(i);
            // If a character is invalid, make the user guess again.
            if (AVAILABLE_COLORS.indexOf(curr) == -1) {
                throw new Exception("Invalid character: " + curr);
            }
            // If the character matches, add one to the right count.
            if (curr == code[i]) {
                fb.right();
                guessChars[i] = ' '; // This character will be ignored when looking for colors in the wrong position.
            } else {
                remaining.add(code[i]);
                guessChars[i] = curr;
            }
        }
        for (int i = 0; i < CODE_LENGTH; i++) {
            char curr = guessChars[i];
            // If a character is the right color, but in the wrong place, add one to the wrong count.
            if (remaining.contains(curr)) {
                fb.wrong();
                remaining.remove(Character.valueOf(curr));
            }
        }
    }

    private boolean playTurn(int guessNumber) {
        // Get pattern guess from user
        System.out.print(String.format("%d. Enter a guess: ", guessNumber));
        String guess = scanner.next().trim().toUpperCase();
        String result;
        try {
            testPattern(guess);
            guesses--;
            result = fb.toString();
        } catch (Exception e) {
            result = e.getMessage();
        }

        System.out.println(result);
        return fb.correct();
    }

    // Returns a string of the letters in the current code.
    private String codeString() {
        return new String(code);
    }

    public boolean playGame() {
        while(guesses > 0) {
            if (playTurn(TOTAL_GUESSES + 1 - guesses)) {
                System.out.println("Correct!");
                break;
            }
        }
        System.out.println("The code was " + codeString());
        generateCode();
        System.out.print("Play again? Enter \'yes\' to start a new game. ");
        String answer = scanner.next().trim().toUpperCase();
        return answer.equals("YES");
    }

    private class Feedback {
        private int right = 0;
        private int wrong = 0;

        public void reset() {
            right = 0;
            wrong = 0;
        }

        public void right() {
            this.right++;
        }

        public void wrong() {
            this.wrong++;
        }

        public boolean correct() {
            return right == 4;
        }

        @Override
        public String toString() {
            return String.format("Right=%d Wrong=%d", right, wrong);
        }
    }

    public static void main(String[] args) {
        Mastermind m = new Mastermind();
        int i = 1;
        final String ROUND = "Round %d";
        System.out.println(String.format(ROUND, i++));
        while(m.playGame()) {
            System.out.println(String.format(ROUND, i++));
        }
    }

}
