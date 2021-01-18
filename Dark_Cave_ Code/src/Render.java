import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.LabelUI;

public class Render {

	/**
	 * @author Agzam4
	 * --------{Info}--------
	 *  Render & Other :)
	 * ----------------------
	 * PS: I'm forgot added
	 * "To menu" button
	 */
	
	public final boolean[] isWalls = new boolean[] {
			false,true,false,false,false,  // 0-4
			false,false,false,true,false, // 5-9
			false,false,false,false,false, // 10-14
			false,true,true,true,true, // 15-19
			false,false,false,false,false  // 20-24
	};
	private boolean right = false;
	private boolean left = false;
	private boolean up = false;
	private boolean kr = false;
	private boolean fullMap = false;
//	private boolean key = false;
	private boolean viewMode = false;
	private boolean upper = true;
	
	private int goldtotal = 0;
	
	private int buttonId = -1;
	
	private int myX = 0;
	private int myY = 0;
	
	boolean miniMap = false;
	
	private String fps = "";
	
	private int[][] lightR;
	private int[][] lightG;
	private int[][] lightB;
	private int[][] type;
	private int[] boosetsTime = new int[2];
	
	private boolean game = true;

	private int timing = 50;
	private int w = 1;
	private int h = 1;
	private double goX = 0;
	private double goY = 0;

	private double px = 10;
	private double py = 10;
	
	private double sx = 0;
	private double sy = 0;
	
	private double pw = 23;
	private double ph = 23;
	private BufferedImage main; 
	
	private  int collectedGold = 0;

	private BufferedImage[] textures = new BufferedImage[11];
	private BufferedImage[] upperTextures = new BufferedImage[11];

	private final Color[] miniColors = new Color[11];// {
//			new Color(109,89,72),
//			new Color(61,61,61),
//			Color.WHITE,
//			new Color(78,193,215),
//			new Color(154,98,227),
//	};

	
	private BufferedImage mouseTexture;
	private boolean ismouseTexture = true;
	
	private BufferedImage[] buttons = new BufferedImage[8];
	private boolean[] buttonsPress = new boolean[8];
	

