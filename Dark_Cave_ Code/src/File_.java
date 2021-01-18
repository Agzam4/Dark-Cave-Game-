
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class File_ {
	
	/**
	 * @author Agzam4
	 * --------{Info}--------
	 *  File work class
	 * ----------------------
	 */
	
	public String ReadFile(String name) throws IOException {
		String string = "";
		byte[] all = Files.readAllBytes(Paths.get(name));
		string = new String(all);
		return string;
	}
	
	public void WriteFile(String filename, String text) throws IOException {
		
		try (FileWriter writer = new FileWriter(new File(filename))) {
			writer.write(text);
			writer.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void SaveImage(String p, BufferedImage bufferedImage2) throws IOException {
		BufferedImage bufferedImage = bufferedImage2;
		byte[] currentImage = null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);
			baos.flush();
			currentImage = baos.toByteArray();
			baos.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}   
		
		FileOutputStream newFile = new FileOutputStream(p);
				newFile.write(currentImage);
				newFile.close();
	}
	
}
