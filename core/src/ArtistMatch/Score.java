package ArtistMatch;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score implements Screen, InputProcessor, ApplicationListener{
	private ArtistMatch AMGame;
	private SpriteBatch batch;
	private int incorrect, correct,time;
	private boolean displayScore, initialEnter,enterScore;
	private char [] letters,initLetter;
	private int letIndex1,letIndex2,letIndex3;
	private int timeBonus, incorrectPenalty, correctBonus;
	private BitmapFont font;
	private float screenWidth,screenHeight;
	
	public Score(ArtistMatch game, int right, int wrong, int time){
		this.AMGame = game;
		this.correct = right;
		this.incorrect = wrong;
		this.time = time;
	}

	
	public void create(){
		displayScore = true;
		initialEnter = false;
		enterScore = false;
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
		char [] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		letIndex1 = letters.length / 2;
		letIndex2 = letters.length / 2;
		letIndex3 = letters.length / 2;
		initLetter = new char[3];
		initLetter[0] = letters[letIndex1];
		initLetter[1] = letters[letIndex2];
		initLetter[2] = letters[letIndex3];
		font = new BitmapFont();
		Gdx.input.setInputProcessor(this);
	}
	
	
	@Override
	public void render(float delta) {
		batch.begin();
		
		if(displayScore){
			drawScoreTotals();
		} if(initialEnter){
			drawHiScore();
		}
		batch.end();
	}
	
	public int setTimeScore(){
		return time * timeBonus;
	}

	public int setIncorrectScore(){
		return incorrect * incorrectPenalty;
	}
	
	public int setCorrectScore(){
		return correct * correctBonus;
	}
	
	public int setTotalScore(){
		return ( (setTimeScore() + setCorrectScore()) - setIncorrectScore() );
	}
	
	public void drawScoreTotals(){
		font.draw(batch, "Your correct score is " + setCorrectScore(), 100, screenHeight -100);
		font.draw(batch, "Your Time score is " + setTimeScore(), 100, screenHeight -150);
		font.draw(batch, "Your Incorrect score is " + setIncorrectScore(), 100, screenHeight -200);
		font.draw(batch, "Your Total score is " + setTotalScore(), 100, screenHeight -250);
	}
	
	public void drawHiScore(){
		font.draw(batch, initLetter[0], x, y);
		font.draw(batch, initLetter[1], x, y);
		font.draw(batch, initLetter[2], x, y);

		
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
		if(initialEnter){
			if( (0 <= screenX) && (screenX < ( (screenWidth / 10) *3) )){
				if( (screenY <= (screenHeight/ 2) ) && (letIndex1 !=0))
					letIndex1--;
				if( ( (screenHeight/ 2) < screenY ) && (letIndex1 !=letters.length ))
					letIndex1++;
			}
			
			if( (( (screenWidth / 10) *3) <= screenX) && (screenX < ((screenWidth / 10) *6  ))){
				if( (screenY <= (screenHeight/ 2) ) && (letIndex2 !=0))
					letIndex2--;
				if( ( (screenHeight/ 2) < screenY ) && (letIndex2 !=letters.length ))
					letIndex2++;
			}

			if( (( (screenWidth / 10) *6) <= screenX) && (screenX < ((screenWidth / 10) *9  ))){
				if( (screenY <= (screenHeight/ 2) ) && (letIndex3 !=0))
					letIndex3--;
				if( ( (screenHeight/ 2) < screenY ) && (letIndex3 !=letters.length ))
					letIndex3++;
			}

			if( (( (screenWidth / 10) *9) <= screenX) && (screenX <= screenWidth)){
	
			}
			
		}
		
		
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
