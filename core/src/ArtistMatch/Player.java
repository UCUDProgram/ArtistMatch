package ArtistMatch;

import com.badlogic.gdx.graphics.Texture;

class Player{
	private float xPlayerLoc, yPlayerLoc, playerScale;
	private Texture playerImage;
	int diffSett;
	
	/*
	 * Constructor for the player class that passes nothing in
	 */
	public Player (){
//		xPlayerLoc = setPlayerXStart();
		yPlayerLoc = 100;
		playerImage = new Texture("YouDee.png");
		playerScale = .25f;
		diffSett = 0;
	}
	
	public Player (float xLoc, int diff){
		xPlayerLoc = xLoc;
		yPlayerLoc = 100;
		playerImage = new Texture("YouDee.png");
		diffSett = diff;
		playerScale = setPlayerScale();
	}
	
	/*
	 * Constructor for the player class that passes starting xLocation, image and Difficulty in
	 */
	public Player (float xLoc, Texture playImage, int diff){
		xPlayerLoc = xLoc;
		yPlayerLoc = 100;
		playerImage = playImage;
		diffSett = diff;
		playerScale = setPlayerScale();
	}
	
	

	public float getxPlayerLoc() {
		return xPlayerLoc;
	}

	public void setxPlayerLoc(float xPlayerLoc) {
		this.xPlayerLoc = xPlayerLoc;
	}

	public float getyPlayerLoc() {
		return yPlayerLoc;
	}

	public void setyPlayerLoc(float yPlayerLoc) {
		this.yPlayerLoc = yPlayerLoc;
	}
	
	public Texture getPlayerImage() {
		return playerImage;
	}

	public float getPlayerScale() {
		return playerScale;
	}
	
	/*
	 * Updates the player's X Location, based on the player moving left or right
	 boolean of true is moving to the right
	 boolean of false is movement to left
	 later on a second input will be added to determine the distance
	 	This distance will be based on the difficulty and how movement will impact
	 */
	public void updateXPos(boolean shift){
		if (shift)
			xPlayerLoc +=10;
		else
			xPlayerLoc -=10;
	}
	
	/*
	 * Sets the player's Scale, which is the height the player will appear
	 */
	public float setPlayerScale(){
		if (diffSett ==0)
			return .25f;
		else if (diffSett ==0)
			return .2f;
		else if (diffSett ==0)
			return .15f;
		else
			return .1f;
	}
}