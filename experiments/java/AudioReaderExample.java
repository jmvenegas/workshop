import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class AudioReaderExample {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Starting data..");
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		TargetDataLine line = null;
		DataLine.Info info = new DataLine.Info(TargetDataLine.class,
		                format);
		if (!AudioSystem.isLineSupported(info)) {
		    throw new Exception("AudioSystem data line unsupported");
		}

		try {
		    line = (TargetDataLine) AudioSystem.getLine(info);
		    System.out.println("data line: "+ line.toString());
		    line.open(format);
		} catch (LineUnavailableException ex) {
		    ex.printStackTrace();
		}

		ByteArrayOutputStream out  = new ByteArrayOutputStream();
		int numBytesRead;
		byte[] data = new byte[line.getBufferSize() / 5];

		line.start();
		
		while (true) { // Program must be terminated manually
		    numBytesRead =  line.read(data, 0, data.length);
		    out.write(data, 0, numBytesRead);
		    System.out.println("DATA: "+Arrays.toString(data)); // Visual representation of data capture from audio device
		}

	}
}
