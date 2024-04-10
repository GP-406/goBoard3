//package com.example.project;
// go pieces ◯ ●
// *Imports*

import java.util.Arrays;
import java.util.Scanner;

//--------------------------


//!!!*Beginning of primary Class*!!!
public class goBoard {

    // ***Global Variables***
    static int boardSize = 9;
    //static String[][] goBoard = new String[boardSize][boardSize];
    static String [][] statusArray = new String[boardSize][boardSize];
    static String[][] goBoard = {
                                    {"●",null,null,null,null,null,null,null,null},
                                    {"◯","●",null,null,null,null,null,null,null},
                                    {"●",null,null,"◯",null,null,null,null,null},
                                    {null,null,"◯","●","◯",null,null,null,null},
                                    {null,null,"◯","●","◯",null,null,null,null},
                                    {null,null,"◯","●","◯",null,null,null,null},
                                    {null,null,null,"◯",null,null,null,null,null},
                                    {null,null,null,null,null,null,null,"●","●"},
                                    {null,null,null,null,null,null,null,"●","◯"},
                                }; 
    static boolean [][] visited = new boolean[boardSize][boardSize];
    static int blackScore = 0, whiteScore = 0;

    //--------------------------------------------------------------
    
 
    // *** Methods ***

    // resets visited array to false to aid in isAliveRecursive.
    private static void recentVisited() {
        for (boolean[] row : visited) {
            Arrays.fill(row, false);
        }
    }

    // Checks if a piece is alive
    public static boolean isAlive(int y, int x) {
        recentVisited();
        return isAliveRecursive(y, x, goBoard[y][x]);
    }

    // Checks if a single piece can breathe.
    static boolean canBreathe(int y, int x){
        if(x < boardSize - 1 && goBoard[y][x+1] == null){
            return true;
        }
        if(x > 0 && goBoard[y][x-1] == null){
            return true;
        }
        if(y < boardSize - 1 && goBoard[y+1][x] == null){
            return true;
        }
        if(y > 0 && goBoard[y-1][x] == null){
            return true;
        }
        return false;
    }

    // Recursivly goes through all pieces on the board checking if they are alive and placing them in visited.
    private static boolean isAliveRecursive(int y, int x, String pieceColor) {
        if (y < 0 || x < 0 || y >= boardSize || x >= boardSize || visited[y][x]) {
            return false;
        }
        visited[y][x] = true;
        // If canBreathe == true, the loop ends.
        if(canBreathe(y, x)) {
            return true;
        }

        if (y < boardSize-1 && goBoard[y+1][x] == pieceColor) {
            return isAliveRecursive(y+1, x, goBoard[y+1][x]);
        }
        if (y > 0 && goBoard[y-1][x] == pieceColor) {
            return isAliveRecursive(y-1, x, goBoard[y-1][x]);
        }
        if (x < boardSize-1 && goBoard[y][x+1] == pieceColor) {
            return isAliveRecursive(y, x+1, goBoard[y][x+1]);
        }
        if (x > 0 && goBoard[y][x-1] == pieceColor) {
            return isAliveRecursive(y, x-1, goBoard[y][x-1]);
        }
        return false;
    }

