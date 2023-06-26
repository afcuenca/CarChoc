 package gameObjects;
 

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;

public class BonusCar extends MovingObject {

	
	public BonusCar(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
		super(position, velocity, maxVel, texture, gameState);

	}

	@Override
	public void update() {
		position = position.add(velocity);
		
		if(position.getX() > Constants.WIDTH)
			position.setX(-width);
		if(position.getY() > Constants.HEIGHT)
			position.setY(-height);
		
		if(position.getX() < - width)
			position.setX(Constants.WIDTH);
		if(position.getY() < - height)
			position.setY(Constants.HEIGHT);
		
		angle+= Constants.DELTAANGLE/5;
		
	
	}
	
	@Override
	public void Destroy() {
		gameState.addPuntaje(Constants.BONUSCAR_SCORE, position);
		super.Destroy();
	}

	@Override
	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		
		at.rotate(angle, width/2, height/2);
		
		g2d.drawImage(texture, at, null);
		
	}

}