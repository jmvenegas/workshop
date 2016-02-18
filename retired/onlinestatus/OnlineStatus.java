import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

public class OnlineStatus {

	private static JLabel status;
	private static JFrame jFrame;
	private static boolean online = false;
	private static int pingInterval = 3000; // milliseconds
	private static Color transparent = new Color(0, 0, 0, 0);

	public static void main(String[] args) {
		/**
		 * Graphics
		 */
		String statusText = new String("offline");
		Font statusFont = new Font("Monospaced", Font.BOLD, 42);

		jFrame = new JFrame();
		status = new JLabel(statusText);

		JPanel jPanel = new JPanel();
		JLabel leftStatusBar = new JLabel("[");
		JLabel rightStatusBar = new JLabel("]");

		status.setFont(statusFont);
		status.setForeground(Color.RED);
		status.setBackground(transparent);

		leftStatusBar.setFont(statusFont);
		leftStatusBar.setForeground(Color.BLACK);
		leftStatusBar.setBackground(transparent);

		rightStatusBar.setFont(statusFont);
		rightStatusBar.setForeground(Color.BLACK);
		rightStatusBar.setBackground(transparent);

		jPanel.add(leftStatusBar);
		jPanel.add(status);
		jPanel.add(rightStatusBar);
		jPanel.setBackground(transparent);

		jFrame.add(jPanel);
		jFrame.setSize(300, 100);
		jFrame.setUndecorated(true);
		jFrame.setBackground(new Color(0, 0, 0, 0));
		jFrame.setAlwaysOnTop(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);

		/**
		 * Threads
		 */
		Thread blinkThread = new Thread() {
			public void run() {
				while (true) {
					synchronized (status) {
						status.setForeground(transparent);
					}

					synchronized (jFrame) {
						jFrame.repaint();
					}
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// e.printStackTrace();
					}
					if (online) {
						synchronized (status) {
							status.setForeground(Color.GREEN);
						}
					} else {
						synchronized (status) {
							status.setForeground(Color.RED);
						}
					}
					synchronized (jFrame) {
						jFrame.repaint();
					}
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// e.printStackTrace();
					}
				}
			}
		};

		blinkThread.start();

		/**
		 * Runtime
		 */
		while (true) {
			int connectionCode = 0;
			try {
				URL outsideSite = new URL("http://google.com");
				HttpURLConnection connection = (HttpURLConnection) outsideSite
						.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				connectionCode = connection.getResponseCode();
			} catch (MalformedURLException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			}

			if (connectionCode != 200) {
				online = false;
				synchronized (status) {
					status.setText("offline");
					status.setForeground(Color.RED);
				}
			} else {
				online = true;
				synchronized (status) {
					status.setText("online");
					status.setForeground(Color.GREEN);
				}
			}
			synchronized (jFrame) {
				jFrame.repaint();
			}
			try {
				Thread.sleep(pingInterval);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
		}

	}
}