	private int[][] opendMiniMap;

	
	private void Init(JLabel iv) {
		iv.setUI(new LabelUI() {
		@Override
		public void paint(Graphics gr, JComponent c) {
			Graphics2D g = (Graphics2D) gr;
			g.drawImage(main, 0, 0, null);
			super.paint(g, c);
		}
		});
	}
	public void Start(int[][] types, int ww, int hh, JLabel iv, JPanel f, int gold, JFrame frame) {
		
		try {
			mouseTexture = ImageIO.read(new File(System.getProperty("user.dir") + "\\textures\\mouse.png"));
		} catch (IOException e2) {
			ismouseTexture = false;
		}
		
		for (int i = 0; i < boosetsTime.length; i++) {
			boosetsTime[i] = 0;
		}
		
		goldtotal = gold;
		
		w = ww;
		h = hh;

		type = new int[ww][hh];
		lightR = new int[ww][hh];
		lightG = new int[ww][hh];
		lightB = new int[ww][hh];

		for (int x = 0; x < ww; x++) {
			for (int y = 0; y < hh; y++) {
				type[x][y] = types[x][y];
				lightR[x][y] = 0;
				lightG[x][y] = 0;
				lightB[x][y] = 0;
			}
		}

		for (int i = 0; i < textures.length; i++) {
			try {
				//				System.out.println(System.getProperty("user.dir") + "\\textures\\" + i + ".png");
				textures[i] =  ImageIO.read(new File(System.getProperty("user.dir") + "\\textures\\" + i + ".png"));
				upperTextures[i] =  ImageIO.read(new File(System.getProperty("user.dir") + "\\textures\\up\\" + i + ".png"));
			} catch (IOException e) {
				textures[i] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
				upperTextures[i] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			}
		}
		InitColorsArr();
		
		for (int i = 0; i < buttons.length; i++) {
			try {
				//				System.out.println(System.getProperty("user.dir") + "\\textures\\" + i + ".png");
				buttons[i] =  ImageIO.read(new File(System.getProperty("user.dir") + "\\textures\\buttons\\B" + i + ".png"));
			} catch (IOException e) {
				buttons[i] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
			}
			buttonsPress[i] = false;
		}
		
		Init(iv);

		Thread thread = new Thread() {

			public void run() {
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
				}
				
				GraphicsConfiguration gfxConfig = GraphicsEnvironment.
				        getLocalGraphicsEnvironment().getDefaultScreenDevice().
				        getDefaultConfiguration();

				
				BufferedImage main2 = new BufferedImage(iv.getWidth(), iv.getHeight(), BufferedImage.TYPE_INT_RGB);
				main = gfxConfig.createCompatibleImage(
						main2.getWidth(), main2.getHeight(), main2.getTransparency());
				
				iv.repaint();
				game = false;
				
				if(ismouseTexture) {
					iv.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
							mouseTexture,
							new Point(0, 0), "blank"));
				}else {
					iv.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
				menuSounds = new Sounds();
				menuSounds.Play(Sounds.BG_MUSIC_2);
				
				while (true) {
					for (int i = 0; i < buttonsPress.length; i++) {
						buttonsPress[i] = false;
					}
					DrawMenu();
					iv.repaint();
					if(buttonsPress[0]) {
						menuSounds.Stop();
						menuSounds = null;
						game = true;
						RenderGame(f, iv, hh);
					}
					if(buttonsPress[1]) {
						System.exit(-1);
					}
					if(buttonsPress[2]) {
						UpdateSize();
						JSettings settings = new JSettings(0,0,null,0,0,0,true);
						settings.main(h,w, frame, 32, main.getWidth(), main.getHeight(), miniMap);
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		thread.start();

		// TODO: You can delite
		iv.addMouseListener(new MouseListener() {
			

			@Override
			public void mouseReleased(MouseEvent e) {
				buttonId = -1;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				buttonId = e.getButton();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		// This is to delite
		iv.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				myX = e.getX();
				myY = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				myX = e.getX();
				myY = e.getY();
				
			}
		});
		iv.setFocusable(true);
		iv.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					right = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					left = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					up = false;
				}
				// Mby add ESC (back to menu)
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					right = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					left = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					up = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_R){
					kr = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_V){
					viewMode = !viewMode;
				}
				if(e.getKeyCode() == KeyEvent.VK_U){
					upper = !upper;
				}
				if(e.getKeyCode() == KeyEvent.VK_M){
					fullMap = !fullMap;
				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					game = false;
				}
			}
		});
	}

	private void DrawMenu() {
		
		Graphics2D g  = (Graphics2D) main.getGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, main.getWidth(), main.getHeight());

		g.drawImage(buttons[3], 0, 0,  main.getWidth(), main.getHeight(),  null);///
		
		DrawButton(g, (int) ((main.getWidth()/6)*0.25), main.getWidth()/4, main.getWidth()/8, 0);
		DrawButton(g, (int) ((main.getWidth()/6)*1.25), main.getWidth()/4, main.getWidth()/8, 2);
		DrawButton(g, (int) ((main.getWidth()/6)*2.25), main.getWidth()/4, main.getWidth()/8, 1);

		g.setFont(new Font("Dialog", Font.PLAIN, 25));
		g.drawImage(upperTextures[6], 15, 15, null);
		g.setColor(new Color(255, 223, 50));
		g.drawString("Total Gold: " + goldtotal, 50, 40);
		
		g.dispose();
	}
	
	private void InitColorsArr() {
		for (int i = 0; i < textures.length; i++) {
			int red = 0;
			int green = 0;
			int blue = 0;
			
			BufferedImage t = textures[i];
			for (int x = 0; x < t.getWidth(); x++) {
				for (int y = 0; y <  t.getHeight(); y++) {
					Color c = new Color(t.getRGB(x, y));
					red += c.getRed();
					green += c.getGreen();
					blue += c.getBlue();
				}
			}
			miniColors[i] = new Color(red/(32*32), green/(32*32), blue/(32*32));
			
			miniColors[6] = Color.YELLOW;
		}
	}
	
	private void DrawButton(Graphics2D g, int y, int w, int h, int id) {
		int x =  (main.getWidth()-w)/2;
		if(myX > x && myX < x+w) {
			if(myY > y && myY < y+h) {
				g.setColor(new Color(255,255,255,75));
				g.fillRect(x, y, w, h);
				if(buttonId > -1) {
					buttonsPress[id] = true;
				}
			}
		}
		g.drawImage(buttons[id], x, y, w, h, null);
	}

	
	private void UpdateSize() {
		File_ file = new File_();
		
		String[] size = new String[] {"50", "50"};
		try {size = file.ReadFile(System.getProperty("user.dir") + "\\data\\MapSize.txt").split("x");
		}catch (IOException e){}
		w = 50;
		try {w = Integer.valueOf(size[0]);
		}catch (NumberFormatException e){}
		h = 50;
		try {h = Integer.valueOf(size[1]);
		}catch(NumberFormatException e){}
		
		miniMap = false;
		try {miniMap = 1 == (int)(file.ReadFile(System.getProperty("user.dir") + "\\data\\MiniMap.txt").toCharArray()[0]);
		}catch (IOException e){}

		opendMiniMap = new int[w][h];
		type = new int[w][h];
		lightR = new int[w][h];
		lightG = new int[w][h];
		lightB = new int[w][h];

		for (int x = 0; x < w; x++) {
			for (int y = 10; y < h; y++) {
				type[x][y] = 0;
				lightR[x][y] = 0;
				lightG[x][y] = 0;
				lightB[x][y] = 0;
				opendMiniMap[x][y] = 0;
			}
		}
	}
	
	int msgTime = 0;
	
	Sounds menuSounds;
	
	private void RenderGame(JPanel f, JLabel iv, int MaxGold) {
		
		UpdateSize();
		System.out.println(32);
		
		Generator generator = new Generator();
		int[][] type2 = generator.Generate(w, h, main, buttons[6], iv);
		type = null;
		type = new int[w][h];
		MaxGold = 0;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				type[x][y] = type2[x][y];
				if(type2[x][y] == 6) {
					MaxGold++;
				}
			}
		}
		
		iv.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "blank"));
		
		long start;
		start = System.nanoTime();
		int fpsint = 0;
		collectedGold = 0;
		
		px = w*32/2;
		py = h*32/2;
		
		double sx2 = px*32;
		double sy2 = py*32;
		
		kr = true;

		if(type[(int) (px/32)][(int) (py/32) + 1] == 6) {
			collectedGold++;
		}
		type[(int) (px/32)][(int) (py/32) + 1] = 1;

		if(type[(int) (px/32)][(int) (py/32)] == 1) {
			type[(int) (px/32)][(int) (py/32)] = 0;
		}

		goX = 0;
		goY = 0;

		Sounds sounds = new Sounds();;
		sounds.Play(Sounds.BG_MUSIC_1);
		
		while (game) {

			sx2 =  (main.getWidth()-pw) / 2;
			sy2 =  main.getHeight()/2;//hh*16;

			final double sxsx = sx + sx2;
			final double sysy = sy + sy2;
			
			Graphics2D g  = (Graphics2D) main.getGraphics();
			g.setColor(new Color(0,0,0));
			g.fillRect(0, 0, main.getWidth(),main.getHeight());
			g.setColor(Color.WHITE);
			g.drawRect(
					(int)(sxsx)-5,
					(int)(sysy)-5,
					w*32 + 10,
					h*32 + 10);
			
			
			int startX = (int) (-(sx + sx2)/32);
			if(startX < 0)startX = 0;
			int endX = (main.getWidth()/32) + startX + 2;
			if(endX > w)endX = w;

			int startY = (int) (-(sy + sy2)/32);
			if(startY < 0)startY = 0;
			int endY = (main.getHeight()/32) + startY + 2;
			if(endY > h)endY = h;
			
			// TODO: MiniMap
			
			final int xX = fullMap ? 8: 1;
			BufferedImage minimap = new BufferedImage(iv.getWidth()/8*xX, iv.getHeight()/8*xX, BufferedImage.TYPE_INT_RGB);
			if(miniMap) {
				Graphics2D mg = (Graphics2D) minimap.getGraphics();
				for (int x = startX*2-(startX*xX); x < endX*xX; x++) {
					for (int y = startY*2-(startY*xX); y < endY*xX; y++) {
						if(x > 0 && x < w) {
							if(y > 0 && y < h) {
								final int dX = (int) ((sxsx + (x*32))/8);
								final int dY = (int) ((sysy + (y*32))/8);//(int) ((x*4)+(minimap.getWidth()+(sx + sx2))/2);//-(sx + sx2)
								mg.setColor(miniColors[type[x][y]]);
								mg.fillRect(
										dX + minimap.getWidth()/2 - (endX - startX)*2,
										dY + minimap.getHeight()/2 - (endY - startY)*2,//(int) ((sy + sy2 + (y*32))/8),
										4, 4);
								int rgb = (lightR[x][y] + lightG[x][y] + lightB[x][y])/3;
								if(opendMiniMap[x][y] < rgb) {
									if(rgb > 255) {
										opendMiniMap[x][y]  = 255;
									}else {
										opendMiniMap[x][y] = rgb;
									}
								}
								if(!viewMode) {
									mg.setColor(new Color(0,0,0,255-opendMiniMap[x][y]));
									mg.fillRect(
									dX + minimap.getWidth()/2 - (endX - startX)*2,
									dY + minimap.getHeight()/2 - (endY - startY)*2,
											4, 4);
								}
							}
						}
					}
				}
				mg.setColor(Color.WHITE);
				mg.fillRect(
						(int) ((sxsx + px)/8) + minimap.getWidth()/2 + 1 - (endX - startX)*2,
						(int) ((sysy + py)/8) + minimap.getHeight()/2 + 1 - (endY - startY)*2,
						3, 3);
				mg.drawRect(
						(int) ((sxsx)/8) + minimap.getWidth()/2 + 1 - (endX - startX)*2,
						(int) ((sysy)/8) + minimap.getHeight()/2 + 1 - (endY - startY)*2,
						w*4,h*4);
				mg.dispose();
			}

			for (int x = startX; x < endX; x++) {
				for (int y = startY; y < endY; y++) {

					AllL(x,y,1.05);

					final double xDm = (px/32)-x;
					final double yDm = (py/32)-y;
					final double dm = (int) Math.sqrt((xDm*xDm) + (yDm*yDm));

					if(dm < 1 && !(type[x][y] == 2)) {
						lightR[x][y] = 1000 - boosetsTime[0]*10 - boosetsTime[1]*10;
						lightG[x][y] = 1000 - boosetsTime[1]*10;
						lightB[x][y] = 1000 - boosetsTime[0]*10;
						if(lightR[x][y] < 0)
							lightR[x][y] = 0;
					}

					int red = lightR[x][y];
					int green = lightG[x][y];
					int blue = lightB[x][y];

					if(red > 255)red = 255;
					if(green > 255)green = 255;
					if(blue > 255)blue = 255;

					int alfa = 255 - ((red + green + blue)/3);
					if(alfa < 100) {
						alfa = 100;
					}
					if(viewMode) {
						alfa = 0;
					}
					if(!fullMap) {
						final int newX = (int) (sxsx + (x*32));
						final int newY = (int) (sysy + (y*32));
						g.drawImage(textures[type[x][y]],
								newX,
								newY,
								null);
						g.setColor(new Color(red, green, blue, alfa));
						g.fillRect(
								newX,
								newY,
								32, 32);
						if(upper)
							g.drawImage(upperTextures[type[x][y]],
									newX,
									newY,
									null);
					}
				}
			}

			g.setColor(Color.WHITE);
			g.drawRect(
					(int) (px + sxsx),
					(int) (py + sysy),
					(int) ((pw*32)/32), (int) ((ph*32)/32));

			if(fullMap) {
				g.drawImage(minimap, 3, 3, null);
			}else {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Dialog", Font.PLAIN, 12));
				
				final int txtX = (miniMap) ? iv.getWidth()-minimap.getWidth()-25 : iv.getWidth()-15;
				if(miniMap) {
					g.setColor(Color.WHITE);
					g.drawRect(iv.getWidth()-16-minimap.getWidth(), 14, minimap.getWidth()+1, minimap.getHeight()+1);
					g.drawImage(minimap, iv.getWidth()-15-minimap.getWidth(), 15, null);
				}
				
				String textToScreen = "Dark Cave V1.12.6.18 | Optimization update";
				g.drawString("FPS: " + fps, txtX + (fps.length()+4)*-7, 40);
				String xx = Math.abs(Math.round((px-w*16)/32)) + "m";
				xx = (px/32 > w/2) ? "Right: " + xx : "Left: " + xx;
				final String xy = "Depth: " + Math.round(py/32) + "m " + xx; 
				g.drawString(textToScreen, txtX + (textToScreen.length()-8)*-7, 25);
				g.drawString(xy,  txtX + (xy.length()-3)*-7, 55);
				g.drawString("Game by Agzam4", txtX + (14)*-7, 70);
				g.drawString("Music made in BeepBox.co", (int) (txtX + (21.5)*-7)+2, 85);
				
				g.setFont(new Font("Dialog", Font.PLAIN, 25));
				g.drawImage(upperTextures[6], 5, 5, null);
				g.setColor(new Color(255, 223, 50));
				g.drawString(collectedGold + "/" + MaxGold, 35, 31);
			}
			
			if(!(collectedGold < MaxGold)) {
				game = false;
			}

			if(msgTime > 0) {
				msgTime--;
				g.setColor(new Color(255,50,40));
				g.setFont(new Font("Dialog", Font.BOLD, 48));
				g.drawString("Don't move!", main.getWidth()/2 -140, main.getHeight()/2);
			}
			
			// DEBUG 
//			g.drawLine(main.getWidth()/2, 0, main.getWidth()/2, main.getHeight());
//			g.drawLine(0, main.getHeight()/2, main.getWidth(), main.getHeight()/2);
			
			g.dispose();
			iv.repaint();
			
			px += goX;
			
//			try { 
				if(GetTile(px, py) || GetTile(px+pw, py+ph) || GetTile(px, py+ph) || GetTile(px+pw, py)) {
					px -= goX/2;
					if(GetTile(px, py) || GetTile(px+pw, py+ph) || GetTile(px, py+ph) || GetTile(px+pw, py)) {
						px -= goX/2;
						goX = 0;
					}
				}
//			} catch (ArrayIndexOutOfBoundsException e) {
//				px -= goX;
//				goX = 0;
//			}

			if(goY < -32) {
				py -= 32;
			}else if(goY > 32) {
				py += 32;
			}else {
				py += goY;
			}
//			try {
				if(GetTile(px, py) || GetTile(px+pw, py+ph) || GetTile(px, py+ph) || GetTile(px+pw, py)) {
					if(goY < -32)goY = -32;
					if(goY > 32)goY = 32;
					py -= goY/2;
					if(GetTile(px, py) || GetTile(px+pw, py+ph) || GetTile(px, py+ph) || GetTile(px+pw, py)) {
						if(boosetsTime[1] > 25 && type[(int)(px/32)][(int)(py/32)] == 1) {
							type[(int)(px/32)][(int)(py/32)] = 0;
							boosetsTime[1] -= 25;
						}
						py -= goY/2;
						goY = 0;
					}
				}
//			} catch (ArrayIndexOutOfBoundsException e) {
//				py -= goY;
//				goY = 0;
//			}

			goX = goX * 0.8;
			goY = goY + 2.5;
			
			if(right) {
				goX += 5;
				if(boosetsTime[0] > 0) {
					goX += boosetsTime[0]/10;
				}
			}
			if(left) {
				goX -= 5;
				if(boosetsTime[0] > 0) {
					goX -= boosetsTime[0]/10;
				}
			}
			if(up) {
				if(isTypeTile(px+pw, py+ph+2, 8) || isTypeTile(px, py+ph+2, 8)) {
					goY = -ph*5;
				}else if(GetTile(px+pw, py+ph+2) || GetTile(px, py+ph+2)) {
					goY = -ph;
					if(boosetsTime[0] > 0) {
						goY -= boosetsTime[0]/10;
					}
					if(boosetsTime[1] > 1) {
						goY -= boosetsTime[1]/5;
					}
				}
			}
			if(px < 0) {
				px = 0;
			}
			if(py < 0) {
				py = 0;
			}

			sx = (sx + px)*0.9 - px;
			sy = (sy + py)*0.9 - py;

			if(isTypeTile(px+pw, py + ph -10, 5) || isTypeTile(px+pw, py, 5)
					|| isTypeTile(px, py + ph -10, 5) || isTypeTile(px, py, 5)) {
				game = false;
			}

			if(isTypeTile(px+pw, py + ph, 9) || isTypeTile(px+pw, py, 9)
					|| isTypeTile(px, py + ph, 9) || isTypeTile(px, py, 9)) {
				boosetsTime[0] += 5;
				if(boosetsTime[0] > 100) {
					boosetsTime[0] = 100;
				}
			}
			if(isTypeTile(px+pw, py + ph, 10) || isTypeTile(px+pw, py, 10)
					|| isTypeTile(px, py + ph, 10) || isTypeTile(px, py, 10)) {
				boosetsTime[1] += 5;
				if(boosetsTime[1] > 100) {
					boosetsTime[1] = 100;
				}
			}
			
			for (int i = 0; i < boosetsTime.length; i++) {
				if(boosetsTime[i] > 0)
				boosetsTime[i]--;
			}

			if(kr) {
				if(Math.round(goY/10) == 0 && Math.round(goX/10) == 0) {
					px = w*16;
					py = h*16 - ph/2;
					msgTime = 0;
				}else {
					msgTime = 15;
				}
				kr = false;
			}
			
			collectTile(px+pw, py + ph, 6);
			collectTile(px, py + ph, 6);
			collectTile(px+pw, py, 6);
			collectTile(px, py, 6);

			if((System.nanoTime()-start) > 1000000000) {
				start = System.nanoTime();
				fps =  fpsint + "/" + 1000/timing;
				fpsint = 0;
			}
			fpsint++;

			try {
				Thread.sleep(timing);
			} catch (InterruptedException e) {
			}
		}
		
		sounds.Stop();
		
		menuSounds = new Sounds();
		menuSounds.Play(Sounds.BG_MUSIC_2);
		
		if(ismouseTexture) {
			iv.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					mouseTexture,
					new Point(0, 0), "blank"));
		}else {
			iv.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		goldtotal += collectedGold;
		

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		md.update((goldtotal + "").getBytes(StandardCharsets.UTF_8));
		byte[] digest = md.digest();
		String hex = String.format("%064x", new BigInteger(1, digest));
		File_ file = new File_();
		try {
			file.WriteFile(System.getProperty("user.dir") + "\\data\\data1.data", goldtotal + "");
			file.WriteFile(System.getProperty("user.dir") + "\\data\\data2.data", hex);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Saving err");
		}
	}

	public void AllL(int x, int y, double v) {
		Logic(x, y, lightR, v);
		Logic(x, y, lightG, v);
		Logic(x, y, lightB, v);
		Lamps(x, y);
	}
	private void Logic(int x, int y, int[][] light, double value) {

		// TODO: Logic

		int delit = 1;
		int plus = light[x][y];

		int typexy = type[x][y];
		boolean lt = true;

		if(typexy == 19) {
		}else {
			if(lt) {
				if(x + 1 < w) {
					if(!isWalls[type[x+1][y]]) {
						plus = plus + light[x+1][y];
						delit++;
					}
				}
				if(x - 1 > 0) {
					if(!isWalls[type[x-1][y]]) {
						plus = plus + light[x-1][y];
						delit++;
					}
				}
				if(y + 1 < h) {
					if(!isWalls[type[x][y+1]]) {
						plus = plus + light[x][y+1];
						delit++;
					}
				}
				if(y - 1 > 0) {
					if(!isWalls[type[x][y-1]]) {
						plus = plus + light[x][y-1];
						delit++;
					}
				}
			}
			light[x][y] = plus / delit;
			light[x][y] = (int) (light[x][y] / 1.05);
			if(typexy == 20) {
				light[x][y] = (int) (light[x][y]/2);//1.05
			}
		}
		if(light[x][y] > 255) {
			light[x][y] = 255;
		}
		if(light[x][y] < 0) {
			light[x][y] = 0;
		}
	}
	
	private boolean GetTile(double px2, double py2) {
		int nx = (int) (px2/32);
		int ny = (int) (py2/32);
		if(nx > -1 && nx < w && ny > -1 && ny < w)
			return isWalls[type[nx][ny]];
		return true;
	}
		
	private boolean isTypeTile(double px2, double py2, int type2) {
		int nx = (int) (px2/32);
		int ny = (int) (py2/32);
		if(nx > -1 && nx < w && ny > -1 && ny < w)
			return type[nx][ny] == type2;
		return false;
	}
	
	private void collectTile(double px2, double py2, int type2) {
		int x = (int) (px2/32);
		int y = (int) (py2/32);
		if(x > -1 && x < w && y > -1 && y < w) {
			if(type[x][y] == type2) {
				type[x][y] = 0;
				collectedGold++;
			}
		}
	}

	private void Lamps(int x, int y) {
		if(type[x][y] == 2) {
			lightR[x][y] = 255;
			lightG[x][y] = 255;
			lightB[x][y] = 255;
		}
		if(type[x][y] == 3) {
			if(lightR[x][y] < 25)
			lightR[x][y] = 25;
			if(lightG[x][y] < 125)
			lightG[x][y] = 125;
			if(lightB[x][y] < 90)
			lightB[x][y] = 90;
		}
		if(type[x][y] == 4) {
			if(lightR[x][y] < 75)
			lightR[x][y] = 75;
			if(lightG[x][y] < 45)
			lightG[x][y] = 45;
			if(lightB[x][y] < 114)
			lightB[x][y] = 114;
		}
		if(type[x][y] == 5) {
			lightR[x][y] = 500;
			lightG[x][y] = 360;
		}
		if(type[x][y] == 6) {
			if(lightR[x][y] < 40)
			lightR[x][y] = 40;
			if(lightG[x][y] < 50)
			lightG[x][y] = 50;
		}
		if(type[x][y] == 7) {
			if(lightG[x][y] < 254)
			lightG[x][y] = (int) (lightG[x][y]*1.75);
		}
		if(type[x][y] == 8) {
			if(lightG[x][y] < 4)
			lightG[x][y] = 4;
			if(lightB[x][y] < 2)
			lightB[x][y] = 2;
		}
		if(type[x][y] == 9) {
			if(lightG[x][y] < 90)
				lightG[x][y] = 90;
		}
		if(type[x][y] == 10) {
			if(lightG[x][y] < 90)
			lightG[x][y] = 90;
			if(lightB[x][y] < 125)
			lightB[x][y] = 125;
		}
		if(type[x][y] == 11) {
			lightR[x][y] = 180;
			lightB[x][y] = 255;
		}
		if(type[x][y] == 12) {
			lightR[x][y] = 255;
			lightB[x][y] = 255;
		}
	}
}
