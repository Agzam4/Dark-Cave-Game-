import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.Font;
import javax.swing.JCheckBox;

public class JSettings extends JFrame {

	/**
	 * @author Agzam4
	 * --------{Info}--------
	 *  Game Settings (swing)
	 * ----------------------
	 */
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField w;
	private JTextField h;

	/**
	 * Launch the application.
	 */
	public void main(int w, int h, JFrame main, int q, int iw, int ih, boolean miniMap) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JSettings frame = new JSettings(h, w, main, q, iw, ih, miniMap);
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
	public JSettings(int ww, int hh, JFrame main, int q, int iw, int ih, boolean miniMap) {
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\textures\\up\\6.png"));
		setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		

		JPanel panel_0 = new JPanel();
		panel_0.setOpaque(false);
		panel.add(panel_0);
		panel_0.setLayout(new BoxLayout(panel_0, BoxLayout.X_AXIS));
		
		JLabel set = new JLabel("|     Settings     |");
		set.setFont(new Font("Tahoma", Font.PLAIN, 30));
		set.setForeground(Color.WHITE);
		panel_0.add(set);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("Map Size:");
		lblNewLabel_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel.add(panel_2);
		
		JLabel lblNewLabel = new JLabel("Wight:");
		lblNewLabel.setForeground(Color.WHITE);
		panel_2.add(lblNewLabel);
		
		w = new JTextField(ww+ "");
		w.setForeground(Color.WHITE);
		((PlainDocument) w.getDocument()).setDocumentFilter(new DigitFilter());;
		w.setBackground(Color.BLACK);
		panel_2.add(w);
		w.setColumns(10);

		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		panel.add(panel_3);
		
		JPanel panel_6 = new JPanel();
		panel_6.setOpaque(false);
		panel.add(panel_6);
		
		JLabel lblGraphics = new JLabel("Graphics:");
		lblGraphics.setForeground(Color.WHITE);
		panel_6.add(lblGraphics);
		
		JLabel lblY = new JLabel("Height:");
		lblY.setForeground(Color.WHITE);
		panel_3.add(lblY);
		
		h = new JTextField(hh+ "");
		SetJTextField(h);
		SetJTextField(w);
		h.setColumns(10);
		panel_3.add(h);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.BLACK);
		panel.add(panel_7);
		
		
		JLabel lblOpacity = new JLabel("Opacity:");
		lblOpacity.setForeground(Color.WHITE);
		panel_7.add(lblOpacity);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.BLACK);
		panel.add(panel_5);
		
//		JLabel qualityJLabel = new JLabel("Quality:");
//		qualityJLabel.setForeground(Color.WHITE);
//		panel_5.add(qualityJLabel);
		
		JPanel panel_4 = new JPanel();
		panel_4.setOpaque(false);
		contentPane.add(panel_4, BorderLayout.SOUTH);
		
		JButton save = new JButton("Save");
		save.setForeground(new Color(200,200,200));
		panel_4.add(save);
		
		JButton close = new JButton("Close");
		close.setForeground(new Color(200,200,200));
		panel_4.add(close);
		
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//				System.exit(JFrame.DISPOSE_ON_CLOSE);
//				main.setVisible(true);
				Exit();
			}
		});
		
		WindowsButtons windowsButtons = new WindowsButtons();
		windowsButtons.ConvertToWindowsButton(close, new Color(65,65,65), new Color(25,25,25), new Color(100,100,100));
		windowsButtons.ConvertToWindowsButton(save, new Color(65,65,65), new Color(25,25,25), new Color(100,100,100));


		JCheckBox minimap = new JCheckBox(" Show Minimap");
		minimap.setOpaque(false);
		minimap.setSelected(miniMap);
		minimap.setForeground(Color.WHITE);
		panel_5.add(minimap);
		
		
		JSlider setOpacity = new JSlider();
		try {
			setOpacity.setValue((int) (main.getOpacity()*100));
		} catch (NullPointerException e) {
			setOpacity.setValue(100);
		}
		setOpacity.setBackground(Color.BLACK);

		setOpacity.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblOpacity.setText("Opacity (" + setOpacity.getValue() +"%)");
			}
		});
		panel_7.add(setOpacity);
		

//		JSlider quality = new JSlider();
//		quality.setBackground(Color.BLACK);
//		quality.setMaximum(32);
//		quality.setMinimum(1);
//		panel_5.add(quality);
//
//		quality.addChangeListener(new ChangeListener() {
//			
//			@Override
//			public void stateChanged(ChangeEvent e) {
//				qualityJLabel.setText("Quality: (" + quality.getValue()*iw/32 +
//						"x" + quality.getValue()*ih/32 + ")");
//			}
//		});
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File_ file = new File_();
				try {
					file.WriteFile(System.getProperty("user.dir") + "\\data\\MapSize.txt", w.getText() + "x" + h.getText());
					int mm = 0;
					if(minimap.isSelected())
						mm = 1;
					file.WriteFile(System.getProperty("user.dir") + "\\data\\MiniMap.txt", "" + (char)(mm));
//					file.WriteFile(System.getProperty("user.dir") + "\\data\\Quality.txt", quality.getValue() + "");
//					main.setVisible(true);
					main.setOpacity(setOpacity.getValue() * 0.01f);
					Exit();
				} catch (IOException e1) {
				}
			}
		});
		
		JTf(h);
		JTf(w);
//		main.setVisible(false);
//		main.setOpacity(75 * 0.01f);
	}
	
	private void JTf(JTextField t) {
		t.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(Integer.valueOf(t.getText()) < 15)
					t.setText("15");
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
	}
	
	private void Exit() {
//		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		dispose();
	}
	
	private void SetJTextField(JTextField t) {
		((PlainDocument) t.getDocument()).setDocumentFilter(new DigitFilter());;
		t.setForeground(Color.WHITE);
		t.setBackground(Color.BLACK);
		
		t.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				try {
					int v = Integer.valueOf(t.getText());
					if(v < 3)t.setText("3");
					if(v > 100000)t.setText("100000");
				} catch (NumberFormatException e) {
					t.setText("50");
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				try {
					int v = Integer.valueOf(t.getText());
					if(v < 3)t.setText("3");
					if(v > 100000)t.setText("100000");
				} catch (NumberFormatException e) {
					t.setText("50");
				}
			}
		});
	}
	
	class DigitFilter extends DocumentFilter {
	    private static final String DIGITS = "\\d+";

	    @Override
	    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
	    	if (string.matches(DIGITS)) {
	    		super.insertString(fb, offset, string, attr);
	    	}
	    }
	    
	    @Override
	    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
	    	if (string.matches(DIGITS)) {
	    		super.replace(fb, offset, length, string, attrs);
	    	}
	    }
	}
}
