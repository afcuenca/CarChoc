package states;

import java.awt.Graphics;
import java.util.ArrayList;

import gameObjects.Constants;
import graphics.Assets;
import ui.Action;
import ui.Button;

public class MenuState extends State{
	
	private ArrayList<Button> buttons;
	
	public MenuState() {
		buttons = new ArrayList<Button>();
		
		buttons.add(new Button(
				Assets.blueBtn,
				Assets.yellowBtn,
				Constants.WIDTH / 2 - Assets.blueBtn.getWidth()/2,
				Constants.HEIGHT / 2 - Assets.blueBtn.getHeight(),
				Constants.PLAY,
				new Action() {
					@Override
					public void doAction() {
						State.changeState(new GameState());
					}
				}
				));
		
		buttons.add(new Button(
				Assets.blueBtn,
				Assets.yellowBtn,
				Constants.WIDTH / 2 - Assets.blueBtn.getWidth()/2,
				Constants.HEIGHT / 2 + Assets.blueBtn.getHeight()/2 ,
				Constants.EXIT,
				new Action() {
					@Override
					public void doAction() {
						System.exit(0);
					}
				}
				));
		
	}
	
	
	@Override
	public void update() {
		for(Button b: buttons) {
			b.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(Assets.Inicio, 0, 0, null);
		g.drawImage(Assets.logo, 190, 50, null);
		for(Button b: buttons) {
			b.draw(g);
		}
	}

}