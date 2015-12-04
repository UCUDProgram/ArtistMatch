package ArtistMatch;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Score implements Screen, InputProcessor, ApplicationListener{
	private ArtistMatch game;
	private SpriteBatch batch;
	private int incorrect, correct,time;
	private BitmapFont font, font1, font2, font3;
	private float screenWidth,screenHeight,moveDist;
	private double timeScore, correctScore, incorrectScore, moveScore, totalScore;
	
	
	private Stage stage;
	private boolean displayScore, initialEnter,enterScore;
	private char [] letters,initLetter;
	private int letIndex1,letIndex2,letIndex3;
	private int timeBonus, incorrectPenalty, correctBonus, movePenalty;
	
	
	public Score(ArtistMatch game){
		this.game = game;
	}
	
	public void create(){
		batch = new SpriteBatch();
		initializeScoreVariables();
		initializeScoreValues();
		displayScore = true;
		initialEnter = false;
		enterScore = false;
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
		font = new BitmapFont();
		font1 = new BitmapFont();
		font2 = new BitmapFont();
		font1.setColor(Color.GREEN);
		font2.setColor(Color.RED);
		
		
		stage = new Stage();
		char [] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		letIndex1 = letters.length / 2;
		letIndex2 = letters.length / 2;
		letIndex3 = letters.length / 2;
		initLetter = new char[3];
		initLetter[0] = letters[letIndex1];
		initLetter[1] = letters[letIndex2];
		initLetter[2] = letters[letIndex3];
		font3 = new BitmapFont();
		font3.setScale((screenWidth * 3) / 10, screenHeight);
		
		
		Gdx.input.setInputProcessor(this);
//		Gdx.input.setInputProcessor(stage);
	}
	
	
	@Override
	public void render(float delta) {
		batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		if(displayScore){
			drawScoreTotals();
		} else{
//			(initialEnter){
//			drawHiScore();
			font1.draw(batch, "The Entering initials Screen is working ", 100, screenHeight -100);
		}
		
		batch.end();
	}
	
	/*
	 * Initialize the Score variables
	 */
	public void initializeScoreVariables(){
		correct = game.getRight();
		incorrect = game.getWrong();
		time = game.getTime();
		moveDist = game.getMovement();
	}
	
	/*
	 * Initializes the multiple variables
	 */
	public void initializeScoreValues(){
		timeScore = setTimeScore();
		incorrectScore = setIncorrectScore();
		correctScore = setCorrectScore();
		moveScore = setMoveScore();
		totalScore = setTotalScore();
	}
	
//											THESE FUNCTIONS DEAL WITH THE TIME SCORE IN THE GAME
	/*
	 * Sets the Time Score
	 */
	public double setTimeScore(){
		double tscore = timeBasis();
		double timeScore = 0;
		if (tscore > 1)
			tscore = 1;
		double tScore = (timeSpeedScoreBase() - ( ( 1- tscore) * timeSpeedScoreBase() ) ) * timeMult(); 
		timeScore += tScore;
		timeScore += unUsedTimeBonus();
		timeScore -= setTimePenalty();
		return timeScore;
	}

	/*
	 * Sets the unused time bonus for the player
	 * Any unused time positively affects the player's time score
	 */
	public double unUsedTimeBonus(){
		if (time < setTimeMax()){
			int unused = setTimeMax() - time;
			return unused * unusedTimeBasis();
		} else
			return 0;
	}
	
	/*
	 * Helper function for the unused time bonus
	 * Difficulty determines each second's score value 
	 */
	public int unusedTimeBasis(){
		if (game.getDifficulty() == 0)
			return 30;
		else if (game.getDifficulty() == 1)
			return 40;
		else if (game.getDifficulty() == 2)
			return 45;
		else
			return 55;
	}
	
	/*
	 * Determines the time score multiplier, when finishing the game below the recommended time
	 */
	public double timeMult(){
		if (game.getDifficulty() == 0)
			return 1.25;
		else if (game.getDifficulty() == 1)
			return 1.75;
		else if (game.getDifficulty() == 2)
			return 2.5;
		else
			return 4.5;
	}
	
	/*
	 * Returns the ratio, referring to the time to correctly answer the question
	 * A double below 1 indicates that the player answered the question in a time below the max number of time for the level
	 * A double above 1 indicates that the player answered the question in a time above the max number of time for the level 
	 */
	public double timeBasis(){
		int max = setTimeMax();
			return (double) ((time / max)) ;
	}
	
	
	/*
	 * Sets the time Speed Base Value
	 * Used to determine the base for answering the question fast
	 */
	public double timeSpeedScoreBase(){
		if (game.getDifficulty() == 0)
			return 50;
		else if (game.getDifficulty() == 1)
			return 60;
		else if (game.getDifficulty() == 2)
			return 75;
		else
			return 95;
	}

	/*
	 * Sets the Max time that the player needs to answer the question
	 */
	public int setTimeMax(){
		if (game.getDifficulty() == 0)
			return 15;
		else if (game.getDifficulty() == 1)
			return 30;
		else if (game.getDifficulty() == 2)
			return 37;
		else
			return 60;
	}
	
	/*
	 * Sets the Time penalty with answering this question
	 * Any Time above the Max time will negatively impact the player's Time Score
	 */
	public double setTimePenalty(){
		int extraTime = time - setTimeMax();
		if (extraTime <= 0)
			return 0;
		else{
			return (timePenalty(extraTime) * setPenaltyMult() ) ;
		}
	}
	
	/*
	 * Sets the value of the time penalty
	 * Passes in a parameter, representing the number of extra time needed to answer the question(in seconds)  
	 */
	public int timePenalty(int overTime){
		int tPen = 0;
		int first10Pen = setTenSecPenBase();
		if(overTime <= 10){
			for(int i = 0;i < overTime; i++){
				tPen += first10Pen; 
			}
			return tPen;
		} else{
			for(int i =0; i<10; i++)
				tPen += first10Pen;
			int fifteenSecPen = set15SecPenBase();
			int thirtySecPen = set30SecPenBase();
			int fifteen = overTime / 15;
			int thirty = overTime / 30;
			
			tPen += (fifteenSecPen * fifteen);
			tPen += (thirtySecPen * thirty);
			return tPen;
		}
	}
	
	/*
	 * Sets the Base for the first 10 seconds of extra time, to penalize the player
	 */
	public int setTenSecPenBase(){
		if (game.getDifficulty() == 0)
			return 5;
		else if(game.getDifficulty() == 1)
			return 20;
		else if (game.getDifficulty() == 2)
			return 30;
		else
			return 40;
	}
	
	/*
	 * Sets the Base for every 15 seconds of extra time, to penalize the player
	 */
	public int set15SecPenBase(){
		if (game.getDifficulty() == 0)
			return 20;
		else if(game.getDifficulty() == 1)
			return 30;
		else if (game.getDifficulty() == 2)
			return 50;
		else
			return 70;
	}
	
	/*
	 * Sets the Base for every 30 seconds of extra time, to penalize the player
	 */
	public int set30SecPenBase(){
		if (game.getDifficulty() == 0)
			return 35;
		else if(game.getDifficulty() == 1)
			return 50;
		else if (game.getDifficulty() == 2)
			return 75;
		else
			return 100;
	}
	
	/*
	 * Sets the multiplier to penalize the player's score
	 */
	public double setPenaltyMult(){
		if (game.getDifficulty() == 0)
			return 1.0;
		else if(game.getDifficulty() == 1)
			return 1.50;
		else if (game.getDifficulty() == 2)
			return 2.75;
		else
			return 5.5;
	}
	
//											THESE FUNCTIONS DEAL WITH THE INCORRECT ANSWER SCORE IN THE GAME
	/*
	 * Sets the Incorrect Answer Score
	 */
	public double setIncorrectScore(){
		return incorrect * setWrongPenalty();
	}
	
	/*
	 * Sets the Wrong Answer multiplier
	 */
	public int setWrongPenalty(){
		if (game.getDifficulty() ==0)
			return 30;
		if (game.getDifficulty() ==1)
			return 40;
		if (game.getDifficulty() ==2)
			return 50;
		else
			return 75;
	}
	
	
//											THESE FUNCTIONS DEAL WITH THE CORRECT ANSWER SCORE IN THE GAME
	/*
	 * Sets the Correct Answer Score
	 */
	public double setCorrectScore(){
		return correct * setCorrectBonus();
	}
	
	/*
	 * Sets the Correct Answer multiplier
	 */
	public int setCorrectBonus(){
		if (game.getDifficulty() ==0)
			return 80;
		if (game.getDifficulty() ==1)
			return 70;
		if (game.getDifficulty() ==2)
			return 60;
		else
			return 45;
	}
	
//											THESE FUNCTIONS DEAL WITH THE MOVEMENT SCORE IN THE GAME
	/*
	 * Sets the Move Score
	 */
	public double setMoveScore(){
		double move = ((double) (moveDist / setMoveDistBasis() ) ) * setMovPen();
		return (move * 100) / 100 ;
	}
	
	/*
	 * Function to determine the basis for movement penalty
	 * The more one moves, coupled with difficulty, decreases the distance one moves before penalty is called
	 */
	public int setMoveDistBasis(){
		if (game.getDifficulty() ==0)
			return 50;
		if (game.getDifficulty() ==1)
			return 40;
		if (game.getDifficulty() ==2)
			return 30;
		else
			return 15;
	}
	
	/*
	 * Sets the Move Penalty Bonus multiplier
	 */
	public int setMovPen(){
		if (game.getDifficulty() ==0)
			return 70;
		if (game.getDifficulty() ==1)
			return 80;
		if (game.getDifficulty() ==2)
			return 90;
		else
			return 110;
	}
	
//											THESE FUNCTIONS DEAL WITH THE TOTAL SCORE IN THE GAME
	/*
	 * Sets the Total Score
	 */
	public double setTotalScore(){
		return timeScore + correctScore - incorrectScore - moveScore;
	}
	
	
//	/*
//	 * Sets the Time Bonus multiplier
//	 */
//	public int setTimeBonus(){
//		if (game.getDifficulty() ==0)
//			return 70;
//		if (game.getDifficulty() ==1)
//			return 60;
//		if (game.getDifficulty() ==2)
//			return 50;
//		else
//			return 35;
//	}
	
	
	/*
	 * Draws the Score on the Screen
	 * Gives the player feedback on their score
	 */
	public void drawScoreTotals(){
		font1.draw(batch, "Your correct score is " + setCorrectScore(), 100, screenHeight -100);
		
		if (setTimeScore() > 0)
			font1.draw(batch, "Your Time score is " + setTimeScore(), 100, screenHeight -150);
		else
			font2.draw(batch, "Your Time score is " + setTimeScore(), 100, screenHeight -150);
		
		font2.draw(batch, "Your Incorrect score is " + setIncorrectScore(), 100, screenHeight -200);
		font2.draw(batch, "Your Movement score is " + setMoveScore(), 100, screenHeight -250);
		font.draw(batch, "Your Total score is " + setTotalScore(), 100, screenHeight -300);
		font.draw(batch, "Click to Continue", 10, screenHeight -20);
	}
	
	public void enterHiScores(){
		displayScore =  false;
		initialEnter = true;
	}
			
	/*
	 * Draws the three components to enter initials on the Screen
	 */
	public void drawHiScore(){
		font3.draw(batch, Character.toString(initLetter[0]), 0, 0);
		font3.draw(batch, Character.toString(initLetter[1]),(float) ((screenWidth * 3) / 10),(float) 0);
		font3.draw(batch, Character.toString(initLetter[2]), (screenWidth * 6) / 10, 0);
	}
	
	
	/*
	 * Clears the Score Variables values and resets them to 0
	 */
	public void clearScoreVariables(){
		game.setMovement(0);
		game.setRight(0);
		game.setWrong(0);
		game.setTime(0);
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
		if(displayScore){
			enterHiScores();
		}	
		if(initialEnter){
//		These functions will be called right before exiting the score screen
		clearScoreVariables();
		game.switchScreens(4);
		}
		enterHiScores();
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//		if(initialEnter){
//			if( (0 <= screenX) && (screenX < ( (screenWidth / 10) *3) )){
//				if( (screenY <= (screenHeight/ 2) ) && (letIndex1 !=0))
//					letIndex1--;
//				if( ( (screenHeight/ 2) < screenY ) && (letIndex1 !=letters.length ))
//					letIndex1++;
//			}
//			
//			if( (( (screenWidth / 10) *3) <= screenX) && (screenX < ((screenWidth / 10) *6  ))){
//				if( (screenY <= (screenHeight/ 2) ) && (letIndex2 !=0))
//					letIndex2--;
//				if( ( (screenHeight/ 2) < screenY ) && (letIndex2 !=letters.length ))
//					letIndex2++;
//			}
//
//			if( (( (screenWidth / 10) *6) <= screenX) && (screenX < ((screenWidth / 10) *9  ))){
//				if( (screenY <= (screenHeight/ 2) ) && (letIndex3 !=0))
//					letIndex3--;
//				if( ( (screenHeight/ 2) < screenY ) && (letIndex3 !=letters.length ))
//					letIndex3++;
//			}
//
//			if( (( (screenWidth / 10) *9) <= screenX) && (screenX <= screenWidth)){
//	
//			}
//			
//		}
		
		if(displayScore){
			enterHiScores();
		}	
		if(initialEnter){
//		These functions will be called right before exiting the score screen
		clearScoreVariables();
		game.switchScreens(4);
		}
		
		enterHiScores();
		
//		These functions will be called right before exiting the score screen
//		clearScoreVariables();
//		game.switchScreens(4);
		
		
		
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
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
