import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sounds {

	/**
	 * @author Agzam4
	 * --------{Info}--------
	 *  Music player class
	 * ----------------------
	 */
	
	public static final int BG_MUSIC_1 = 0;
	public static final int BG_MUSIC_2 = 1;
	
	AudioInputStream[] audioip = InitAIS();

	private AudioInputStream[] InitAIS() {
		AudioInputStream[] audioip = new AudioInputStream[2];
		for (int i = 0; i < audioip.length; i++) {
			try {
				audioip[i] = AudioSystem.getAudioInputStream(
						new File(System.getProperty("user.dir") + "\\sounds\\M" + i + ".wav").getAbsoluteFile());
			} catch (UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			}
		}
		return audioip;
	}

	private Clip clip = getClip();
			
	private Clip getClip() {
		try {
			return AudioSystem.getClip();
		} catch (LineUnavailableException e) {
		}
		return null;
	}
	
	public void Play(int id) {
		try {
			try {
				clip.open(audioip[id]);
				clip.loop(-1);
			    clip.start();
			} catch (IOException e) {
			}
		} catch (LineUnavailableException e1) {
		}
	}	
	
	public void Stop() {
		clip.stop();
		clip.close();
	}
}
