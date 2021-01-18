import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Generator {

	/**
	 * @author Agzam4
	 * --------{Info}--------
	 *  In this class,
	 *  the map is generated
	 * ----------------------
	 */
	
//	private Render render = new Render();
//	private boolean[] isWalls = render.isWalls;

	private int maxValue = 0;
	private int value = 0;
//	private String seed = "";
	
//	private char[] cs = getCodes();

//	private char[]  getCodes() {
//		char[] cs = new char[100];//43
//		for (int i = 0; i < cs.length; i++) {
//			cs[i] = (char) (i+48);
//			System.out.println(cs[i]);
//		}
//		return cs;
//	}
	
	public int[][] Generate(int w, int h, BufferedImage main, BufferedImage bg, JLabel iv) {
		
		value = 0;
		maxValue = w*4;//(w + h)/2

//		double ss = Math.random()*Double.MAX_VALUE;
//		double seed =  Double.MAX_VALUE/ss;
		
		Graphics2D g = (Graphics2D) main.getGraphics();
		g.setStroke(new BasicStroke(4));
		g.drawImage(bg, 0, 0, main.getWidth(), main.getHeight(), null);
		
		int[][] light = new int[w][h];
		int[][] type = new int[w][h];

		int todo = 0; // TODO;
		
		for (int x = 0; x < w; x++) {
			DrawwProgress(g, iv, main.getWidth(), main.getHeight());
			value++;
			for (int y = 0; y < h; y++) {
//				byte r = (byte);
//				seedStart += r*xE;
//				xE = xE*10;
//				if(seedStart > 9) {
//					System.out.println("S: " + seedStart);
//					seed += cs[seedStart];
//					seedStart = 0;
//					xE = 1;
//				}
				if(1 == (int)(Math.random()*5)) { // *5
					type[x][y] = 2;
				}else {
					type[x][y] = 0;
				}
				light[x][y] = 0;
			}
		}
//		System.out.println("Seed\n" + seed);
		
		type[w/2][h/2] = 2;
		for (int i = 0; i < 5; i++) { // 5
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					Logic(x, y, light, type, w, h);
				}
			}
		}

		for (int x = 0; x < w; x++) {
			DrawwProgress(g, iv, main.getWidth(), main.getHeight());
			value++;
			for (int y = 0; y < h; y++) {
					if(light[x][y] > 25) {
						type[x][y] = 0;
//						System.out.print(" ");
					}else {
						type[x][y] = 1;
//						System.out.print("#");
					}
					light[x][y] = 0;
					
//				System.out.print(light[x][y] + " ");
			}
		}
		for (int x = 0; x < w; x++) {
			DrawwProgress(g, iv, main.getWidth(), main.getHeight());
			value++;
			for (int y = 0; y < h; y++) {
				try {
				if(type[x][y+1] == 1 && type[x+1][y] == 1 && type[x-1][y] == 1 && type[x][y-1] == 0 && 1 == (int) (Math.random()*5)) {//light[x][y] > 5 && 
					type[x][y] = 5;
				}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				if(1 == (int) (Math.random()*7)) {
					try {
						if(type[x][y] == 0) {
							if(type[x][y+1] == 1) {
								if(1 == (int) (Math.random()*2)) { //15
									type[x][y] = (int) (9 + Math.random()*2);
								}else {
									type[x][y] = 3;
								}
							}
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
				}
				if(1 == (int) (Math.random()*15)) {
					try {
						if(type[x][y] == 1) {
							if(type[x][y-1] == 0) {
								type[x][y] = 8;
							}
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
				}
				if(1 == (int) (Math.random()*50)) {
					if(type[x][y] == 0) {
						type[x][y] = 4;
						todo++;
					}
				}
			}
		}
		
		// DIAMONDS

		ArrayList<Integer> xAirId = new ArrayList<Integer>();
		ArrayList<Integer> yAirId = new ArrayList<Integer>();
		
		for (int x = 0; x < w; x++) {
			DrawwProgress(g, iv, main.getWidth(), main.getHeight());
			value++;
			for (int y = 0; y < h; y++) {
				xAirId.add(x);
				yAirId.add(y);
				
			}
		}

		for (int i = 0; i < (w * h)/100; i++) {
			if(xAirId.size() < 1) {
				break;
			}
			int randomId = (int) (Math.random()*xAirId.size());
			type[xAirId.get(randomId)][yAirId.get(randomId)] = 6;
			xAirId.remove(randomId);
			yAirId.remove(randomId);
		}
		
		if(xAirId.size() > 0) {
			for (int i = 0; i < 3; i++) {
				try {
					int randomId = (int) (Math.random()*xAirId.size());
					type[xAirId.get(randomId)][yAirId.get(randomId)] = 7;
					xAirId.remove(randomId);
					yAirId.remove(randomId);
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}
		System.out.println(todo + "/" + w*h);
		return type;
	}
	
	private void DrawwProgress(Graphics2D g, JLabel iv, int w, int h) {
		g.setColor(Color.GRAY);
		g.fillRect(w/5, h/2, (w/5)*3, 25); 
		g.setColor(Color.GREEN);
		g.fillRect(w/5, h/2, ((w/5)*3*value)/maxValue, 25);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(w/5, h/2, (w/5)*3, 25);
		iv.repaint();
	}
	
	private void Logic(int x, int y, int[][] light, int[][] type, int w, int h) {

			if(type[x][y] == 2) {
				light[x][y] = 255;
			}
			
			int delit = 1;
	    	int plus = light[x][y];

	    	if(x + 1 < w) {
	    		plus = plus + light[x+1][y];
	    		delit++;
	    	}
	    	if(x - 1 > 0) {
	    		plus = plus + light[x-1][y];
	    		delit++;
	    	}
	    	if(y + 1 < h) {
	    		plus = plus + light[x][y+1];
	    		delit++;
	    	}
	    	if(y - 1 > 0) {
	    		plus = plus + light[x][y-1];
	    		delit++;
	    	}
	    	
	    	light[x][y] = plus / delit;
	    	light[x][y] = (int) (light[x][y]/1.05);//1.05 // /1.05
	    	
	    	if(light[x][y] > 255) {
	    		light[x][y] = 255;
	    	}
	    	if(light[x][y] < 0) {
	    		light[x][y] = 0;
	    	}
		}
}
