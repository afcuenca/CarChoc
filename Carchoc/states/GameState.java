package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameObjects.BonusCar;
import gameObjects.Constants;
import gameObjects.Enemy;
import gameObjects.Message;
import gameObjects.MovingObject;
import gameObjects.Player;
import graphics.Animation;
import graphics.Assets;
import math.Vector2D;

public class GameState extends State{
	public static final Vector2D PLAYER_START_POSITION = new Vector2D(Constants.WIDTH/2 - Assets.player.getWidth()/2,
			Constants.HEIGHT/2 - Assets.player.getHeight()/2);

	
	private Player player;
	private ArrayList<MovingObject> movingObjects = new ArrayList<>();
	private ArrayList<Animation> explosions = new ArrayList<>();
	private ArrayList<Message> messages = new ArrayList<>();
	
	private int puntaje = 0;
	private int vidas = 3;
	
	private int bonusCarCount = 20;
	private int enemyCount = 3;
	private boolean gameOver;
	private int fase = 1;
	
	
	public GameState() {
		player = new Player(PLAYER_START_POSITION, new Vector2D(),
				Constants.PLAYER_MAX_VEL, Assets.player, this);
		movingObjects.add(player);

		startWave();
		startEnemy();
	}

	public void addPuntaje(int value, Vector2D position) {
		puntaje += value;
		messages.add(new Message(position, true, "+"+value+" score", Color.WHITE, false, Assets.fontMed, this));
	}

	private void startWave() {
		double x, y;

		messages.add(new Message(new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2), false,
				"FASE "+fase++, Color.WHITE, true, Assets.fontBig, this));
		
		for (int i = 0; i < bonusCarCount; i++) {
			x = i % 2 == 0 ? Math.random() * Constants.WIDTH : 0;
			y = i % 2 == 0 ? 0 : Math.random() * Constants.HEIGHT;

			BufferedImage texture = Assets.bonuscar;

			movingObjects.add(new BonusCar(new Vector2D(x, y),
					new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2),
					Constants.ENEMY_VEL * Math.random() + 1, texture, this));
		}
		bonusCarCount++;
	}

	private void startEnemy() {
		double x, y;

		for (int i = 0; i < enemyCount; i++) {
			x = i % 2 == 0 ? Math.random() * Constants.WIDTH : 0;
			y = i % 2 == 0 ? 0 : Math.random() * Constants.HEIGHT;

			BufferedImage texture = Assets.enemycar;

			movingObjects.add(new Enemy(new Vector2D(x, y),
					new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2),
					Constants.ENEMY_VEL * Math.random() + 1, texture, this));
		}
		enemyCount++;
	}

	public void playExplosion(Vector2D position) {
		explosions.add(new Animation(Assets.exp, 50,
				position.subtract(new Vector2D(Assets.exp[0].getWidth() / 2, Assets.exp[0].getHeight() / 2))));
	}

	public void update() {
	    for (int i = 0; i < movingObjects.size(); i++) {
	        movingObjects.get(i).update();
	    }

	    for (int i = 0; i < explosions.size(); i++) {
	        Animation anim = explosions.get(i);
	        anim.update();
	        if (!anim.isRunning()) {
	            explosions.remove(i);
	        }
	    }

	    boolean playerAlive = false;
	    for (MovingObject object : movingObjects) {
	        if (object instanceof Player) {
	            playerAlive = true;
	            break;
	        }
	    }

	    if (!playerAlive) {
	        respawnPlayer();
	    }

		for(int i = 10; i < movingObjects.size(); i++)
			if(movingObjects.get(i) instanceof BonusCar)
				return;
		
		startWave();
		startEnemy ();
		
	
		}
	

	private void respawnPlayer() {
	    if (vidas > 0) {
	        player = new Player(new Vector2D(Constants.WIDTH / 2 - Assets.player.getWidth() / 2,
	                Constants.HEIGHT / 2 - Assets.player.getHeight() / 2), new Vector2D(), Constants.PLAYER_MAX_VEL,
	                Assets.player, this);
	        movingObjects.add(player);
	        vidas--;
	    } else {
	        gameOver = true;
	    }
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		for(int i = 0; i < messages.size(); i++)
			messages.get(i).draw(g2d);
		
		for(int i = 0; i < movingObjects.size(); i++)
			movingObjects.get(i).draw(g);

		for(int i = 0; i < explosions.size(); i++){
			Animation anim = explosions.get(i);
			g2d.drawImage(anim.getCurrentFrame(), (int)anim.getPosition().getX(), (int)anim.getPosition().getY(),
					null);
			
			
		}
		
		drawPuntaje(g);
		drawVidas(g);

		
	}

	private void drawPuntaje(Graphics g) {
		Vector2D pos = new Vector2D(850, 25);
		String puntajeToString = Integer.toString(puntaje);

		for (int i = 0; i < puntajeToString.length(); i++) {
			g.drawImage(Assets.numbers[Integer.parseInt(puntajeToString.substring(i, i + 1))], (int) pos.getX(),
					(int) pos.getY(), null);
			pos.setX(pos.getX() + 20);
		}
	}

	private void drawVidas(Graphics g) {
		
		Vector2D livePosition = new Vector2D(25, 25);
		g.drawImage(Assets.vida, (int) livePosition.getX(), (int) livePosition.getY(), null);
		g.drawImage(Assets.numbers[10], (int) livePosition.getX() + 40, (int) livePosition.getY() + 5, null);

		String livesToString = Integer.toString(vidas);
		Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());

		if (gameOver) {

	        g.setColor(Color.RED);
	        g.setFont(Assets.fontBig);
	        g.drawString("GAME OVER", (int) pos.getX() + 300, (int) pos.getY() + 230);
			
			} else {
	        
				for (int i = 0; i < livesToString.length(); i++) {
				    int number = Integer.parseInt(livesToString.substring(i, i + 1));
				    if (number < 0 || number > 9)
				        break;
				    g.drawImage(Assets.numbers[number], (int) pos.getX() + 60, (int) pos.getY() + 5, null);
				    pos.setX(pos.getX() + 20);
					}
		
			}

		}

	public ArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public void reset() {
		puntaje = 0;
		vidas = 3;
		bonusCarCount = 20;
		enemyCount = 10;
		movingObjects.clear();
		explosions.clear();
		startWave();
		startEnemy();
	}
}
