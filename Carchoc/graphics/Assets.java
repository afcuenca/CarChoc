package graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage player, propuls, bonuscar, enemycar, vida;
	
	public static BufferedImage [] exp = new BufferedImage [9];
	
	public static BufferedImage[] numbers = new BufferedImage[11];
	
	// fonts
	
	public static Font fontBig;
	public static Font fontMed;

	public static BufferedImage blueBtn;
	public static BufferedImage yellowBtn;
	
	public static BufferedImage Inicio, logo;
	
	public static void init ()
	{
		player = Loader.ImageLoader("/Imagenes/RedCar.png");
		
		propuls = Loader.ImageLoader("/Imagenes/Propuls.png");
		
		bonuscar = Loader.ImageLoader ("/Imagenes/bonusCar.png");
		
		enemycar = Loader.ImageLoader ("/Imagenes/med1.png");
		
		vida = Loader.ImageLoader ("/Imagenes/life.png");
		
		fontBig = Loader.loadFont("/fonts/future.ttf", 42);
		
		fontMed = Loader.loadFont("/fonts/future.ttf", 20);
		
		for (int i = 0; i < exp.length; i++)
			exp [i] = Loader.ImageLoader("/ImagesExplosions/"+i+".png");
		
		for(int i = 0; i < numbers.length; i++)
			numbers[i] = Loader.ImageLoader("/ImagesNumbers/"+i+".png");
		
		blueBtn = Loader.ImageLoader("/ImagesInterfaz/button_inicio.png");
		
		yellowBtn = Loader.ImageLoader("/ImagesInterfaz/button_inicio2.png");
		
		Inicio = Loader.ImageLoader("/ImagesInterfaz/Inicio.png");
		
		logo = Loader.ImageLoader("/ImagesInterfaz/logo.png");
	}
	
}
