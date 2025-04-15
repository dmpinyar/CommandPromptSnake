/**
 * 
 */
package dmpinyar.snake_game;

import java.util.Scanner;

import dmpinyar.snake.Snake;
import dmpinyar.scan_thread.ScanThread;

/**
 * 
 */
public class SnakeGame extends Thread {

	public static final int GRID_WIDTH = 20;
	public static final int GRID_HEIGHT = 15;
	public static final int APPLES = 5;
	public static final int LENGTH = 3;
	public enum State{empty, snake, apple};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScanThread inputScan = new ScanThread(new Scanner(System.in));
		inputScan.start();
		
		
		int dir = 2;
		boolean gameOver = false;
		int ticks = 0;
		
		//slowest speed is roughly 2000000000
		//mid is 1000000000
		//fastest is 600000000
		final int MAX_TICKS = 600000000;
		
		//generate grid
		State[][] grid = new State[GRID_WIDTH][GRID_HEIGHT];
		for (int a = 0; a < grid.length; a++) {
			for (int b = 0; b < grid[0].length; b++) {
				grid[a][b] = State.empty;
			}
		}
		
		//generate snake
		Snake snake = new Snake(GRID_WIDTH, GRID_HEIGHT, LENGTH);
		
		//generate apples
		int apples = 0;
		
		while (apples < APPLES) {
			int totalSpaces = GRID_WIDTH * GRID_HEIGHT - snake.getLength() - apples;
			int spaceChosen = ((int)(totalSpaces * Math.random()) - 1) % totalSpaces;
			
			for (int a = 0; a < GRID_WIDTH; a++) {
				for (int b = 0; b < GRID_HEIGHT; b++) {
					if (grid[a][b] == State.empty) spaceChosen--;
					if (spaceChosen == 0) grid[a][b] = State.apple;
				}
			}
			apples++;
		}
		

		//game
		String lastInput = null;
		int tickCheck = 1;
		
		while (!gameOver) {
			ticks += tickCheck;
			String input = inputScan.getLine();
			
			if (input != lastInput) {
				if ("w".equalsIgnoreCase(input) && dir != 1) {
					dir = 0;
				} else if ("s".equalsIgnoreCase(input) && dir != 0) {
					dir = 1;
				} else if ("d".equalsIgnoreCase(input) && dir != 3) {
					dir = 2;
				} else if ("a".equalsIgnoreCase(input) && dir != 2) {
					dir = 3;
				} else if ("x".equalsIgnoreCase(input)) {
					tickCheck--;
					if (tickCheck < 0) tickCheck = 1;
				}
			}
			
			lastInput = input;
			
			if (ticks >= MAX_TICKS) {
				ticks -= MAX_TICKS;
				
				if (!updateGame(snake, grid, dir)) gameOver = true;
				else displayGame(grid);
			}
			
		}
		
		System.out.println("Score: " + snake.getLength());
	}
	
	static boolean updateGame(Snake snake, State[][] grid, int dir) {
		if (!snake.SnakeMove(dir, GRID_WIDTH, GRID_HEIGHT)) return false;
		
		boolean appleEat = false;
		
		if (grid[snake.getChunkPosX(0)][snake.getChunkPosY(0)] == State.apple) {
			snake.appleEat();
			grid[snake.getChunkPosX(0)][snake.getChunkPosY(0)] = State.empty;
			appleEat = true;
		} 
		
		//clear grid
		for (int a = 0; a < grid.length; a++) {
			for (int b = 0; b < grid[0].length; b++) {
				if (grid[a][b] != State.apple) grid[a][b] = State.empty;
			}
		}
		
		//build snake on grid
		for (int i = 0; i < snake.getLength(); i++) {
			grid[snake.getChunkPosX(i)][snake.getChunkPosY(i)] = State.snake;
		}
		
		//regenerate apple
		if (appleEat == true) {
			int totalSpaces = GRID_WIDTH * GRID_HEIGHT - snake.getLength() - (APPLES - 1);
			int spaceChosen = ((int)(totalSpaces * Math.random()) - 1) % totalSpaces;
			
			for (int a = 0; a < GRID_WIDTH; a++) {
				for (int b = 0; b < GRID_HEIGHT; b++) {
					if (grid[a][b] == State.empty) spaceChosen--;
					if (spaceChosen == 0) grid[a][b] = State.apple;
				}
			}
		}
		
		return true;
	}
	
	static void displayGame(State[][] grid) {
		for (int a = 0; a < grid.length; a++) System.out.print("-");
		System.out.print("\n");
		for (int a = 0; a < grid[0].length; a++) {
			for (int b = 0; b < grid.length; b++) {
				if (grid[b][a] == State.empty) System.out.print(".");
				else if (grid[b][a] == State.snake) System.out.print("#");
				else if (grid[b][a] == State.apple) System.out.print("@");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
}
