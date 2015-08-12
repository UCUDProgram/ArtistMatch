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

/*
 * NEED TO REDO/REWORK 
 * THE CORRECT ANSWERS/END OF LEVEL FUNCTIONS
 * THE PLACEMENT OF BOXES ON THE SCREEN
 */

public class ArtistGame implements Screen, InputProcessor, ApplicationListener {
	private ArtistMatch game;
	// These booleans are used to display results once
	// Will be used to pass data to score class
	// Will be removed later
	private boolean isEndTime,displayTimeCount,moveDistanceCount,guessCounter;
	
	private String ques,cAnswer,xmlFile;
	private Texture background, ballImage;
	private long startTime, endTime;
	private int angle,ballCount,incorrectGuesses,correctGuess;
	private BitmapFont font,font1, font2;
	private Stage stage;
	private SpriteBatch batch;
	private String [] answers;
	private List<String> correctA, selectedA;
	private float screenWidth,screenHeight,moveDistance;
	private boolean goLeft, goRight,ballShoot, activeBall,turnUp, turnDown,win,tutorial;
	private Ball validBall;
	private Player Player;
	private List<Box> possAnswers;
	private ShapeRenderer shape;
	private Box boxTester;
	
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
		initializeVariables();
		initializeBoolean();
		initializeText();
		Player = new Player(setPlayerXStart(), setPlayerYPos() , game.getDifficulty());
//		ballImage = setBallImage();
		validBall = new Ball(0,0,angle,false,game.getDifficulty());
		
		selectedA = new ArrayList<String>();
		correctA = new ArrayList<String>();
		
		xmlFile = setXMLFile();
		initializeQuestion();
		initializeSelectionArray();
		initializeAnswer();	
		
		
		
//		ques = "Who is the best Musician Ever";
//		answers = new String[4];
//		answers[0]= "Lionel Ritchie";
//		answers[1] = "Michael Jackson";
//		answers[2]= "The Temptations";
//		answers[3] = "The Jackson 5";
//		cAnswer = answers[1];
//		initializeArray();
		
		boxTester = new Box(game.getDifficulty());
		possAnswers = new ArrayList<Box>();
		setBoxes();
		
		System.out.println("The height of the font for the options text and the question is " + font.getLineHeight() );
		
		
//	System.out.println("The height dimensions of the box is " + (possAnswers.get(0).image.getHeight() * possAnswers.get(0).boxScale) );
	
		shape = new ShapeRenderer();
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
	}
	
//	public void initializeArray(){
//		selectedA.clear();
//		correctA.add(answers[1]);
//		correctA.add(answers[3]);
//	}
	
	
	/*
	 * Formats the artist's name to proper formatting, including spacing
	 * Any update to the Levels.xml file needs to be updated here, as well
	 */
	public String formatName(String artName){
		if (game.getArtist().equals("Michael Jackson"))
			return "MichaelJackson";
		else if (game.getArtist().equals("Whitney Houston"))
			return "WhitneyHouston";
		else if (game.getArtist().equals("Mariah Carey"))
			return "MariahCarey";
		else if (game.getArtist().equals("Carrie Underwood"))
			return "CarrieUnderwood";
		else if (game.getArtist().equals("Taylor Swift"))
			return "TaylorSwift";
		else
			return artName;
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
		Element artist = root.getChildByName(formatName(game.getArtist()));
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
		Element artist = root.getChildByName(formatName(game.getArtist()));
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
		Element artist = root.getChildByName(formatName(game.getArtist()));
		Element quesNum = artist.getChildByName(Integer.toString(game.getQuestion()));
		Array<Element> answerCA = quesNum.getChildrenByName("Correct");
		System.out.println("The size of the correct array is " + answerCA.size);
		for (int i = 0; i <answerCA.size; i++){
			correctA.add(answerCA.get(i).getText());
		}
		}
		catch(IOException e){
		}
	}
	
	/*
	 * Function to set the Image of the Ball
	 */
	
/* DELETE THIS COMMENT ONCE IMPLEMENTED	
 * Think about creating an xml file that will store the images String
 * The image strings should be two types added, general and artist Specific
 */	
	
	public Texture setBallImage(){
		List<String> balls = new ArrayList<String>();
		
//		Use of xml file readers to add strings to the balls List
		
		
		int randomBall = (int) Math.random() * balls.size();
		return new Texture(balls.get(randomBall));
	}
	
	
	/*
	 * Functions to initialize the Boxes
	 * Used to set Properties that are passed to the constructor of the Box class
	 */
	
	/*
	 * Sets the Number of rows for displaying the possible answers
	 * Code needs to be reworked
	 */
	public int setRowTotal(){
		if (game.getDifficulty() == 0 )
			return 2;
		else if( game.getDifficulty() == 1 )
			return 3;
		else 
			return 4;
	}
	
	/*
	 * Sets the Scale for the Y Position
	 */
