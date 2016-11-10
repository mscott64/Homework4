package solution;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MastermindGui extends JFrame implements ActionListener, KeyListener {

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
    private JTextArea window;
    private JTextField entry;

    public MastermindGui() {
        // Init game
        scanner = new Scanner(System.in);
        code = new char[CODE_LENGTH];
        guessChars = new char[CODE_LENGTH];
        fb = new Feedback();
        remaining = new ArrayList<Character>();
        guesses = TOTAL_GUESSES;
        rand = new Random();
        generateCode();

        // Init gui
        window = new JTextArea();
        DefaultCaret caret = (DefaultCaret)window.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        window.setEditable(false);
        window.setText("Welcome to Mastermind! You get 10 tries to guess the code.\n" +
                "Enter your guess in the text area below and hit the guess key (or Enter).\n" +
                "There are 6 possible colors in the code red(R), blue(B), green(G),\n" +
                "yellow(Y), white(W), and purple(P). Good luck!\n"+
                "Type QUIT at any point to end a game.");
        entry = new JTextField(30);
        entry.setEditable(true);
        entry.addKeyListener(this);
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(new JScrollPane(window), BorderLayout.CENTER);
        JPanel guessPanel = new JPanel();
        guessPanel.add(entry);
        guessPanel.add(createGuessButton());
        content.add(guessPanel, BorderLayout.PAGE_END);
        setContentPane(content);
        setTitle("Mastermind");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setVisible(true);//making the frame visible
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

    public void addText(String text) {
        StringBuilder existing = new StringBuilder(window.getText());
        existing.append("\n");
        existing.append(text);
        window.setText(existing.toString());
    }

    public void playTurn() {
        String guess = entry.getText();
        addText(guess);
        String result;
        try {
            testPattern(guess);
            guesses--;
            result = fb.toString();
        } catch (Exception ex) {
            result = ex.getMessage();
        }

        addText(result);
        entry.setText("");
        synchronized (fb) {
            fb.notify();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playTurn();
    }

    private void waitForGuess(int guessNumber) {
        // Get pattern guess from user
        addText(String.format("%d. Enter a guess: ", guessNumber));
        try {
            synchronized (fb) {
                fb.wait();
            }
        } catch(InterruptedException ex) {}
    }

    // Returns a string of the letters in the current code.
    private String codeString() {
        return new String(code);
    }

    public boolean playGame() {
        while(guesses > 0) {
            waitForGuess(TOTAL_GUESSES + 1 - guesses);
            if (fb.correct()) {
                addText("Correct!");
                break;
            }
        }
        addText("The code was " + codeString());
        generateCode();
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            playTurn();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

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

    private JButton createGuessButton() {
        JButton button = new JButton("Guess");
        button.addActionListener(this);
        return button;
    }

    public static void main(String[] args) {
        MastermindGui mg = new MastermindGui();
        int i = 1;
        final String ROUND = "Round %d";
        mg.addText(String.format(ROUND, i++));
        while(mg.playGame()) {
            mg.addText(String.format(ROUND, i++));
        }
    }

}
