package ArtistMatch;

//import ArtistMatch.ArtistGame.Ball;

import com.badlogic.gdx.graphics.Texture;

class Ball{
	private float xLoc, yLoc;
	private int ballAngle;
	private Texture ballImage;
	private boolean activeBall;
	private double ballSpeed;
	private float ballScale;
	private int gameDiff;
	/*
	 * Constructor for the ball class
	 * 
	 */
	public Ball(float xLocation, float yLocation, int bAngle,boolean active, int diff){
		xLoc = xLocation;
		yLoc = yLocation;
		ballAngle = bAngle;
		ballImage = new Texture("SoccerBall.png");
		activeBall = active;
		gameDiff = diff;
		ballScale = ballS();
		ballSpeed = setBallSpeed();
	}
	
	/*
	 * Constructor for the ball class
	 * 
	 */
	public Ball(float xLocation, float yLocation, int bAngle,boolean active,Texture ballIm, int diff){
		xLoc = xLocation;
		yLoc = yLocation;
		ballAngle = bAngle;
		ballImage = ballIm;
		activeBall = active;
		gameDiff = diff;
		ballScale = ballS();
		ballSpeed = setBallSpeed();
	}
	
	/*
	 * Converts the angle to pi radians
	 */
	public double convertToRadians(int degree){
		return (double) (degree / 57.3 );
	}
	
	/*
	 * Updates the Ball's X Position
	 */
	public void updateBallXPos(){
		xLoc += (Math.cos(convertToRadians(ballAngle))* ballSpeed);
	}
	
	/*
	 * Updates the Ball's Y Position
	 */
	public void updateBallYPos(){
		yLoc += (Math.sin(convertToRadians(ballAngle))* ballSpeed);
	}
	
	public float getxLoc() {
		return xLoc;
	}

	public void setxLoc(float xLoc) {
		this.xLoc = xLoc;
	}

	public float getyLoc() {
		return yLoc;
	}

	public void setyLoc(float yLoc) {
		this.yLoc = yLoc;
	}

	public Texture getBallImage() {
		return ballImage;
	}

	public float getBallScale() {
		return ballScale;
	}

	public boolean isActiveBall() {
		return activeBall;
	}

	public void setActiveBall(boolean activeBall) {
		this.activeBall = activeBall;
	}
	
	
	
	/*
	 * Sets the Speed of the Ball
	 */
	public double setBallSpeed(){
		if (gameDiff ==0)
			return 2.5;
		else if (gameDiff ==1)
			return 3;
		else if (gameDiff ==2)
			return 3.5;
		else
			return 4.75;
	}
	
	/*
	 * Determines the Ball's Size, which is passed to the set Ball Scale function
	 */
	public float ballS(){
		if (gameDiff == 0)
			return .30f;
		else if (gameDiff == 1)
			return .25f;
		else if (gameDiff == 2)
			return .20f;
		else 
			return .10f;
	}
}
