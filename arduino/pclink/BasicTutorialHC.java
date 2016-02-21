
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 *	Heavily commented class for Arduino serial communication tutorial
 *
 */
public class BasicTutorialHC implements SerialPortEventListener{ // Implement 
	private BufferedReader sInput; // Reads from an input stream
	private OutputStream sOutput; // Used for writing out to arduino (not used right now)
	private SerialPort serialPort; // Similar to a socket. Allows for ardruino-PC exchange.
	private static final String PORTS[] = {
			"/dev/tty.usbmodem", // Port on Mac OS X
			"COM3" // Port for Windows
	};
	private static final int TIME_OUT = 2000; // 2 seconds
	private static final int DATA_RATE = 9600; // Arduino communication rate
	
	private void initialize() { // Method we will call to setup serial connection
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers(); // For enumerating through 'ports' on computer
		
		while (portEnum.hasMoreElements() && portId == null) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORTS) {
				if (currPortId.getName().contains(portName)) { // If it detects a valid arduino com port...
					System.out.println("Arduino found on port: " + currPortId.getName());
					portId = currPortId; // Set port object to use with serial object later.
				}
			}
		}
		
		if (portId == null) { // If no arduino com port was found. Is your arduino plugged in?
			System.out.println("Error: No COM port found");
			return;
		}
		
		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT); // Attempt to open the com port
			serialPort.setSerialPortParams(DATA_RATE, serialPort.DATABITS_8,
					serialPort.STOPBITS_1, serialPort.PARITY_NONE); // Set comm specs for the arduino-pc port we created
			sInput = new BufferedReader(new InputStreamReader(serialPort.getInputStream())); // Set our buffered reader to read from the serial comm port.
			sOutput = serialPort.getOutputStream();
			serialPort.addEventListener(this); // Set the class to to respond to an event when serialPort gets data
			serialPort.notifyOnDataAvailable(true); // This is the 'event' we are looking for! (when data is sent down the pipe)
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void closeCommPort() { // Shut down the port when we are done
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	
	@Override
	public void serialEvent(SerialPortEvent event) { // Required method for implementing the event listener
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String receivedInput = sInput.readLine(); // Read buffered input into a string to print
				System.out.println("DATA INCM: " + receivedInput);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		BasicTutorialHC tut = new BasicTutorialHC();
		tut.initialize(); // Make sure we set up our serial port connection
		System.out.println("Listening for arduino...");
		while(true) {
			// Infinitely listen for Arduino data
		}
		
	}
}
