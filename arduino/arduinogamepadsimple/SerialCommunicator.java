import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
 * @brief - SerialCommunicator.java handles the serial communication between
 *        java application and non-HID arduino
 */
public class SerialCommunicator implements SerialPortEventListener {
	private BufferedReader bufferedInput;
	private OutputStream outputStream;
	private SerialPort serialPort;
	private static final String PORTS[] = { "/dev/tty.usbmodem", "COM3" };
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;
	private int currentLeftClickState = 0;
	private int currentRightClickState = 0;

	private void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		while (portEnum.hasMoreElements() && portId == null) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORTS) {
				if (currPortId.getName().contains(portName)) {
					System.out.println("Arduino found on port: " + currPortId.getName());
					portId = currPortId;
				}
			}
		}
		// Unable to find a port
		if (portId == null) {
			System.out.println("Error: No COM port found");
			this.programExit();
		}
		try {
			this.serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
			this.serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			this.bufferedInput = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			this.outputStream = this.serialPort.getOutputStream();
			this.serialPort.addEventListener(this);
			this.serialPort.notifyOnDataAvailable(true);
		} catch (PortInUseException e) {
			e.printStackTrace();
			this.programExit();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
			this.programExit();
		} catch (IOException e) {
			e.printStackTrace();
			this.programExit();
		} catch (TooManyListenersException e) {
			e.printStackTrace();
			this.programExit();
		}
	}
	
	public void programExit() {
		this.closeCommPort();
		System.exit(1);	
	}

	public synchronized void closeCommPort() {
		if (this.serialPort != null) {
			this.serialPort.removeEventListener();
			this.serialPort.close();
		}
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String receivedInput = this.bufferedInput.readLine();
				this.sendKeyEvent(receivedInput);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendKeyEvent(String signal) {
		// Parse Input
		String[] gamepadArgs = signal.split(":");
		int xAxis = Integer.parseInt(gamepadArgs[0]);
		int yAxis = Integer.parseInt(gamepadArgs[1]);
		int trigger = Integer.parseInt(gamepadArgs[2]);
		int thumb = Integer.parseInt(gamepadArgs[3]);
		
		Robot robot = null;
		int scrollSpeedUp = 2;
		int scrollSpeedDown = -2;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		if (robot == null){
			// Improve redundancy of fail case
			this.programExit();
		}
		
		// Buttons
		if(trigger == 0 && currentLeftClickState == 1){
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			this.currentLeftClickState = 0;
		}
		else if(trigger == 1 && currentLeftClickState == 0){
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			this.currentLeftClickState = 1;
		}
		
		if(thumb == 0 && currentRightClickState == 1){
			robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			this.currentRightClickState = 0;
		}
		else if(thumb == 1 && currentRightClickState == 0){
			robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			this.currentRightClickState = 1;
		}
		
		// Movement
		if (xAxis < 100) {
			this.moveLeft(robot, xAxis);
		}
		else if (xAxis > 130) {
			this.moveRight(robot, xAxis);
		}
		
		if (yAxis < 100) {
			this.moveUp(robot, yAxis);
		}
		else if (yAxis > 130) {
			this.moveDown(robot, yAxis);
		}
	}
	
	// Refactor - clean up, make custom bot
	public void moveLeft(Robot robot, int movement) {
		int currentX = MouseInfo.getPointerInfo().getLocation().x;
		int newX = 100 - movement;
		robot.mouseMove(currentX - newX, MouseInfo.getPointerInfo().getLocation().y);
	}
	
	public void moveRight(Robot robot, int movement) {
		int currentX = MouseInfo.getPointerInfo().getLocation().x;
		int newX =  movement - 130;
		robot.mouseMove(currentX + newX, MouseInfo.getPointerInfo().getLocation().y);
	}
	
	public void moveUp(Robot robot, int movement) {
		int currentY = MouseInfo.getPointerInfo().getLocation().y;
		int newY = 100 - movement;
		robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x, currentY - newY);
	}
	
	public void moveDown(Robot robot, int movement) {
		int currentY = MouseInfo.getPointerInfo().getLocation().y;
		int newY =  movement - 130;
		robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x, currentY + newY);
	}

	public static void main(String[] args) {
		boolean running = true;
		SerialCommunicator serialCommunicator = new SerialCommunicator();
		// Refactor - make proper thread, nothing changes running state
		serialCommunicator.initialize();
		System.out.println("Listening for arduino...");
		while (running) {
			try {
				// Reduce looping execution by sleeping a little
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		serialCommunicator.programExit();
	}
}
