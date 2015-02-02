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
	private String ques,cAnswer,guess,xmlFile;
	private Texture ball,background;
	private long startTime, endTime;
	private int angle,ballAngle,ballCount,guessCount,incorrectGuesses,correctGuess;
	private BitmapFont font,font1, font2;
	private Stage stage;
	private SpriteBatch batch;
	private String [] answers, correctAnswers;
	private List<String> collectedAnswers;
	private float xloc, personScale,ballScale,playerScale,ballX, ballY,screenWidth,screenHeight,moveDistance;
	private boolean goLeft, goRight,ballShoot, activeBall,turnUp, turnDown,win, tutorial;
	private Ball validBall;
	private Player YouDee;
	private List<Box> possAnswers;
	private ShapeRenderer shape;
	List<String> correctA, selectedA;
	
	class Box{
		private String aString;
		private char displaySelection;
		private int xStart,drawXLoc,drawYLoc;
		private Texture image;
		private float yStart,yHeight, xWidth;
		private boolean drawable;
		
		/*
		 * Constructor for the Box class
		 * 
		 */
		public Box(String aStr, int xSt, char dis,int dXloc, int dYloc){
			aString = aStr;
			xStart = xSt;
			displaySelection = dis;
			image = new Texture("YouDee.png");
			yStart = screenHeight -100 /* ( image.getHeight()* playerScale)*/;
			yHeight = image.getHeight()* playerScale;
			xWidth = image.getWidth() * playerScale;
			drawable = true;
			drawXLoc = dXloc;
			drawYLoc = dYloc;
		}
		
		/*
		 * Draws the Box on the screen
		 */
		public void drawBox(){
			batch.draw(image, xStart, yStart, image.getWidth() * playerScale,image.getHeight() * playerScale);			
			font1.draw(batch,Character.toString(displaySelection), xStart, yStart);
			font.draw(batch,(displaySelection + ": " + aString), setAnswerXDraw(drawXLoc),setAnswerYDraw(drawYLoc));
			
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
	}
	
	class Ball{
		private float xLoc, yLoc;
		private int ballAngle;
		private Texture ballImage;
		private boolean activeBall;
		private double ballSpeed = 2.5;
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
		validBall = new Ball(YouDee.xPlayerLoc,100, angle,true);
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
		public void setBallSpeed(double speed){
			ballSpeed = speed;
		}
	}
	
	class Player{
		private int xPlayerLoc, yPlayerLoc;
		private Texture playerImage;
		/*
		 * Constructor for the player class that passes in the x coordinate
		 */
		public Player (int xPlayLoc){
			xPlayerLoc = xPlayLoc;
			yPlayerLoc = 100;
			playerImage = new Texture("YouDee.png");
		}
		/*
		 * Constructor for the player class that passes in the x coordinate and the y coordinate
		 */
		public Player (int xPlayLoc, int yPlayLoc){
			xPlayerLoc = xPlayLoc;
			yPlayerLoc = yPlayLoc;
			playerImage = new Texture("YouDee.png");
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
		YouDee = new Player( (int)(screenWidth / 2) );
		initializeVariables();
		initializeBoolean();
		ques = "Who is the best Musician Ever";
		answers = new String[4];
		answers[0]= "Lionel Ritchie";
		answers[1] = "Michael Jackson";
		answers[2]= "The Temptations";
		answers[3] = "The Jackson 5";
		cAnswer = answers[1];
		
//		xmlFile = setXMLFile();
//		initializeQuestion();
//		initializeSelectionArray();
//		initializeAnswer();
		initializeText();
		possAnswers = new ArrayList<Box>();
		setBoxes();
		validBall = new Ball(0,0,90,false);
		shape = new ShapeRenderer();
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
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
		ballScale = .25f;
		personScale = .5f;
		xloc = screenWidth / 2;
		playerScale = .25f;
		angle = 90;
		guessCount = 0;
		guess = "";
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
		Element genre = root.getChildByName(game.getGenre());
		Element level = genre.getChildByName(Integer.toString(game.getLevelNumber()) );
		Element quesNum = level.getChildByName(Integer.toString(game.getQuestion()));
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
		Element genre = root.getChildByName(game.getGenre());
		Element level = genre.getChildByName(Integer.toString(game.getLevelNumber()));
		Element quesNum = level.getChildByName(Integer.toString(game.getQuestion()));
		Array<Element> answerE = quesNum.getChildrenByName("Selection");
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
		Element genre = root.getChildByName(game.getGenre());
		Element level = genre.getChildByName(Integer.toString(game.getLevelNumber()));
		Element quesNum = level.getChildByName(Integer.toString(game.getQuestion()));
		Array<Element> answerCA = quesNum.getChildrenByName("Correct");
		int count = answerCA.size;
		correctAnswers = new String[count];
		for (int i = 0; i <answerCA.size; i++){
			correctAnswers[i]=answerCA.get(i).getText();
		}
		}
		catch(IOException e){
		}
	}
	
	/*
	 * Initialize the Boxes that the player must choose
	 */
	public void setBoxes(){
		char[] displayAnswer;
		displayAnswer = new char[4];
		displayAnswer[0] = 'A';
		displayAnswer[1] = 'B';
		displayAnswer[2] = 'C';
		displayAnswer[3]= 'D';
		int count = 5;
		int numcount = 1;
		int vertdet=0;
		int hordet=0;
		for(int i=0; i< answers.length; i++){
			if( (i<4) && (i>1) ){
				vertdet= 1;
			}else 
				vertdet = 0;
			if ( (i==0)|| (i==2))
				hordet =1;
			else
				hordet = 0;
			possAnswers.add(new Box(answers[i], (int)((screenWidth / count) * numcount),displayAnswer[i],vertdet,hordet ));
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
		
		updateXPos();
		updateAngle();
		drawQuestion();
		drawBoxes();	
		drawAngleInfo();
		
		if(ballShoot){
			validBall.setBallInitial();
			activeBall = true;
			ballCount = 1;
		}
		if(activeBall){
			validBall.updateBall();
			ballBoxCollision();
//			win = boxCollision();
			validBall.drawBall();
			win = areAllAnswersCollect();
		}

		
//		drawAngleIndicator();

				
			drawPlayer();
		

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
	 * Draws the player on the Screen
	 */
	public void drawPlayer(){
		batch.draw(YouDee.playerImage, YouDee.xPlayerLoc, YouDee.yPlayerLoc, YouDee.playerImage.getWidth() * playerScale,YouDee.playerImage.getHeight() * playerScale);		
	}
	
	/*
	 * Draws the angle indicator on the screen
	 */
	public void drawAngleIndicator(){
		float bottomLineHyp = 60;
		float topLineHyp = 100;
		
		float bottomXCoord = (float) ((Math.cos(Math.toRadians(angle) ) ) * bottomLineHyp);
		float bottomYCoord = (float) ((Math.sin(Math.toRadians(angle) ) ) * bottomLineHyp);
		
		float bottomX = YouDee.xPlayerLoc + ((YouDee.playerImage.getWidth() * playerScale) / 2 ) + bottomXCoord;
		float bottomY = YouDee.yPlayerLoc + ((YouDee.playerImage.getHeight() * playerScale) / 2 )  + bottomYCoord ;
		
		float topXCoord = (float) ((Math.cos(Math.toRadians(angle) ) ) * topLineHyp);
		float topYCoord = (float) ((Math.sin(Math.toRadians(angle) ) ) * topLineHyp);
		
		float topX = YouDee.xPlayerLoc + ((YouDee.playerImage.getWidth() * playerScale) / 2 ) + topXCoord;
		float topY = YouDee.yPlayerLoc + ((YouDee.playerImage.getHeight() * playerScale) / 2 ) + topYCoord;
		
		ShapeRenderer shape = new ShapeRenderer();
		shape.setColor(Color.BLACK);
		shape.begin(ShapeType.Line);
		shape.line(bottomX, bottomY, topX, topY);
		shape.end();
	}
	
	/*
	 * Updates the Player's x Position
	 */
	public void updateXPos(){
		if(goRight&&YouDee.xPlayerLoc<=(screenWidth - (YouDee.playerImage.getWidth()* playerScale) ) ){
			YouDee.xPlayerLoc+=10;
		}
		if(goLeft&&YouDee.xPlayerLoc >=( (YouDee.playerImage.getWidth() / 2) * playerScale) ){
			YouDee.xPlayerLoc-=10;
		}
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

	/*
	 * Determines if a collision between the Ball and a selection has occurred
	 * 
	 */
	public boolean ballBoxCollision(){
		boolean collision = false;
		for(Box possibleSelection: possAnswers ){
			if(possibleSelection.drawable){
			if (ballBoxXCollision(possibleSelection) && ballBoxYCollision(possibleSelection)){
				if (isCorrect(possibleSelection.aString)){
					possibleSelection.drawable = false;
					selectedA.add(possibleSelection.aString);
					activeBall = false;
					ballCount = 0;
					correctGuess++;
					win = true;
			}else {
					possibleSelection.drawable = false;
					guessCount++;				
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
		boolean gameOver = false;
		for(String Aanswer: selectedA ){
			if(!(correctA.contains(Aanswer)))
				return false;
		}
		
		if(collectedAnswers.size() == answers.length)
			gameOver = true;
		else
			gameOver = false;
		
		return gameOver;
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
			int bufferWindow = 50;
			if( (YouDee.xPlayerLoc + bufferWindow) < screenX)
				turnDown = true;
			if(screenX < (YouDee.xPlayerLoc - bufferWindow) )
				turnUp = true;
			if( ( (YouDee.xPlayerLoc + ((YouDee.playerImage.getWidth() * playerScale) / 2) - bufferWindow ) < screenX) 
					&& ( screenX <= (YouDee.xPlayerLoc + ( (YouDee.playerImage.getWidth() * playerScale) / 2) + bufferWindow ) )){
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
				if( (YouDee.xPlayerLoc + bufferWindow) < screenX)
					turnDown = false;
				if(screenX < (YouDee.xPlayerLoc - bufferWindow) )
					turnUp = false;
				if( ( (YouDee.xPlayerLoc + ((YouDee.playerImage.getWidth() * playerScale) / 2) - bufferWindow ) < screenX) 
						&& ( screenX <= (YouDee.xPlayerLoc + ( (YouDee.playerImage.getWidth() * playerScale) / 2) + bufferWindow ) 
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
