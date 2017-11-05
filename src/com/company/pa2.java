package com.company;

/**
 *
 * Algorithms PA2
 * Taylor Coury and Melinda Grad
 * Fall 2017
 *
 */
import java.io.*;
import java.util.*;

public class pa2 {

    public static void main(String[] args) {

        // Get input and output files from cmd line
        File infile = new File(args[0]);
        File outfile = new File(args[1]);
        Scanner scanner = null;

        try {
            scanner = new Scanner(infile);
        }
        catch (FileNotFoundException e){
            System.out.print(e);
        }

        //Init board
        int[][] board = new int[10][10];
        while(scanner.hasNext()){
            board[scanner.nextInt()][scanner.nextInt()] = scanner.nextInt();
        }

        //Print Board
        for(int i = 0; i < 9; i ++){
            for(int j=0; j<9; j++){
                System.out.print(board[i][j] + "");
            }
            System.out.print("\n");
        }
    }
}
