package ie.gmit.sw.ai.maze;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import ie.gmit.sw.ai.characters.Player;
import ie.gmit.sw.ai.characters.Spider;

/*
 * 
 *  This class will be used for starting the game
 *  
 *  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  NOTES  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 *  - Next thing to do is get the spartan to move around the maze using an algorithm 
 *  - Pass in the maze and Spartan location into one of the algorithms
 *  - Looks likely this will be done in the placePlayer method or the updateView method
 *  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 * 
 */
public class GameRunner implements KeyListener
{
	
	// =============== Constants  ===============
	
	private static final int MAZE_DIMENSION = 100;
	private static final int IMAGE_COUNT = 14;
	
	// =============== Variables  ===============
	
	private Maze maze;
	private GameView view;
	
	private int currentRow = 5;
	private int currentCol = 5;
	
	private Player player;
	private Spider spider;
		
	 // Algorithm to use
    private Traversator t, spiderT;
		
	// Main method to start the game 
	public static void main(String[] args) throws Exception 
	{
		
		// Start game
		new GameRunner();
		
	}// End method main
	
	public GameRunner() throws Exception
	{
		
		// Initialize size of maze
		maze = new Maze(MAZE_DIMENSION);
		
		// Pass a copy of the initialized maze into the game view 
		view = new GameView(maze);
		
		Sprite[] sprites = getSprites();
    	view.setSprites(sprites);
    	
    	// Position player in the maze
	    // Initialize node to starting position
	    placePlayer();
    	
		//printFullMaze();
    	
    	// Sets size of the game view
    	setGameDimension();
    	
    	// Builds the window for the game view
    	buildWindow();
    	
    	// Player node 
    	player = new Player(currentRow, currentCol, '5', maze.getMaze());
    	//spider = new Spider(currentRow + 10, currentCol + 10, '6', maze.getMaze());
    	
    	//view.setPlayer(player);
    	
    	if(maze.getMaze() == null)
    	{
    		System.out.println("Maze is null");
    		
    	}
    	
		t = new BruteForceTraversator(true, player);
		//spiderT = new BruteForceTraversator(true, spider);
		
		//spiderT.traverse(maze.getMaze(), spider);
		t.traverse(maze.getMaze(), player);
		
    		
	}// End constructor GameRunner
	
	private Sprite[] getSprites() throws Exception
	{
		
		//Read in the images from the resources directory as sprites. Note that each
		//sprite will be referenced by its index in the array, e.g. a 3 implies a Bomb...
		//Ideally, the array should dynamically created from the images... 
		Sprite[] sprites = new Sprite[IMAGE_COUNT];
		sprites[0] = new Sprite("Hedge", "resources/hedge.png");
		sprites[1] = new Sprite("Sword", "resources/sword.png");
		sprites[2] = new Sprite("Help", "resources/help.png");
		sprites[3] = new Sprite("Bomb", "resources/bomb.png");
		sprites[4] = new Sprite("Hydrogen Bomb", "resources/h_bomb.png");
		sprites[5] = new Sprite("Spartan Warrior", "resources/spartan_1.png", "resources/spartan_2.png");
		sprites[6] = new Sprite("Black Spider", "resources/black_spider_1.png", "resources/black_spider_2.png");
		sprites[7] = new Sprite("Blue Spider", "resources/blue_spider_1.png", "resources/blue_spider_2.png");
		sprites[8] = new Sprite("Brown Spider", "resources/brown_spider_1.png", "resources/brown_spider_2.png");
		sprites[9] = new Sprite("Green Spider", "resources/green_spider_1.png", "resources/green_spider_2.png");
		sprites[10] = new Sprite("Grey Spider", "resources/grey_spider_1.png", "resources/grey_spider_2.png");
		sprites[11] = new Sprite("Orange Spider", "resources/orange_spider_1.png", "resources/orange_spider_2.png");
		sprites[12] = new Sprite("Red Spider", "resources/red_spider_1.png", "resources/red_spider_2.png");
		sprites[13] = new Sprite("Yellow Spider", "resources/yellow_spider_1.png", "resources/yellow_spider_2.png");
		return sprites;
	}
	
	private void placePlayer()
	{   
		// ======================  Have to set spartan in maze or he won't show up in game view ======================
		//A Spartan warrior is at index 5
    	maze.set(currentRow, currentCol, '5'); 
    	
    	view.setCurrentRow(currentRow);
    	view.setCurrentCol(currentCol);
    		
	}
	
	private void buildWindow()
	{
		JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(view);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
	}
	
	private void setGameDimension()
	{
		
		Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
		
	}
	
	public void updateView()
	{
	
		// Get player values and update them in view
		view.setCurrentRow(player.getRow());
		view.setCurrentRow(player.getCol());
	}
	
	private void printFullMaze()
	{
		
		// Loop through the whole array
		for (int row = 0; row < maze.getMaze().length; row++)
		{
			
			for (int col = 0; col < maze.getMaze()[row].length; col++)
			{
				
				// Print out the character at that node
				System.out.print(maze.getMaze()[row][col].getElement());
				
			}// End inner for
			
			System.out.println();
			
		}// End outer for
		
	}// End method printFullMaze

	// ==========  IGNORE  ==========
	public void keyPressed(KeyEvent e) 
	{
		
		/*if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow, currentCol + 1)) currentCol++;   		
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
        	if (isValidMove(currentRow, currentCol - 1)) currentCol--;	
        }else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
        	if (isValidMove(currentRow - 1, currentCol)) currentRow--;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow + 1, currentCol)) currentRow++;        	  	
        }else if (e.getKeyCode() == KeyEvent.VK_Z){
        	view.toggleZoom();
        }else{
        	return;
        }
        
        updateView(); */
		
	}

	private boolean isValidMove(int row, int col)
	{
		if (row <= maze.size() - 1 && col <= maze.size() - 1 && maze.get(row, col) == ' '){
			maze.set(currentRow, currentCol, '\u0020');
			maze.set(row, col, '5');
			return true;
		}else{
			return false; //Can't move
		}
	}
	
	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}
	
}// End class GameRunner