//	public float setYScale(){
//		if(game.getDifficulty() == 0 )
//			return 35;
//		else if (game.getDifficulty() == 1)
//			return ;
//		else
//			return (font.getLineHeight() * 2);
//	}
	
	
	/*
	 * Sets the X Location of the Box
	 */
	public float setBoxXLoc(int boxNum){
		if(boxNum < 5)
			return (float)( (screenWidth * boxNum) / 5);
		else{
			if(boxNum % 2 == 1)
				return (float) (screenWidth / 5);
			else
				return (float) ( (screenWidth / 5) * 4);
		}
	}
	
	/*
	 * Sets the Y Location of the Box
	 */
	public float setBoxYLoc(int boxNum){
		int yStartLoc;
		if (boxNum < 5 )
			yStartLoc = 0;
		else{
			yStartLoc = ( ( (boxNum - 5) / 2) + 1);
		}
		
		return (screenHeight -(boxTester.getImage().getHeight() * boxTester.getBoxScale() ) - 25 ) - (25 * yStartLoc) - (yStartLoc * boxTester.getImage().getHeight() * boxTester.getBoxScale() )   ;
	}
	
	
	/*
	 * Sets the Horizontal Window, in which the options are drawn on the screen
	 */
	public float setBoxWindow(int boxNum){
		int padding = 10;
		if (game.getDifficulty() == 0)
			return (screenWidth / 2) - padding ;
		else if (game.getDifficulty() == 1){
			if (boxNum <=6)
				return (screenWidth / 3) - padding;
			else
				return (screenWidth / 2) - padding;
		}
		else if (game.getDifficulty() == 2){
			if (boxNum <=9)
				return (screenWidth / 3) - padding;
			else
				return (screenWidth / 2) - padding;
		}
		else
			return (screenWidth / 3) - padding;
	}
	
	/*
	 * Sets the Scale for the Y Position
	 */
//	public float setYScale(){
//		
//	}
	
	/*
	 * Sets the X Position of the Option being drawn on the screen 
	 */
	public float setDisplayXDrawPos(int boxNum){
		if (game.getDifficulty() == 0){
			if (boxNum % 2 == 1)
				return 0;
			else
				return ( (screenWidth / 2) );
		}
		else if (game.getDifficulty() == 1){
			if (boxNum < 7){
				return ( ( (boxNum - 1) % 3) * (screenWidth / 3) ) ;
			} else {
				return ( ( (boxNum % 2) * (screenWidth / 2) ));
			}
		} 
		else if (game.getDifficulty() == 2){
			if (boxNum < 10){
				return ( ( (boxNum - 1) % 3) * (screenWidth / 3) ) ;
			} else {
				return 0;
			}
		}
		else{
			return ( ( (boxNum - 1) % 3) * (screenWidth / 3) ) ;
		}
	}
	
	/*
	 * Sets the Y Position of the Option being drawn on the screen 
	 */
	public float setDisplayYDrawPos(int boxNum){
		int rowCount, rowNum;
		if (game.getDifficulty() == 0)
			rowCount = 2;
		else
			rowCount = 3;
		if ( boxNum % rowCount == 0 )
			rowNum = (boxNum / rowCount) - 1;
		else
			rowNum = boxNum / rowCount;
		return (setYMax() - ( (font.getLineHeight() * 2) * rowNum ) );
	}
	
	
	/*
	 * Sets the Base, representing the highest value that the options will appear on the screen, in the Y 
	 */
	public float setYMax(){
		if (game.getDifficulty() == 0)
			return 72;
		else if (game.getDifficulty() == 1)
			return 108;
		else
			return 144;
	}
	/*
	 * Sets the X Location of the Display Selection
	 * REFACTOR TO TAKE IN THE BOX NUMBER
	 */
	public float setDisplayXDraw(int vert){
		if (vert == 0)
			return 0;
		else
			return (int) ( (screenWidth / 2) );
	}
	
	/*
	 * Sets the Y Location of the Display Selection
	 * REFACTOR TO TAKE IN THE BOX NUMBER
	 */
	public float setDisplayYDraw(int hor){
		if (hor == 0)
			return 25;
		else if (hor == 1)
			return 50;
		else
			return 75;
	}
	
	/*
	 * Function to set the Image of the Box
	 */
	
