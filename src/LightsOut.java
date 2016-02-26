/*
 * Programmed by Joshua Hill
 * Created 2/23/16
 * Lights Out Game
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LightsOut extends JFrame {
	private Container contents;
	private JButton [][] grid;
	private ButtonHandler bh;
	//Constructor
	public LightsOut(){
		
		super("The Game of Lights Out!");
		contents = getContentPane();
		contents.setLayout(new GridLayout(3,3));
bh = new ButtonHandler();
		grid = new JButton [3][3];
		
		//Instantiates all the buttons
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				grid [i][j] = new JButton();
				grid [i][j].setBackground(getRandomState());
				grid[i][j].addActionListener(bh);
				contents.add(grid[i][j]);
			}
		}
		setSize(320,320);
		setVisible(true);
	}
	
	private Color getRandomState(){
		Random rand = new Random();
		if(rand.nextBoolean()){
			return Color.white;
		}
		else{
			return Color.black;
		}
	}
	
	private Color getOpposite(Color c){
		if(c.equals(Color.white)){
			return Color.black;
		}
		else{
			return Color.white;
		}
	}
	
	private int [] getLocation(JButton b){
		int [] location = new int [2];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(grid[i][j].equals(b)){
					location [0] = j;
					location [1] = i;
				}
			}
		}
		return location;
	}
	
	private void toggleTile(JButton b){
		b.setBackground(getOpposite(b.getBackground()));
	}
	
	//Goes through all the buttons, and decides whither or not to toggle them
	
	private void changeNeighbors(JButton tile){
		for(JButton [] row : grid){
			for(JButton tile2 : row){
				if(neighbors(tile,tile2)){
					toggleTile(tile2);
				}
			}
		}
	}
	
	public boolean neighbors(JButton b1,JButton b2){
		int [] loc1 = getLocation(b1);
		int [] loc2 = getLocation(b2);
		
		//if they are in the same column
		if (loc1[0] == loc2[0]){
			//and either the same row, or the row above or below
			if(loc1[1] == loc2[1] || loc1[1]+1 == loc2[1] || loc1[1] -1 == loc2[1]){
				return true;
			}
		}
		//if they are in the same row
		else if (loc1[1] == loc2[1]){
			//if the column they are in is to the left or right
			if(loc1[0]+1 == loc2[0] || loc1[0] -1 == loc2[0]){
				return true;
			}
		}
		return false;
	}
	
	//if all tiles are the same color: return true
	public boolean getWinning(){
		Color c = grid[0][0].getBackground();
		for(JButton [] row : grid){
			for(JButton b : row){
				if(!b.getBackground().equals(c)){
					return false;
				}
			}
		}
		return true;
	}
	
	private class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton)e.getSource();
			changeNeighbors(button);
			boolean won = getWinning();
			if(won){
				for(JButton [] row : grid){
					for(JButton b : row){
						b.setBackground(Color.green);
						b.setText("YOU WON!!!");
						b.removeActionListener(bh);
					}
				}
			}
		}
		
		
	}

}
