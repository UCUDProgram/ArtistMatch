package ArtistMatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class ArtistGame implements Screen, InputProcessor, ApplicationListener {
	private ArtistMatch game;
	private boolean isEndTime,displayTimeCount,moveDistanceCount,guessCounter;
	private String ques,cAnswer,xmlFile;
	private Texture background;
	private long startTime, endTime;
	private int angle,ballCount,incorrectGuesses,correctGuess;
	private BitmapFont font,font1, font2;
	private Stage stage;
	private SpriteBatch batch;
	private String [] answers;
	private List<String> correctA, selectedA;
	private float ballX,screenWidth,screenHeight,moveDistance;
	private boolean goLeft, goRight,ballShoot, activeBall,turnUp, turnDown,win, tutorial;
	private Ball validBall;
	private Player Player;
	private List<Box> possAnswers;
	private ShapeRenderer shape;

	
	class Box{
		private String aString;
		private char displaySelection;
		private int drawXLoc,drawYLoc;
		private Texture image;
		private float yStart,yHeight, xWidth, xStart;
		private boolean drawable;
		private float boxScale;
		
		
		/*
		 * Constructor for the Box class where the X & Y Coordinate changes
		 */
		public Box(String aStr, int xSt,int yStLoc, char dis,int dXloc, int dYloc){
			aString = aStr;
			xStart = xSt;
			yStart = setYLocation(yStLoc);
			displaySelection = dis;
			drawXLoc = dXloc;
			drawYLoc = dYloc;
			boxScale = setBoxScale();
			image = new Texture("YouDee.png");
			yHeight = image.getHeight()* boxScale;
			xWidth = image.getWidth() * boxScale;
			drawable = true;
			
		}
		
//		public Texture setBoxImage(){
//			
//		}
		
		/*
		 * Sets the Y Location of the Box
		 */
		public float setYLocation(int yStartLocation){
			if (yStartLocation == 0)
				return screenHeight - 100;
			else{
				return (screenHeight -100) - (yStartLocation * 100);
			}
		}
		
		/*
		 * Sets the X Location of the Box
		 */
		public int setAnswerXDraw(int vert){
			if (vert == 0)
				return 25;
			else
				return (int) ( (screenWidth / 2) + 25);
		}
		
		/*
		 * Sets the Y Location of the Box
		 */
		public int setAnswerYDraw(int hor){
			if (hor == 0)
				return 25;
			else
				return 50;
		}
		
		/*
		 * Draws the Box Properties on the screen
		 * 		The Image of the Selection on the Screen
		 * 		The Letter associated with the Box
		 * 		The Option underneath the Question, representing the options left to choose from
		 */
		public void drawBox(){
			batch.draw(image, xStart, yStart, image.getWidth() * boxScale,image.getHeight() * boxScale);			
			font1.draw(batch,Character.toString(displaySelection), xStart, yStart);
			font.draw(batch,(displaySelection + ": " + aString), setAnswerXDraw(drawXLoc),setAnswerYDraw(drawYLoc));
		}
		
		/*
		 * Sets the Box's Scale, which is the size that the box will be drawn on the screen
		 */
		public float setBoxScale(){
			if (game.getDifficulty() ==0)
				return .25f;
			else if (game.getDifficulty() ==0)
				return .2f;
			else if (game.getDifficulty() ==0)
				return .18f;
			else
				return .12f;
		}
		
	}
	
	class Ball{
		private float xLoc, yLoc;
		private int ballAngle;
		private Texture ballImage;
		private boolean activeBall;
		private double ballSpeed;
		private float ballScale;
		/*
		 * Constructor for the ball class
		 * 
		 */
		public Ball(float xLocation, float yLocation, int bAngle,boolean active){
			xLoc = xLocation;
			yLoc = yLocation;
			ballAngle = bAngle;
			ballImage = new Texture("SoccerBall.png");
			activeBall = active;
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
			if (ballX < 0 ){
				activeBall = false;
			} else 
				validBall.xLoc += (Math.cos(convertToRadians(validBall.ballAngle))* ballSpeed);
		}
		
		/*
		 * Updates the Ball's Y Position
		 */
		public void updateBallYPos(){
			validBall.yLoc += (Math.sin(convertToRadians(validBall.ballAngle))* ballSpeed);
		}
		
		/*
		 * Update Ball Count total
		 */
		public void updateBallCount(){
			if (validBall.xLoc < 0 || validBall.xLoc > screenWidth)
				ballCount = 0;
			else if (validBall.yLoc < 0 || validBall.yLoc > screenHeight)
				ballCount = 0;
			else
				ballCount = 1;
		}
		
		/*
		 * Initialize the Ball
		 */
		public void setBallInitial(){
		validBall = new Ball(Player.xPlayerLoc,100, angle,true);
//		activeBall = true;
//		ballCount = 1;
	}
		
		/*
		 * Updates the Ball
		 */
		public void updateBall(){
			updateBallXPos();
			updateBallYPos();
			updateBallCount();
		}
		
		/*
		 * Draws the Ball on the Screen
		 */
		public void drawBall(){
			batch.draw(validBall.ballImage, validBall.xLoc,validBall.yLoc, validBall.ballImage.getWidth() * ballScale,validBall.ballImage.getHeight() * ballScale );
		}
		
		/*
		 * Sets the Speed of the Ball
		 */
		public double setBallSpeed(){
			if (game.getDifficulty() ==0)
				return 2.5;
			else if (game.getDifficulty() ==0)
				return 3;
			else if (game.getDifficulty() ==0)
				return 3.5;
			else
				return 4.75;
		}
		
		/*
		 * Determines the Ball's Size, which is passed to the set Ball Scale function
		 */
		public float ballS(){
			if (game.getDifficulty() == 0)
				return .30f;
			else if (game.getDifficulty() == 1)
				return .25f;
			else if (game.getDifficulty() == 2)
				return .20f;
			else 
				return .10f;
		}
	}
	
	class Player{
		private float xPlayerLoc, yPlayerLoc;
		private Texture playerImage;
		private float playerScale;
		
		/*
		 * Constructor for the player class that passes nothing in
		 */
		public Player (){
			xPlayerLoc = setPlayerXStart();
			yPlayerLoc = 100;
			playerImage = new Texture("YouDee.png");
			playerScale = .25f;
		}
		
		/*
		 * Updates the player's X Location, based on the player moving left or right
		 */
		public void updateXPos(){
			if(goRight&&Player.xPlayerLoc<=(screenWidth - (Player.playerImage.getWidth()* playerScale) ) ){
				Player.xPlayerLoc+=10;
			}
			if(goLeft&&Player.xPlayerLoc >=( (Player.playerImage.getWidth() / 2) * playerScale) ){
				Player.xPlayerLoc-=10;
			}
		}
		
		/*
		 * Draws the player on the Screen
		 */
		public void drawPlayer(){
			batch.draw(Player.playerImage, Player.xPlayerLoc, Player.yPlayerLoc, Player.playerImage.getWidth() * playerScale,Player.playerImage.getHeight() * playerScale);		
		}
		
		/*
		 * Sets the player's Scale, which is the height the player will appear
		 */
//		public float setPlayerScale(){
//			if (game.getDifficulty() ==0)
//				return 2.5;
//			else if (game.getDifficulty() ==0)
//				return 3.;
//			else if (game.getDifficulty() ==0)
//				return 3.5;
//			else
//				return 4.75;
//		}
		
		/*
		 * Sets the Player's X Location, which is the starting location for the player
		 */
		public float setPlayerXStart(){
			if (game.getDifficulty() ==0)
				return screenWidth /2;
			else if (game.getDifficulty() ==1){
				int random = (int)( (Math.random() * 5) + 1);
				return ( (screenWidth / 6) * random );
			}
			else if (game.getDifficulty() ==2){
				int random = (int)( (Math.random() * 7) + 1);
				return ( (screenWidth / 8) * random );
		}
			else{
				int random = (int)( (Math.random() * 9) + 1);
				return ( (screenWidth / 10) * random );
			}
		}
	}
	
	public ArtistGame(ArtistMatch game){
		this.game = game;
	}
	
	public void create(){
		batch = new SpriteBatch();
		stage = new Stage();
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
		Gdx.input.setInputProcessor(this);		
		background = new Texture("ShooterBackground.png");
		Player = new Player();
		validBall = new Ball(0,0,angle,false);
		initializeVariables();
		initializeBoolean();
		initializeText();
		xmlFile = setXMLFile();
//		initializeQuestion();
//		initializeSelectionArray();
//		initializeAnswer();	
		selectedA = new ArrayList<String>();
		correctA = new ArrayList<String>();
		
		
		ques = "Who is the best Musician Ever";
		answers = new String[4];
		answers[0]= "Lionel Ritchie";
		answers[1] = "Michael Jackson";
		answers[2]= "The Temptations";
		answers[3] = "The Jackson 5";
//		cAnswer = answers[1];
		initializeArray();
		
		
		possAnswers = new ArrayList<Box>();
		setBoxes();
		
//	System.out.println("The height dimensions of the box is " + (possAnswers.get(0).image.getHeight() * possAnswers.get(0).boxScale) );
	
		shape = new ShapeRenderer();
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
	}
	
	public void initializeArray(){
		selectedA.clear();
		correctA.add(answers[1]);
		correctA.add(answers[3]);
	}
	
	/*
	 * Initialize font settings
	 */
	public void initializeText(){
		font = new BitmapFont();
		font1  = new BitmapFont();
		font1.setColor(Color.BLUE);
		font2 = new BitmapFont();
		font2.setColor(Color.BLACK);
	}
	
	/*
	 * Initialize Variables
	 */
	public void initializeVariables(){
		angle = 90;
		ballCount = 0;
		moveDistance = 0;
		incorrectGuesses = 0;
		correctGuess = 0;
	}
	
	/*
	 * Initialize the boolean variables
	 */
	public void initializeBoolean(){
		isEndTime = false;
		displayTimeCount = true;
		moveDistanceCount = true;
		guessCounter = true;
		
		
		tutorial = true;
		win = false;
		goLeft = false;
		goRight = false;
		ballShoot = false;
		activeBall = false;
	}
	
	/*
	 * Sets the XML File that will be used to get the question, options and correct answers
	 */
	public String setXMLFile(){
		if(game.getDifficulty() == 0)
			return "EasyQuestions.xml";
		if(game.getDifficulty() == 1)
			return "MediumQuestions.xml";
		if(game.getDifficulty() == 2)
			return "HardQuestions.xml";
		else
			return "ExpertQuestions.xml";
	}
	
	/*
	 * Initialize the Question
	 */
	public void initializeQuestion(){
		try{Element root = new XmlReader().parse(Gdx.files.internal(xmlFile));
		Element artist = root.getChildByName(game.getArtist());
		Element quesNum = artist.getChildByName(Integer.toString(game.getQuestion()));
		Element question = quesNum.getChildByName("Question");
		ques = question.getText();
		
		}
		catch(IOException e){
		}
	}
	
	/*
	 * Initialize the possible Answers Array
	 */
	public void initializeSelectionArray(){
		try{Element root = new XmlReader().parse(Gdx.files.internal(xmlFile));
		Element artist = root.getChildByName(game.getArtist());
		Element quesNum = artist.getChildByName(Integer.toString(game.getQuestion()));
		Array<Element> answerE = quesNum.getChildrenByName("Option");
		int count = answerE.size;
		answers = new String[count];
		for (int i = 0; i <answerE.size; i++){
			answers[i]=answerE.get(i).getText();
		}
		}
		catch(IOException e){
		}
	}
	
	/*
	 * Initialize the Correct Answers
	 */
	public void initializeAnswer(){
		try{Element root = new XmlReader().parse(Gdx.files.internal(xmlFile));
		Element artist = root.getChildByName(game.getArtist());
		Element quesNum = artist.getChildByName(Integer.toString(game.getQuestion()));
		Array<Element> answerCA = quesNum.getChildrenByName("Correct");
		for (int i = 0; i <answerCA.size; i++){
			correctA.add(answerCA.get(i).getText());
		}
		}
		catch(IOException e){
		}
	}
	
	/*
	 * Function to initialize the Boxes
	 */
	public void setBoxes(){
		char[] displayAnswer = {'A','B','C','D','E','F','G','H','I','J','K','L'};
		int count = 5;  //Think about how to set the positioning of the boxes
		int numcount = 1;
		int vertdet=0;
		int hordet=0;
		int boxPlace = 0;
		for(int i=0; i< answers.length; i++){
//			Sets the X coordinate for drawing the option, below the question
			if( (i<4) && (i>1) ){
				vertdet= 1;
			}else 
				vertdet = 0;
//			Sets the Y coordinate for drawing the option, below the queston
			if ( (i==0)|| (i==2))
				hordet =1;
			else
				hordet = 0;
//			Sets the Y coordinate placement of the box, based on the difficulty and the number of options available
			if(i <6)
				boxPlace = 0;
			else if( (i == 6 ) || (i == 7) )
				boxPlace = 1;
			else if ( (i == 8) || (i == 9 ) )
				boxPlace = 1;
			possAnswers.add(new Box(answers[i], (int)((screenWidth / count) * numcount),boxPlace,displayAnswer[i],vertdet,hordet ));
			numcount++;
		}
	}
	
	
	
	
	
	
	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		batch.begin();
		if(tutorial){
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			font.setColor(Color.WHITE);
			font.setScale(1,1);
			font.draw(batch, "Press, 'ESC' to return to menu", 0f, screenHeight);		
		} else if((!win) && !tutorial){
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(background, 0, 0, screenWidth, screenHeight);
		
		Player.updateXPos();
		Player.drawPlayer();
		updateAngle();
		drawQuestion();
		drawBoxes();	
		drawAngleInfo();
		
		if(ballShoot){
			validBall.setBallInitial();
			activeBall = true;
			ballCount = 1;
		}
//		if(validBall.activeBall){
		if(activeBall){
			validBall.updateBall();
			ballBoxCollision();
			validBall.drawBall();
//			win = boxCollision();	
			win = areAllAnswersCollect();
		}

		
//		drawAngleIndicator();

				
			
		

		} else {
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			font.setColor(Color.WHITE);
			font.draw(batch,"You got the correct answer", (screenWidth / 2), (screenHeight / 2) );
			if(!isEndTime){
				endTime = System.currentTimeMillis();
				isEndTime = true;
			}
			if(displayTimeCount){
				System.out.println("The time to complete the level is " + duration(startTime,endTime) + " seconds." );
				displayTimeCount = false;
			}
			if(moveDistanceCount){
				System.out.println("The distance traveled is " + moveDistance);
				moveDistanceCount = false;
			}
			if(guessCounter){
			System.out.println("The number of incorrect guesses is " + incorrectGuesses);
			guessCounter = false;
			}
/*
 * Next lines are for end of level and passing certain parameters to the score class
 */
			int duration = duration(startTime,endTime);
			
//			Score score = new Score(game, right, incorrectGuesses,duration, moveDistance);
			
			
		}
		batch.end();
		
	}

	
	
	/*
	 * Determines the time to complete the game in seconds
	 */
	public int duration (long start, long end){
		long duration = end - start;
		int time = (int)(duration /1000);
		return time;
	}
	
	// THIS GROUP OF FUNCTIONS ADDRESS PHYSICAL ASPECTS OF THE GAME THAT MAY OR MAY NOT BE VISIBLE TO THE PLAYER
	
	/*
	 * Draws the Question on the Screen
	 */
	public void drawQuestion(){
		font.draw(batch, ques, screenWidth / 5 , 75 );
	}
	
	/*
	 * Draws the Boxes, which the player must choose from, on the screen
	 */
	public void drawBoxes(){
		for(Box aAnswer:possAnswers){
			if(aAnswer.drawable)
				aAnswer.drawBox();
		}
	}
	
	/*
	 * Draws the String, representing the current angle at which the ball will fire at, on the screen
	 */
	public void drawAngleInfo(){
		font2.draw(batch, ("The angle of Projection is " + angle),(float)(screenWidth * .45), screenHeight - 25);
	}
	
	
	/*
	 * Draws the angle indicator on the screen
	 */
	public void drawAngleIndicator(){
		float bottomLineHyp = 60;
		float topLineHyp = 100;
		
		float bottomXCoord = (float) ((Math.cos(Math.toRadians(angle) ) ) * bottomLineHyp);
		float bottomYCoord = (float) ((Math.sin(Math.toRadians(angle) ) ) * bottomLineHyp);
		
		float bottomX = Player.xPlayerLoc + ((Player.playerImage.getWidth() * Player.playerScale) / 2 ) + bottomXCoord;
		float bottomY = Player.yPlayerLoc + ((Player.playerImage.getHeight() * Player.playerScale) / 2 )  + bottomYCoord ;
		
		float topXCoord = (float) ((Math.cos(Math.toRadians(angle) ) ) * topLineHyp);
		float topYCoord = (float) ((Math.sin(Math.toRadians(angle) ) ) * topLineHyp);
		
		float topX = Player.xPlayerLoc + ((Player.playerImage.getWidth() * Player.playerScale) / 2 ) + topXCoord;
		float topY = Player.yPlayerLoc + ((Player.playerImage.getHeight() * Player.playerScale) / 2 ) + topYCoord;
		
		ShapeRenderer shape = new ShapeRenderer();
		shape.setColor(Color.BLACK);
		shape.begin(ShapeType.Line);
		shape.line(bottomX, bottomY, topX, topY);
		shape.end();
	}
	
	
	/*
	 * Updates the angle's value
	 */
	public void updateAngle(){
		if (turnUp && (angle < 180) ){
			angle+=1;
		}
		if (turnDown && (angle > 0)){
			angle-=1;
		}
	}

	public void setAngle(int xCoord, int yCoord){
		int length = (int) Math.abs( (Player.xPlayerLoc + (Player.playerImage.getWidth() / 2) + xCoord ) );
		float height = yCoord - Player.yPlayerLoc;
		double anglerad = Math.tanh(height/length);
		angle = (int)Math.toDegrees(anglerad);
		
	}
	
	
	// NEXT GROUP OF FUNCTIONS ADDRESS COLLISION DETECTION AND DETERMINE IF THE LEVEL HAS BEEN "WON"

	/*
	 * Determines if a collision between the Ball and a selection has occurred
	 */
	public boolean ballBoxCollision(){
		boolean collision = false;
		for(Box possibleSelection: possAnswers ){
			if(possibleSelection.drawable){
			if (ballBoxXCollision(possibleSelection) && ballBoxYCollision(possibleSelection)){
				if (isCorrect(possibleSelection.aString)){
					possibleSelection.drawable = false;
					
					selectedA.add(possibleSelection.aString);
//					collectedAnswers.add(possibleSelection.aString);
					
					activeBall = false;
					ballCount = 0;
					correctGuess += 1;
//					win = true;
			}else {
					possibleSelection.drawable = false;			
					activeBall = false;
					ballCount = 0;
					incorrectGuesses += 1;
				}
			}
		}
		}
		return collision;
	}
	
	/*
	 * Determines if a Ball and a Selection collision has occurred, based only on the X coordinate
	 */
	public boolean ballBoxXCollision(Box aBox){
		if( (aBox.xStart <= validBall.xLoc) && (validBall.xLoc <= (aBox.xStart + aBox.xWidth)))
			return true;
		else
			return false;
	}
	
	/*
	 * Determines if a Ball and a Selection collision has occurred, based only on the Y coordinate
	 */
	public boolean ballBoxYCollision(Box aBox){
		if( (aBox.yStart <= validBall.yLoc) && (validBall.yLoc <= (aBox.yStart + aBox.yHeight)))
			return true;
		else
			return false;
	}
	
	/*
	 * Determines if the Box's String is the/part of the answer
	 */
	public boolean isCorrect(String theguess){
		return (correctA.contains(theguess));
	}
	
	/*
	 * Boolean to determine if there is a box collision with the right answer
	 */
	public boolean boxCollision(){
		for(Box aBox:possAnswers){
			if (ballBoxXCollision(aBox) && ballBoxYCollision(aBox) ){
				if(aBox.aString.equals(cAnswer))
					return true;
			}
		}
		return false;
	}

	/*
	 * Determines if all the correct answers have been collected
	 * Returns true if all the correct answers have been collected
	 * Returns false if all the correct answers have not been collected
	 */
	public boolean areAllAnswersCollect(){
		if(selectedA.isEmpty())
			return false;
		else{
		
		for(String Aanswer:selectedA){
			if(!(correctA.contains(Aanswer)))
				return false;
		}
		return (selectedA.size() == correctA.size());
		}
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Input.Keys.RIGHT){
			goRight = true;
			moveDistance += 10;
		}
		if(keycode == Input.Keys.LEFT){
			goLeft = true;
			moveDistance += 10;
		}
		if(keycode == Input.Keys.UP){
			turnUp = true;
		}
		if(keycode == Input.Keys.DOWN){
			turnDown = true;
		}
		if( (keycode == Input.Keys.SPACE) && (ballCount == 0) ){
			ballShoot = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Input.Keys.RIGHT){
			goRight = false;
		}
		if(keycode == Input.Keys.LEFT){
			goLeft = false;
		}
		if(keycode == Input.Keys.UP){
			turnUp = false;
		}
		if(keycode == Input.Keys.DOWN){
			turnDown = false;
		}
		if(keycode == Input.Keys.SPACE){
			ballShoot = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if(tutorial){
			tutorial = false;
			startTime = System.currentTimeMillis();
		}
		
		if(!win){	
			int bufferXWindow = 50;
			int bufferYWindow = 50;
			if( (Player.xPlayerLoc + bufferXWindow) < screenX)
				turnDown = true;
			if(screenX < (Player.xPlayerLoc - bufferXWindow) )
				turnUp = true;
			if( ( (Player.xPlayerLoc + ((Player.playerImage.getWidth() * Player.playerScale) / 2) - bufferXWindow ) < screenX) 
					&& ( screenX <= (Player.xPlayerLoc + ( (Player.playerImage.getWidth() * Player.playerScale) / 2) + bufferXWindow ) ) 
					&& ( screenY <= ( (Player.playerImage.getHeight() * Player.playerScale) + bufferYWindow) ) ) {
				if (ballCount == 0){
				ballShoot = true;
				ballCount++;
				} else
					ballShoot = false;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		turnDown = false;
		turnUp = false;
		ballShoot = false;
			
		if(!win){	
				int bufferWindow = 50;
				if( (Player.xPlayerLoc + bufferWindow) < screenX)
					turnDown = false;
				if(screenX < (Player.xPlayerLoc - bufferWindow) )
					turnUp = false;
				if( ( (Player.xPlayerLoc + ((Player.playerImage.getWidth() * Player.playerScale) / 2) - bufferWindow ) < screenX) 
						&& ( screenX <= (Player.xPlayerLoc + ( (Player.playerImage.getWidth() * Player.playerScale) / 2) + bufferWindow ) 
						&& (ballCount == 0) ) ) 
					ballShoot = false;
			}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
}