/* DELETE THIS COMMENT ONCE IMPLEMENTED	
 * Think about creating an xml file that will store the images String
 * The image strings should be two types added, general and artist Specific
 */	
	
	public Texture setBoxImage(){
		List<String> box = new ArrayList<String>();
		
//		Use of xml file readers to add strings to the balls List
		
		
		int randomBall = (int) Math.random() * box.size();
		return new Texture(box.get(randomBall));
	}
	
	public void setBoxes(){
		char[] displayAnswer = {'A','B','C','D','E','F','G','H','I','J','K','L'};
		int count = 5;  //Think about how to set the positioning of the boxes
		int numcount = 1;
		int vertdet=0;
		int hordet=0;
		int boxPlace = 0;
		for(int i=0; i< answers.length; i++){
//			
//			
////			Sets the X coordinate for drawing the option, below the question
//			if( (i<4) && (i>1) ){
//				vertdet= 1;
//			}else 
//				vertdet = 0;
////			Sets the Y coordinate for drawing the option, below the queston
//			if ( (i==0)|| (i==2))
//				hordet =1;
//			else 
//				hordet = 0;
//			
////			Sets the Y coordinate placement of the box, based on the difficulty and the number of options available
//			if(i <6)
//				boxPlace = 0;
//			else if( (i == 6 ) || (i == 7) )
//				boxPlace = 1;
//			else if ( (i == 8) || (i == 9 ) )
//				boxPlace = 1;
//			possAnswers.add(new Box(answers[i], setBoxXLoc(numcount),setBoxYLoc(numcount),displayAnswer[i],setDisplayXDraw(vertdet),setDisplayYDraw(hordet),game.getDifficulty() ) );
//			
			
			
			possAnswers.add(new Box(answers[i], setBoxXLoc(numcount),setBoxYLoc(numcount),displayAnswer[i],setDisplayXDrawPos(numcount), setDisplayYDrawPos(numcount),game.getDifficulty() ) );
			numcount++;
		}
	}
	
	/*
	 * FUNCTIONS TO INITIATE THE PLAYER
	 */
	
	/*
	 * Function to set the Image of the Player
	 */
	