    // Prints an array showing the status of all game positions.
    public static void findDeadAlive() { 
        // Iterate through the board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Check if the stone is alive
                if (goBoard[i][j] != null && isAlive(i, j)) {
                    statusArray[i][j] = "[Alive]";
                } 
                else if (goBoard[i][j] != null && !isAlive(i,j)) {
                    statusArray[i][j] = "[Dead] ";
                }
                else {
                    statusArray[i][j] = "[Empty]";
                }
            }
        }
    }

    public static void printDeadAlive() {
        // Print the status array
        System.out.println("Status of stones (Alive/Dead):");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(statusArray[i][j] + " ");
            }
            System.out.println(); // Move to the next row
        }
    }

    public static int calcDeadScore() {
        for (int i = 0; i < goBoard.length; i++) {
            for (int j = 0; j < goBoard.length; j++) {
                if (goBoard[i][j] == "●" && statusArray[i][j] == "[Dead] ") {
                    whiteScore--;
                    goBoard[i][j] = null;     
                }
                else if (goBoard[i][j] == "◯" && statusArray[i][j] == "[Dead] ") {
                    blackScore--;
                    goBoard[i][j] = null;
                }
            }
        }
        return blackScore + whiteScore;
    }

    public static void printBoard() {
        System.out.print("  ");
        for (int i = 0; i < goBoard.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println("");
        for (int i = 0; i < goBoard.length; i++) {
            System.out.print(i + " ");
            for(int j = 0; j <goBoard.length; j++) {
                if (goBoard[i][j] == null) {
                    System.out.print("+");
                }
                else{  System.out.print(goBoard[i][j]);}

                if(j < goBoard.length -1); 
                    System.out.print(" ");
                
                }
            System.out.println();
            }
        }
    




    //--------------------------------------------------------
    

    // !!!* Beginning of Main Code *!!!
    public static void main(String[] args) {
        
        Scanner userMove = new Scanner(System.in); // Scanner object for getting user input, parsing it
        Boolean cont = true, turn = true; // The condition on which our game loop hangs
        Boolean lastMoveWasPass = false;
        int moveX, moveY;
        String white = "●", black = "◯";



        while (cont) {
            String input;
            //printDeadAlive();
            System.out.println("Player " + (turn ? "1" : "2") + ", enter 'p' to pass or the Y coordinate of your move:");
            input = userMove.nextLine().trim(); // Read the input as string
    
            // Check for pass
            if ("p".equalsIgnoreCase(input)) {
                if (lastMoveWasPass) {
                    System.out.println("Both players passed. Ending game.");
                    findDeadAlive();
                    //printDeadAlive();
                    calcDeadScore();

                    // Asks the user if they would like to calculate territory manually
                    System.out.println("Unfortunately the programmer did not figure out how to calculate territory,");
                    System.out.println("Would you like to calculate territory manually? [y]/[n]");
                    input = userMove.nextLine().trim();
                    if ("y".equalsIgnoreCase(input)) {
                        int territoryScore;
                        printBoard();
                        System.out.println("Points to be added to white?");
                        territoryScore = userMove.nextInt();
                        whiteScore += territoryScore;
                        System.out.println("Points to be added to Black?");
                        territoryScore = userMove.nextInt();
                        blackScore += territoryScore;
                    }
                    System.out.println("Final Score");
                    System.out.println("White's Score: " + whiteScore);
                    System.out.println("Black's Score: " + blackScore);
                    userMove.close();
                    break; // Both players passed consecutively, end game
                } else {
                    lastMoveWasPass = true; // Current player passed, set flag
                    turn = !turn; // Switch turns
                    continue; // Skip the rest of the loop
                }
            } else {
                // Try to parse integers from input
                // Asks user for X coordinate of move.
                try {
                    moveY = Integer.parseInt(input);
                    System.out.println("Now enter the X coordinate of your move:");
                    moveX = Integer.parseInt(userMove.nextLine().trim()); // Assume valid integer input for simplicity
                    lastMoveWasPass = false; // Reset pass flag as a valid move is made
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number or 'p' to pass.");
                    continue;
                }
            }

            // Checks if move is out of bounds.
            if (moveY < 0 || moveX < 0 || moveY > 8 || moveX > 8) {
                System.out.println("Invalid move. Please choose again.");
                    continue;
            }

            // Checks if move is taken.
            if (goBoard[moveY][moveX] == null && turn == true) {
                goBoard[moveY][moveX] = black;
            }
            else if (goBoard[moveY][moveX] == null && turn == false) {
                goBoard[moveY][moveX] = white;
            }
            else {
                System.out.println("Invalid move. Please choose again.");
                continue;
            }

            findDeadAlive();
            //printDeadAlive();
            calcDeadScore();
            // Prints board after each turn.
            printBoard();

            // Flips turn.
            turn = !turn;
            System.out.println();
            }
            System.out.println();
        }
    };

