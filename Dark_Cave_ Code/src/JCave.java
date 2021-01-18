import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Toolkit;

public class JCave extends JFrame {

	/**
	 * @author Agzam4
	 * --------{Info}--------
	 *  Main class (swing)
	 * ----------------------
	 */
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JCave frame = new JCave();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JCave() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\textures\\up\\6.png"));
		setTitle("BY AGZAM4");
		setUndecorated(true);
		setOpacity(1f);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel iv = new JLabel();
		contentPane.add(iv, BorderLayout.CENTER);
		
		File_ file = new File_();
		
		String[] size = new String[] {"50", "50"};
		try {
			 size = file.ReadFile(System.getProperty("user.dir") + "\\data\\MapSize.txt").split("x");
		} catch (IOException e) {
			try {
				file.WriteFile(System.getProperty("user.dir") + "\\data\\MapSize.txt", "50x50");
			} catch (IOException e1) {
			}
		}

		try {
			 file.ReadFile(System.getProperty("user.dir") + "\\data\\MiniMap.txt");
		} catch (IOException e) {
			try {
				file.WriteFile(System.getProperty("user.dir") + "\\data\\MiniMap.txt", "" + (char)(1));
			} catch (IOException e1) {
			}
		}
		
		//MiniMap
		int w = 50;
		try {
			w =	Integer.valueOf(size[0]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
		}
		int h = 50;
		try {
			h = Integer.valueOf(size[1]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
		}
		
		int gold = 0;
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try {
			String value = file.ReadFile(System.getProperty("user.dir") + "\\data\\data1.data");
			md.update(value.getBytes(StandardCharsets.UTF_8));
			String code = file.ReadFile(System.getProperty("user.dir") + "\\data\\data2.data");
			byte[] digest = md.digest();
			String hex = String.format("%064x", new BigInteger(1, digest));
//			System.out.println(hex + "\n" + code);
			if(code.equals(hex)) {
				gold = Integer.parseInt(value);
//				System.out.println("LODAED: " + gold);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Err: " + e.getMessage());
			try {
				md.update("0".getBytes(StandardCharsets.UTF_8));
				byte[] digest = md.digest();
				String hex = String.format("%064x", new BigInteger(1, digest));
				file.WriteFile(System.getProperty("user.dir") + "\\data\\data1.data", "0");
				file.WriteFile(System.getProperty("user.dir") + "\\data\\data2.data", hex);
				gold = 0;
			} catch (IOException e1) {
			}
		}
		
		Render render = new Render();
		render.Start(new int[w][h], w, h, iv, contentPane, gold, this);
	}
}