/* DELETE THIS COMMENT ONCE IMPLEMENTED	
 * Think about creating an xml file that will store the images String
 * The image strings should be two types added, general and artist Specific
 */	
	
	public Texture setPlayerImage(){
		List<String> player = new ArrayList<String>();
		
//		Use of xml file readers to add strings to the balls List
		
		
		int randomBall = (int) Math.random() * player.size();
		return new Texture(player.get(randomBall));
	}
	
	
	
	/*
//	 * Sets the Player's X Location, which is the starting location for the player
//	 */
	public float setPlayerXStart(){
//		int random = (int)( (Math.random() * (5 ) ) + 1);
		
		if (game.getDifficulty() ==0)
			return screenWidth /2;
		else{
			/* Computation to set the random number size for start position
		Medium = 6
		Hard =	8
		Expert = 10
	
		Scale should be between 1 & segments dividing the window
		Basic formula 
		int random = (int)( (Math.random() * x-1) + 1);
			return ( (screenWidth / x) * random );
		*/
			int base = 4 + (2 * game.getDifficulty());
			int random = (int) (Math.random() * base ) ;
			if(random == 0)
				random = 1;
			return (float) ( (screenWidth / base) * random );
		}
	}
	
	/*
	 * Sets the Y Position of the Player
	 */
	public float setPlayerYPos(){
		return setYMax(); 
//				+ 25;
//		return ((font.getLineHeight() * 2 ) * (setRowTotal() + 1)) + 20;
	}
	
	
	/*
	 * Sets the Y Position of the Question, to be drawn on the screen
	 */
	public float setQuesDrawYPos(){
		return (font.getLineHeight() * 2) * (setRowTotal() +1); 
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
			
//			Drawing and Game Maintenance, while game is being played
			updatePlayerXPos();
			updateAngle();	
			drawEssentialComponents();
			drawAngleInfo();
			if(ballShoot){
				setBallInitial();
				activeBall = true;
				ballCount = 1;
			}
			if(activeBall){
				ballManagement();
				ballBoxCollision();	
				win = areAllAnswersCollect();
			}
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
			
			
			
			game.switchScreens(4);
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
	// ALSO THIS ADDRESSES ASPECTS OF THE GAME THAT CHANGE, SUCH AS THE BALL
//	This function will be deleted because the draw function is now in the draw Essential Components function 
	
	public void drawQuestion(){
		font.draw(batch, ques, screenWidth / 5 , 75 );
	}
	
	/*
	 * Draws the Essential/Vital Game Information on the Screen
	 * -- The Player, where ball firing originates from
	 * -- The Boxes, where the player has to choose from
	 * -- The Question, what the player has to answer correctly
	 */
	public void drawEssentialComponents(){
//		Draw the Player
		batch.draw(Player.getPlayerImage(), Player.getxPlayerLoc(), Player.getyPlayerLoc(), Player.getPlayerImage().getWidth() * Player.getPlayerScale(),Player.getPlayerImage().getHeight() * Player.getPlayerScale());		
		
		
//		Draw the Boxes
		for(Box aAnswer:possAnswers){
			if(aAnswer.isDrawable()){
				batch.draw(aAnswer.getImage(), aAnswer.getBoxXStart(), aAnswer.getBoxYStart(), aAnswer.getImage().getWidth() * aAnswer.getBoxScale(),aAnswer.getImage().getHeight() * aAnswer.getBoxScale());			
				font1.draw(batch,Character.toString(aAnswer.getDisplaySelection()), aAnswer.getBoxXStart(), aAnswer.getBoxYStart());
				font.draw(batch,(aAnswer.getDisplaySelection() + ": " + aAnswer.getaString()), aAnswer.getDrawXLoc(),aAnswer.getDrawYLoc() );
//				font.drawWrapped(batch, (aAnswer.getDisplaySelection() + ": " + aAnswer.getaString()), aAnswer.getDrawXLoc(),aAnswer.getDrawYLoc(), setBoxWindow(aAnswer.))
			}
		}

//		Draw the Question
		font.draw(batch, ques, 0 , screenHeight );

	}
	
	
	// This next function address the player in the game
	/*
	 * Updates the player's X Location, based on the player moving left or right
	 */
	public void updatePlayerXPos(){
		if(goRight&&Player.getxPlayerLoc()<=(screenWidth - (Player.getPlayerImage().getWidth()* Player.getPlayerScale() ) ) ){
			Player.updateXPos(true);
			
		}
		if(goLeft&&Player.getxPlayerLoc() >=( (Player.getPlayerImage().getWidth() / 2) * Player.getPlayerScale() ) ){
			Player.updateXPos(false);
		}
	}
	
	
	 // These next functions address the ball (s) in the game
	/*
	 * Update Ball Count total
	 */
	public void updateBallCount(){
		if (validBall.getxLoc() < 0 || validBall.getxLoc() > screenWidth)
			ballCount = 0;
		else if (validBall.getyLoc() < 0 || validBall.getyLoc() > screenHeight)
			ballCount = 0;
		else
			ballCount = 1;
	}
	
	/*
	 * Initializes the Ball
	 */
	public void setBallInitial(){
	validBall = new Ball(Player.getxPlayerLoc(),Player.getyPlayerLoc(), angle,true,game.getDifficulty());
}
	
	
	/*
	 * Ball(s) Manipulation
	 * -- Update the X Position of the Ball
	 * -- Update the Y Position of the Ball
	 * -- Updates the number of Balls on the game
	 * -- Draws the Ball on the screen
	 */
	public void ballManagement(){
		
//		Updates the Ball's X Position
		if (validBall.getxLoc() <0)
			validBall.setActiveBall(false);
		else
			validBall.updateBallXPos();

//		Updates the Ball's Y Position
		if (validBall.getyLoc() > screenHeight)
			validBall.setActiveBall(false);
		else
			validBall.updateBallYPos();
		
//		Updates the Ball Count of the Level
		updateBallCount();
		
//		Draw the Ball on the Screen
		batch.draw(validBall.getBallImage(), validBall.getxLoc(),validBall.getyLoc(), validBall.getBallImage().getWidth() * validBall.getBallScale(),validBall.getBallImage().getHeight() * validBall.getBallScale() );
		
	}
	
	/*
	 * Display's NonEssential Components
	 * These components provide visual cues to the player, for feedback on game play
	 * The Difficulty of the game will determine the visiblity of these components
	 * -- Angle Indicator
	 * 	-- Difficulty level could give a false indicator
	 * 	-- Could override the appearance of the player
	 * -- Angle Display (Tells the Angle the ball will shoot)
	 */
	public void drawNonEssentialComponents(){
		
	}
	
	
	/*
	 * Draws the String, representing the current angle at which the ball will fire at, on the screen
	 */
	public void drawAngleInfo(){
		font2.draw(batch, ("The angle of Projection is " + angle),(float)(screenWidth * .45), 20);
	}
	
	
	/*
	 * Draws the angle indicator on the screen
	 */
	public void drawAngleIndicator(){
		float bottomLineHyp = 60;
		float topLineHyp = 100;
		
		float bottomXCoord = (float) ((Math.cos(Math.toRadians(angle) ) ) * bottomLineHyp);
		float bottomYCoord = (float) ((Math.sin(Math.toRadians(angle) ) ) * bottomLineHyp);
		
		float bottomX = Player.getxPlayerLoc() + ((Player.getPlayerImage().getWidth() * Player.getPlayerScale()) / 2 ) + bottomXCoord;
		float bottomY = Player.getyPlayerLoc() + ((Player.getPlayerImage().getHeight() * Player.getPlayerScale()) / 2 )  + bottomYCoord ;
		
		float topXCoord = (float) ((Math.cos(Math.toRadians(angle) ) ) * topLineHyp);
		float topYCoord = (float) ((Math.sin(Math.toRadians(angle) ) ) * topLineHyp);
		
		float topX = Player.getxPlayerLoc() + ((Player.getPlayerImage().getWidth() * Player.getPlayerScale() ) / 2 ) + topXCoord;
		float topY = Player.getyPlayerLoc() + ((Player.getPlayerImage().getHeight() * Player.getPlayerScale() ) / 2 ) + topYCoord;
		
		ShapeRenderer shape = new ShapeRenderer();
		shape.setColor(Color.BLACK);
		shape.begin(ShapeType.Line);
		shape.line(bottomX, bottomY, topX, topY);
		shape.end();
	}
	
	
	/*
	 * Updates the angle's value
	 * later a second variable will be used to determine the amount that the angle will change
	 * 	Difficulty will affect the amount that the angle changes
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
		int length = (int) Math.abs( (Player.getxPlayerLoc() + (Player.getPlayerImage().getWidth() / 2) + xCoord ) );
		float height = yCoord - Player.getyPlayerLoc();
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
			if(possibleSelection.isDrawable()){
			if (ballBoxXCollision(possibleSelection) && ballBoxYCollision(possibleSelection)){
				if (isCorrect(possibleSelection.getaString())){
					possibleSelection.setDrawable(false);
					selectedA.add(possibleSelection.getaString());
					activeBall = false;
					ballCount = 0;
					correctGuess += 1;
			}else {
					possibleSelection.setDrawable(false);			
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
		if( (aBox.getBoxXStart() <= validBall.getxLoc()) && (validBall.getxLoc() <= (aBox.getBoxXStart() + (aBox.getImage().getWidth() * aBox.getBoxScale() ))) )
			return true;
		else
			return false;
	}
	
	/*
	 * Determines if a Ball and a Selection collision has occurred, based only on the Y coordinate
	 */
	public boolean ballBoxYCollision(Box aBox){
		if( (aBox.getBoxYStart() <= validBall.getyLoc()) && (validBall.getyLoc() <= (aBox.getBoxYStart() + (aBox.getImage().getHeight() * aBox.getBoxScale() ))) )
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
				if(aBox.getaString().equals(cAnswer))
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
//			int bufferYWindow = 50;
			
//			Handles Angle determination
//			if( (game.getDifficulty() == 2)| game.getDifficulty() == 3){
			if( (Player.getxPlayerLoc() + bufferXWindow) < screenX)
				turnDown = true;
			if(screenX < (Player.getxPlayerLoc() - bufferXWindow) )
				turnUp = true;
//			} else {
//				setAngle(screenX,screenY);
//			}
			
//			Handles Ball Shooting
			if( ( (Player.getxPlayerLoc() + ((Player.getPlayerImage().getWidth() * Player.getPlayerScale()) / 2) - bufferXWindow ) < screenX) 
					&& ( screenX <= (Player.getxPlayerLoc() + ( (Player.getPlayerImage().getWidth() * Player.getPlayerScale()) / 2) + bufferXWindow ) ) ){
//					&& ( screenY <= ( (Player.playerImage.getHeight() * Player.playerScale) + bufferYWindow) ) ) {
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
				
//				Handles Angle determination

				if( (Player.getxPlayerLoc() + bufferWindow) < screenX)
					turnDown = false;
				if(screenX < (Player.getxPlayerLoc() - bufferWindow) )
					turnUp = false;
				
//				Handles Ball Shooting	
				if( ( (Player.getxPlayerLoc() + ((Player.getPlayerImage().getWidth() * Player.getPlayerScale()) / 2) - bufferWindow ) < screenX) 
						&& ( screenX <= (Player.getxPlayerLoc() + ( (Player.getPlayerImage().getWidth() * Player.getPlayerScale()) / 2) + bufferWindow ) 
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
