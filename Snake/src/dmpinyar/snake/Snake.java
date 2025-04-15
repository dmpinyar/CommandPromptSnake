/**
 * 
 */
package dmpinyar.snake;

import java.util.ArrayList;

/**
 * Object for the snake object
 */
public class Snake {
	//0 is up
	//1 is down
	//2 is right
	//3 is left
	
	private ArrayList<int[]> chunks;
	private int length;
	
	
	/**
	 * Constructor method for the snake 
	 * Snake knows its own position as well as the position of all body parts
	 * Made before I knew about custom linked lists
	 * 
	 * Should probably be remade for efficiency
	 */
	public Snake(int width, int height, int length) {
		this.length = length;
		this.chunks = new ArrayList<int[]>(0);
		int[] snakeHead = new int[2];
		snakeHead[0] = width / 2;
		snakeHead[1] = height / 2;
		
		chunks.add(snakeHead);
		
		for (int i = 1; i < length; i++) {
			int[] snakeChunk = new int[2];
			snakeChunk[0] = chunks.get(chunks.size() - 1)[0];
			snakeChunk[1] = chunks.get(chunks.size() - 1)[1];
			chunks.add(snakeChunk);
		}
	}
	
	/**
	 * Increments size of the snake
	 * 
	 * Could be made a bit more efficient in terms of passing parameters
	 */
	public void appleEat() {
		length++;
		int[] snakeChunk = new int[2];
		snakeChunk[0] = chunks.get(chunks.size() - 1)[0];
		snakeChunk[1] = chunks.get(chunks.size() - 1)[1];
		chunks.add(snakeChunk);
	}
	
	/**
	 * Moves the snake and all chunks
	 * 
	 * Should be reworked to only update tail
	 * Probably through a custom linked list
	 */
	public boolean SnakeMove(int dir, int width, int height) {
		int tempX = chunks.get(0)[0];
		int tempY = chunks.get(0)[1];
		
		//y-values are top to bottom
		if (dir == 0) {
			chunks.get(0)[1] = tempY - 1;
		} else if (dir == 1) {
			chunks.get(0)[1] = tempY + 1;
		} else if (dir == 2) {
			chunks.get(0)[0] = tempX + 1;
		} else {
			chunks.get(0)[0] = tempX - 1;
		}
		
		//check for outside of the grid
		if (chunks.get(0)[1] < 0 || chunks.get(0)[1] > height - 1 || chunks.get(0)[0] < 0 || chunks.get(0)[0] > width - 1) {
			return false;
		}

		for (int i = 1; i < chunks.size(); i++) {
			//check for bad collisions
			if (chunks.get(0)[0] == chunks.get(i)[0] && chunks.get(0)[1] == chunks.get(i)[1]) {
				return false;
			}
			
			//make sure the tile is valid to move and move
			if (i == chunks.size() - 1 || !chunks.get(i).equals(chunks.get(i + 1))) {
				int temperX = tempX;
				int temperY = tempY;
				
				tempX = chunks.get(i)[0];
				tempY = chunks.get(i)[1];
				
				chunks.get(i)[0] = temperX;
				chunks.get(i)[1] = temperY;
			}
		}
		
		return true;
	}
	
	public int getLength() {
		return this.length;
	}

	/**
	 * @return the chunks
	 */
	public ArrayList<int[]> getChunks() {
		return chunks;
	}
	
	public int getChunkPosX(int index) {
		return chunks.get(index)[0];
	}
	
	public int getChunkPosY(int index) {
		return chunks.get(index)[1];
	}
	
}
