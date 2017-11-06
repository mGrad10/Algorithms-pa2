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
        } catch (FileNotFoundException e) {
            System.out.print(e);
        }

        //Init board
        Cell[][] board = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                Cell cell = new Cell(0, false, temp);
                board[i][j] = cell;
            }
        }
        while (scanner.hasNext()) {
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            int num = scanner.nextInt();
            board[row][col].value = num;
            board[row][col].fixed = true;
        }

        updateInitial(board);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j].value + "");
            }
            System.out.print("\n");
        }

        Cell[][] solution = solve(board);

        //TODO: Add more reductions
        //TODO: Print
        //System.out.println(board[0][0].possible);

        if(solution == null){
            System.out.println("NuLLLLL");
        }
        else {
            //Print Board
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(solution[i][j].value + "");
                }
                System.out.print("\n");
            }
        }

    }

    /*
     *  This method solves find the solution unless one is infeasible
     *  @params Cell[][] board
     *  @return boardCopy the complete solution OR Null if none exists
    */
    public static Cell[][] solve(Cell[][] board){
        boolean solutionFlag = true;
        boolean feasibleFlag = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!board[i][j].fixed) {
                    solutionFlag = false;
                    break;
                }
            }
            if (!solutionFlag)
                break;
        }

        if(solutionFlag){ //There is a solution and we've found it!!!!
            return board;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(!board[i][j].fixed && board[i][j].possible.size() == 0){
                    feasibleFlag = false;
                    break;
                }
            }
            if(!feasibleFlag)
                break;
        }
        if(!feasibleFlag)
            return null;

        int min = 10;
        int row = 9, col = 9;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if(!board[i][j].fixed && min > board[i][j].possible.size()){
                    min = board[i][j].possible.size();
                    row = i;
                    col = j;
                }
            }
        }

        for(int i = 0; i < board[row][col].possible.size(); i++){
            Cell[][] boardCopy = board.clone();
            boardCopy[row][col].fixed = true;
            boardCopy[row][col].value = board[row][col].possible.get(i);
            updateBasic(boardCopy,row,col);
            temp(boardCopy);

            Cell[][] solBoard = solve(boardCopy);

            if(solBoard != null){
                return solBoard;
            }
        }
        return null;
    }

    public static void temp(Cell[][] boardCopy){
        boolean flag = true;
        while(flag){
            flag = false;
            for(int k = 0; k < 9; k++){
                for(int l = 0; l < 9; l++){
                    if(!boardCopy[k][l].fixed && boardCopy[k][l].possible.size() == 1){
                        flag = true;
                        boardCopy[k][l].value = boardCopy[k][l].possible.get(0);
                        boardCopy[k][l].fixed = true;
                        updateBasic(boardCopy, k, l);
                    }
                }
            }
        }
    }

    /*
     *  This method updates possible values of cells in same
     *  row, column and sub-grid as fixed values
     *  @params Cell[][] board updated board state
     *  @params i row index of current fixed value
     *  @params j column index of current fixed value
    */
    public static void updateBasic(Cell[][] board, int i, int j) {

        for(int k = 0;k<9;k++){
            if(board[i][k].possible.contains(board[i][j].value) && !board[i][k].fixed)
            {
                board[i][k].possible.remove(board[i][j].value);
            }
            if(board[k][j].possible.contains(board[i][j].value) && !board[k][j].fixed)
            {
                board[k][j].possible.remove(board[i][j].value);
            }
        }
        //Create temp arrays to hold indices of cells to be updated
        int rowTemp[] = new int[2];
        int colTemp[] = new int[2];

        if(i % 3 == 0){
            rowTemp[0] = i + 1;
            rowTemp[1] = i + 2;
        }
        else if(i % 3 == 1){
            rowTemp[0] = i - 1;
            rowTemp[1] = i + 1;
        }
        else{
            rowTemp[0] = i - 2;
            rowTemp[1] = i - 1;
        }
        if(j % 3 == 0){
            colTemp[0] = j + 1;
            colTemp[1] = j + 2;

        }
        else if(j % 3 == 1){
            colTemp[0] = j - 1;
            colTemp[1] = j + 1;
        }
        else{
            colTemp[0] = j - 2;
            colTemp[1] = j - 1;
        }

        for(int l = 0; l < 2; l++){
            for(int m = 0; m < 2; m++){
                if(board[rowTemp[l]][colTemp[m]].possible.contains(board[i][j].value)){
                    board[rowTemp[l]][colTemp[m]].possible.remove(board[i][j].value);
                }
            }
        }
    }
    /*
     *  This method initializes the cell array according the
     *  initial given fixed values
     *  @params Cell[][] board the initial board state
    */
    public static void updateInitial(Cell[][] board) {

        //Initialize all not fixed possible values to 1-9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board[i][j].fixed == false){
                    for(int k=1;k <=9;k++){
                        board[i][j].possible.add(k);
                    }
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].fixed) {
                    updateBasic(board, i, j);
                    temp(board);
                }
            }
        }
    }
    /*
     *  Class to define the attributes and
     *  methods of the board cells
    */
    public static class Cell {

        Integer value;
        boolean fixed;
        ArrayList<Integer> possible;

        public Cell(int theValue, boolean theFixed, ArrayList<Integer> thePossible) {
            value = theValue;
            fixed = theFixed;
            possible = thePossible;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}