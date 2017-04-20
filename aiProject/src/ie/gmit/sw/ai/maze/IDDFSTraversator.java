package ie.gmit.sw.ai.maze;

import java.awt.Color;
import ie.gmit.sw.ai.maze.*;

public class IDDFSTraversator implements Traversator
{
	private Node[][] maze;
	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
	
	public void traverse(Node[][] maze, Node start) {
		this.maze = maze;
		int limit = 1;
		
		while(keepRunning){
			dfs(start, 0, limit);
			
			if (keepRunning){
				
				try { //Pause before next iteration
					Thread.sleep(500);
		      		limit++;       		
		      		unvisit();	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
			}
      	}
	}

	private void dfs(Node node, int depth, int limit)
	{
		
		if (!keepRunning || depth > limit) return;		
		node.setIsVisited(true);	
		visitCount++;
		System.out.println(node.getName() + " Path " + node.getRow() + " " + node.getCol() + " " + node.getElement());
		if (node.isGoalNode()){
	        time = System.currentTimeMillis() - time; //Stop the clock
	        System.out.println("Found goal node at " +  node.getRow() + " " + node.getCol() + " " + node.getElement() + " " +  node.getIsVisited());
	        //TraversatorStats.printStats(node, time, visitCount);
	        keepRunning = false;
			return;
		}
		
		try { //Simulate processing each expanded node
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Node[] children = node.children(maze);
		Node[] children = node.adjacentNodes(maze);
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].getIsVisited()){
				children[i].setParent(node);
				dfs(children[i], depth + 1, limit);
			}
		}
	} 
		
	private void unvisit()
	{
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				maze[i][j].setIsVisited(false);
				maze[i][j].setParent(null);
				//maze[i][j].setColor(Color.BLACK);
			}
		}
	}
	
}//End IDDFSTraversator


