package ArtistMatch;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Score implements Screen, InputProcessor, ApplicationListener{
	private ArtistMatch AMGame;
	private int incorrect, correct,time;
	private boolean displayScore, initialEnter;
	char [] letters,initLetter;
	int letIndex1,letIndex2,letIndex3;
	private BitmapFont font;
	
	public Score(ArtistMatch game, int right, int wrong, int time){
		this.AMGame = game;
		this.correct = right;
		this.incorrect = wrong;
		this.time = time;
	}

	
	public void create(){
		displayScore = true;
		initialEnter = false;
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
		// TODO Auto-generated method stub
		
		if(displayScore){
			
		} if(initialEnter){
		
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
		// TODO Auto-generated method stub
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
