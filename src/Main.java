import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
    static JLabel label, pvpLabel, aiLabel, scoreLabel;
    static JFrame menuFrame;
    static JPanel menuPanel, pvpPanel, aiPanel, startpanal, gamePanel;
    static JButton pvpButton, aiButton, exitButton, startButton, backButton;
    static JButton[][] boardButtons;
    static JComboBox<String> boardSizeList, difficultyLevel;
    static int boardSize = 3;
    static int[][] board;
    static boolean playerTurn = true;
    static int playerScore = 0, aiScore = 0, draws = 0;
    static Random random = new Random();
    static boolean pvp = true;

    public static void main(String[] args) {

        menuFrame = new JFrame("TIC-TAC-TOE");
        menuFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuFrame.setBounds(400, 70, 700, 700);
        menuFrame.setLayout(null);

        // Menu panel
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 0, 700, 700);
        menuPanel.setBackground(Color.black);
        menuFrame.add(menuPanel);

        Font gameFont = new Font("My Font", Font.ROMAN_BASELINE, 30);

        // Welcome label
        label = new JLabel("WELCOME TO TIC-TAC-TOE");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(gameFont);
        label.setForeground(Color.WHITE);
        label.setBounds(90, 30, 490, 80);
        menuPanel.add(label);

        // PvP button
        pvpButton = new JButton("PLAYER VS PLAYER");
        pvpButton.setBounds(100, 150, 480, 80);
        pvpButton.setFont(gameFont);
        pvpButton.setBackground(Color.darkGray);
        pvpButton.setForeground(Color.WHITE);
        menuPanel.add(pvpButton);

        // AI button
        aiButton = new JButton("AI VS PLAYER");
        aiButton.setBounds(100, 300, 480, 80);
        aiButton.setFont(gameFont);
        aiButton.setBackground(Color.darkGray);
        aiButton.setForeground(Color.WHITE);
        menuPanel.add(aiButton);

        // Exit button
        exitButton = new JButton("EXIT");
        exitButton.setBounds(100, 450, 480, 80);
        exitButton.setFont(gameFont);
        exitButton.setBackground(Color.red);
        exitButton.setForeground(Color.WHITE);
        menuPanel.add(exitButton);

        menuFrame.setVisible(true);

        // Exit button action listener
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // PvP button action listener
        pvpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pvp=true;
                menuFrame.remove(menuPanel);
                pvpPanel = new JPanel();
                pvpPanel.setLayout(null);
                pvpPanel.setBounds(0, 0, 700, 700);
                pvpPanel.setBackground(Color.black);
                menuFrame.add(pvpPanel);

                menuFrame.revalidate();
                menuFrame.repaint();

                // Board size combobox
                String[] size = {"3x3", "4x4", "5x5"};
                boardSizeList = new JComboBox<>(size);
                boardSizeList.setBounds(50, 60, 100, 50);
                boardSizeList.setFont(gameFont);
                boardSizeList.setBackground(Color.white);
                pvpPanel.add(boardSizeList);

                // PvP board size label
                pvpLabel = new JLabel("SELECT THE BOARD SIZE");
                pvpLabel.setHorizontalAlignment(JLabel.LEFT);
                pvpLabel.setFont(new Font("pvp font", Font.PLAIN, 25));
                pvpLabel.setForeground(Color.WHITE);
                pvpLabel.setBounds(200, 45, 490, 80);
                pvpPanel.add(pvpLabel);

                // Start button for PvP
                startButton = new JButton("START GAME");
                startButton.setBounds(100, 200, 480, 80);
                startButton.setBackground(Color.green);
                startButton.setForeground(Color.WHITE);
                startButton.setFont(gameFont);
                pvpPanel.add(startButton);

                startButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boardSize = Integer.parseInt(((String) boardSizeList.getSelectedItem()).substring(0, 1));
                        menuFrame.remove(pvpPanel);
                        startpanal = new JPanel();
                        startpanal.setLayout(null);
                        startpanal.setBounds(0, 0, 700, 700);
                        startpanal.setBackground(Color.black);
                        menuFrame.add(startpanal);

                        menuFrame.revalidate();
                        menuFrame.repaint();
                        backButton = new JButton("<~");
                        backButton.setBounds(10, 610, 90, 50);
                        backButton.setBackground(Color.RED);
                        backButton.setForeground(Color.WHITE);
                        backButton.setFont(gameFont);
                        startpanal.add(backButton);
                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuFrame.remove(startpanal);
                                menuFrame.add(pvpPanel);
                                menuFrame.revalidate();
                                menuFrame.repaint();
                            }
                        });
                        // Score label
                        scoreLabel = new JLabel("player 1: " + playerScore + "            Player 2: " + aiScore + "         draws: " + draws);
                        scoreLabel.setBounds(0, 0, 600, 100);
                        scoreLabel.setBackground(Color.LIGHT_GRAY);
                        scoreLabel.setForeground(Color.WHITE);
                        scoreLabel.setFont(gameFont);
                        startpanal.add(scoreLabel);
                        // Game panel
                        gamePanel = new JPanel();
                        gamePanel.setBounds(90, 100, 500, 500);
                        gamePanel.setFont(gameFont);
                        gamePanel.setBackground(Color.LIGHT_GRAY);
                        gamePanel.setForeground(Color.CYAN);
                        startpanal.add(gamePanel);

                        initializeGameBoard(gamePanel);
                    }
                });

                // Back button for PvP
                backButton = new JButton("BACK");
                backButton.setBounds(100, 350, 480, 80);
                backButton.setBackground(Color.RED);
                backButton.setForeground(Color.WHITE);
                backButton.setFont(gameFont);
                pvpPanel.add(backButton);
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuFrame.remove(pvpPanel);
                        menuFrame.add(menuPanel);
                        menuFrame.revalidate();
                        menuFrame.repaint();
                    }
                });
            }
        });

        // AI button action listener
        aiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pvp = false;
                menuFrame.remove(menuPanel);
                aiPanel = new JPanel();
                aiPanel.setLayout(null);
                aiPanel.setBounds(0, 0, 700, 700);
                aiPanel.setBackground(Color.black);
                menuFrame.add(aiPanel);

                menuFrame.revalidate();
                menuFrame.repaint();

                // AI board size label
                aiLabel = new JLabel("SELECT BOARD SIZE");
                aiLabel.setHorizontalAlignment(JLabel.LEFT);
                aiLabel.setFont(new Font("ai font", Font.PLAIN, 25));
                aiLabel.setForeground(Color.WHITE);
                aiLabel.setBounds(0, 0, 300, 80);
                aiPanel.add(aiLabel);

                // Board size combobox
                String[] size = {"3x3", "4x4", "5x5"};
                boardSizeList = new JComboBox<>(size);
                boardSizeList.setBounds(70, 60, 100, 50);
                boardSizeList.setFont(gameFont);
                boardSizeList.setBackground(Color.white);
                aiPanel.add(boardSizeList);

                // AI difficulty level label
                aiLabel = new JLabel("SELECT DIFFICULTY LEVEL");
                aiLabel.setHorizontalAlignment(JLabel.LEFT);
                aiLabel.setFont(new Font("ai font", Font.PLAIN, 25));
                aiLabel.setForeground(Color.WHITE);
                aiLabel.setBounds(350, 0, 300, 80);
                aiPanel.add(aiLabel);

                // Difficulty level combobox
                String[] level = {"Easy", "Medium", "High"};
                difficultyLevel = new JComboBox<>(level);
                difficultyLevel.setBounds(450, 60, 110, 50);
                difficultyLevel.setFont(gameFont);
                difficultyLevel.setBackground(Color.white);
                aiPanel.add(difficultyLevel);

                // Start button for AI
                startButton = new JButton("START GAME");
                startButton.setBounds(100, 200, 480, 80);
                startButton.setBackground(Color.green);
                startButton.setForeground(Color.WHITE);
                startButton.setFont(gameFont);
                aiPanel.add(startButton);

                // Back button for AI
                backButton = new JButton("BACK");
                backButton.setBounds(100, 350, 480, 80);
                backButton.setBackground(Color.RED);
                backButton.setForeground(Color.WHITE);
                backButton.setFont(gameFont);
                aiPanel.add(backButton);
                backButton.addActionListener(e1 -> {
                    menuFrame.remove(aiPanel);
                    menuFrame.add(menuPanel);
                    menuFrame.revalidate();
                    menuFrame.repaint();
                });

                startButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boardSize = Integer.parseInt(((String) boardSizeList.getSelectedItem()).substring(0, 1));
                        menuFrame.remove(aiPanel);
                        startpanal = new JPanel();
                        startpanal.setLayout(null);
                        startpanal.setBounds(0, 0, 700, 700);
                        startpanal.setBackground(Color.black);
                        menuFrame.add(startpanal);

                        menuFrame.revalidate();
                        menuFrame.repaint();
                        backButton = new JButton("<~");
                        backButton.setBounds(10, 610, 90, 50);
                        backButton.setBackground(Color.RED);
                        backButton.setForeground(Color.WHITE);
                        backButton.setFont(gameFont);
                        startpanal.add(backButton);
                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuFrame.remove(startpanal);
                                menuFrame.add(aiPanel);
                                menuFrame.revalidate();
                                menuFrame.repaint();
                            }
                        });
                        // Score label
                        scoreLabel = new JLabel("player : " + playerScore + "           ai score: " + aiScore + "         draws: " + draws);
                        scoreLabel.setBounds(0, 0, 600, 100);
                        scoreLabel.setBackground(Color.LIGHT_GRAY);
                        scoreLabel.setForeground(Color.WHITE);
                        scoreLabel.setFont(gameFont);
                        startpanal.add(scoreLabel);
                        // Game panel
                        gamePanel = new JPanel();
                        gamePanel.setBounds(90, 100, 500, 500);
                        gamePanel.setFont(gameFont);
                        gamePanel.setBackground(Color.LIGHT_GRAY);
                        gamePanel.setForeground(Color.CYAN);
                        startpanal.add(gamePanel);

                        initializeGameBoard(gamePanel);
                    }
                });
            }
        });
    }

    private static void initializeGameBoard(JPanel gamePanel) {
        gamePanel.removeAll();  // Clear existing components

        int size = boardSize;   // Get the current board size
        gamePanel.setLayout(new GridLayout(size, size));

        boardButtons = new JButton[size][size];
        board = new int[size][size];  // Initialize the board array

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                boardButtons[i][j].setFocusPainted(false);
                boardButtons[i][j].setBackground(Color.WHITE);
                boardButtons[i][j].setOpaque(true);
                boardButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        int row = -1, col = -1;
                        for (int i = 0; i < size; i++) {
                            for (int j = 0; j < size; j++) {
                                if (boardButtons[i][j] == button) {
                                    row = i;
                                    col = j;
                                    break;
                                }
                            }
                            if (row != -1) break;
                        }
                        if (board[row][col] == 0) { // Check if the cell is empty
                            board[row][col] = playerTurn ? 1 : 2; // 1 for player, 2 for AI
                            button.setText(playerTurn ? "X" : "O");
                            button.setEnabled(false); // Disable the button after it's clicked

                            if (checkWin(row, col)) {
                                JOptionPane.showMessageDialog(menuFrame, (playerTurn ? "Player" : "AI") + " wins!");
                                if (playerTurn) playerScore++;
                                else aiScore++;
                                resetGame();
                            } else if (isBoardFull()) {
                                JOptionPane.showMessageDialog(menuFrame, "It's a draw!");
                                draws++;
                                resetGame();
                            } else {
                                playerTurn = !playerTurn; // Switch turn
                                if (!playerTurn&&!pvp) {
                                    aiPlay(); // AI's turn
                                }
                            }
                        }
                    }
                });
                gamePanel.add(boardButtons[i][j]);
            }
        }
        gamePanel.revalidate();  // Revalidate the panel to apply changes
        gamePanel.repaint();     // Repaint the panel to show updates
    }

    private static void aiPlay() {
        // Simple AI implementation (random move)
        int row, col;
        do {
            row = random.nextInt(boardSize);
            col = random.nextInt(boardSize);
        } while (board[row][col] != 0);
        board[row][col] = 2;
        boardButtons[row][col].setText("O");
        boardButtons[row][col].setEnabled(false);

        if (checkWin(row, col)) {
            JOptionPane.showMessageDialog(menuFrame, "AI wins!");
            aiScore++;
            resetGame();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(menuFrame, "It's a draw!");
            draws++;
            resetGame();
        } else {
            playerTurn = true; // Switch turn back to player
        }
    }

    private static boolean checkWin(int row, int col) {
        // Check rows, columns, and diagonals
        int player = board[row][col];
        boolean win = true;

        // Check row
        for (int j = 0; j < boardSize; j++) {
            if (board[row][j] != player) {
                win = false;
                break;
            }
        }
        if (win)
        {return true;}

        // Check column
        win = true;
        for (int i = 0; i < boardSize; i++) {
            if (board[i][col] != player) {
                win = false;
                break;
            }
        }
        if (win) {return true;}

        // Check diagonal (top-left to bottom-right)
        if (row == col) {
            win = true;
            for (int i = 0; i < boardSize; i++) {
                if (board[i][i] != player) {
                    win = false;
                    break;
                }
            }
            if (win) {return true;}
        }

        // Check diagonal (top-right to bottom-left)
        if (row + col == boardSize - 1) {
            win = true;
            for (int i = 0; i < boardSize; i++) {
                if (board[i][boardSize - 1 - i] != player) {
                    win = false;
                    break;
                }
            }
        }

        return win;
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void resetGame() {

        board = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardButtons[i][j].setText("");
                boardButtons[i][j].setEnabled(true);
            }
        }
        playerTurn = true; // Player starts the new game
        scoreLabel.setText("player 1: " + playerScore + "           Player 2: " + aiScore + "         draws: " + draws);
    }
}
