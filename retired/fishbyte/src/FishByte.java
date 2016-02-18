/*
 * Author: Vengas, Justin
 * Date: 27 Feb. 2013
 * Program: FishByte
 * About: 
 * 		The primary function of FishByte is to calculate distances between 4 elements in a picture.
 * 		The user selects four elements in a picture(fish), FishByte logs those positions as coordinates,
 * 		then takes the mean distances between each element and its 3 complimentary elements. 
 * 
 * In progress:
 * 		-nothing at this time.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.io.*;

public class FishByte implements ActionListener, MouseListener, ItemListener {

	static JFrame frame = new JFrame("FishByte");
	static JFrame scaler = new JFrame("Scale");
	static JFrame aboutprog = new JFrame("About");
	static JPanel scalePanel = new JPanel();
	static JComboBox measure = new JComboBox();
	static JButton upload = new JButton("Upload");
	static JButton setScale = new JButton("Set Scale");
	static JButton download = new JButton("Download");
	static JButton calculatButton = new JButton("Calculate!");
	static JButton dataWipe = new JButton("Reset");
	static JButton newScale = new JButton("New Scale");
	static JButton sHelp = new JButton("Scale Help?");
	static JButton about = new JButton("About FishByte");
	static ButtonGroup btnGrp = new ButtonGroup();
	static JRadioButton scaleOn = new JRadioButton("Find Scale");
	static JRadioButton scaleOff = new JRadioButton("Finished");
	static boolean radio;
	static boolean cm = true;
	static boolean mm = false;
	static boolean nm = false;
	static ImageIcon icon = new ImageIcon();
	static JPanel picPanel = new JPanel();
	static Image img;
	static JLabel dPictureLabel = new JLabel();
	static StringBuilder bigData = new StringBuilder();
	static String dataFinal = "";
	static String userHomeFolder = System.getProperty("user.home");
	static File textFile = new File(userHomeFolder, "excelData.txt");
	static JTextArea dialogue = new JTextArea(8, 30);
	static JScrollPane spane = new JScrollPane(dialogue);
	static Writer output;
	static double grandAllMean;
	static String grandAll;
	static JTextField scaleText = new JTextField("Input Scale");
	static JTextField measureInput = new JTextField("Length?");
	static JLabel intro = new JLabel("<html>Using FishByte:" + "<br>"
			+ "Step1: Click Upload. Choose Picture." + "<br>"
			+ "Step2: \"Click\" on Fish." + "<br>"
			+ "Step3: Click \"Calculate!\"" + "<br>"
			+ "Step4: Repeat Steps 1-3 for Each Unique Image." + "<br>"
			+ "Step5: Click \"Download.\"" + "<br>"
			+ "Step6: Import \"excelData.txt\" File to Excel.");

	static JLabel scaleHelp = new JLabel(
			"<html>Using the Scale Feature:"
					+ "<br>"
					+ "<br>"
					+ "Step1: Choose \"New Scale\"."
					+ "<br>"
					+ "Step2: Enter Input Scale OR Enter a Known Length."
					+ "<br>"
					+ "Step3: Enter Length and Choose Measurement."
					+ "<br>"
					+ "Step4: Click Find Scale and Click On-Screen Scale Start and End."
					+ "<br>"
					+ "Step5: Click Finished After Receiving Confirmation.");

	static JLabel aboutme = new JLabel("<html>"
			+ "FishByte was created for the CSU Ghalambor Guppy Lab" + "<br>"
			+ "by Justin Venegas." + "<br>"
			+ "Contact: Justin.Venegas@Gmail.com");

	static double x0, x1, x2, x3, y0, y1, y2, y3;
	static double x00, x11, y00, y11;
	static double scaleDubs;
	static boolean scaleBoolean;
	static double preCalcScale;
	static double measureDubs;
	static JFrame helpPopUp = new JFrame();
	static JPanel helpPane = new JPanel();

	public static void main(String[] args) throws IOException {

		// Variables
		FishByte fbInstance = new FishByte();
		JPanel mainPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		JLabel logo = new JLabel();
		JPanel ctop, cmid, cbot, titlePane;
		JLabel prompt = new JLabel("FishByte");
		Border bevelBorder = BorderFactory.createRaisedBevelBorder();

		// Main Panel
		mainPanel.setLayout(new BorderLayout());
		frame.getContentPane().add(mainPanel);

		// Prompt
		titlePane = new JPanel();
		titlePane.setLayout(new FlowLayout());
		titlePane.add(about);
		calculatButton.addActionListener(fbInstance);
		// titlePane.add(calculatButton);
		dataWipe.addActionListener(fbInstance);
		// titlePane.add(dataWipe);
		titlePane.setBorder(bevelBorder);
		mainPanel.add(titlePane, BorderLayout.NORTH);

		// Control Panel
		controlPanel.setBorder(bevelBorder);
		controlPanel.setBackground(Color.GRAY);
		ctop = new JPanel();
		ctop.setLayout(new FlowLayout());
		ctop.add(intro);
		cmid = new JPanel();
		cmid.setLayout(new GridLayout(4, 2));
		cbot = new JPanel();
		cbot.setLayout(new FlowLayout());
		// Add Components to main c panel
		controlPanel.setLayout(new GridLayout(3, 1));
		upload.addActionListener(fbInstance);
		setScale.addActionListener(fbInstance);
		newScale.addActionListener(fbInstance);
		scaleOn.addItemListener(fbInstance);
		scaleOff.addItemListener(fbInstance);
		sHelp.addActionListener(fbInstance);
		about.addActionListener(fbInstance);

		btnGrp.add(scaleOn);
		btnGrp.add(scaleOff);

		// Tooltip text
		upload.setToolTipText("Load a picture.");
		dataWipe.setToolTipText("Resets all fish coordinate values.");
		calculatButton
				.setToolTipText("Calculate average distances between selected fish.");
		download.setToolTipText("Creates excelData in User's home folder.");
		newScale.setToolTipText("Add a scale modifier to measurements.");
		sHelp.setToolTipText("How to use the scale.");
		about.setVerticalTextPosition(JButton.CENTER);
		about.setHorizontalTextPosition(JButton.CENTER);
		cmid.add(upload);
		cmid.add(dataWipe);
		cmid.add(calculatButton);
		cmid.add(download);
		cmid.add(newScale);
		cmid.add(sHelp);
		cbot.add(spane);
		//cbot.add(about);
		download.addActionListener(fbInstance);
		controlPanel.add(ctop);
		controlPanel.add(cmid);
		controlPanel.add(cbot);
		mainPanel.add(controlPanel, BorderLayout.WEST);

		// Picture Panel
		picPanel.addMouseListener(fbInstance);
		picPanel.add(dPictureLabel);
		mainPanel.add(picPanel, BorderLayout.CENTER);

		// south border
		//mainPanel.add(about, BorderLayout.SOUTH);

		// Frame Options
		frame.pack();
		frame.setSize(1000, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set Up Scale Popup
		scalePanel.setLayout(new GridLayout(3, 2));
		measure.addItem("CM");
		measure.addItem("MM");
		measure.addItem("NM");
		measure.addActionListener(fbInstance);

		scalePanel.add(scaleText);
		scalePanel.add(setScale);

		scalePanel.add(measureInput);
		scalePanel.add(measure);
		scalePanel.add(scaleOn);
		scalePanel.add(scaleOff);
		scaler.add(scalePanel);

		// Help Popup
		helpPane.setLayout(new FlowLayout());
		helpPane.add(scaleHelp);
		helpPopUp.add(helpPane);

		// about popup
		aboutprog.setLayout(new FlowLayout());
		aboutprog.add(aboutme);

	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == upload) {
			fileChooser();
			// When picking a new file, reset the coordinates so mouse event
			// loop works correctly
			x0 = 0;
			y0 = 0;
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
			x3 = 0;
			y3 = 0;
		} else if (src == setScale) {

			scaleDubs = Double.parseDouble(scaleText.getText());
			if (scaleDubs > 0) {
				dialogue.append("\nScale has been set to: "
						+ scaleText.getText());
				scaleBoolean = true;
				scaleDubs = Double.parseDouble(scaleText.getText());
			} else if (scaleDubs <= 0)
				dialogue.append("\nThat is not a valid scale.");
		} else if (src == download) {

			// If scale is set, do, else dont.
			if (scaleBoolean == true) {
				// Print String to File
				try {
					output = new PrintWriter(new FileWriter(textFile));
					output.write(dataFinal);
					output.close();
					dialogue.append("\nFile created!");
				} catch (IOException e0) {
					e0.printStackTrace();
				}
			} else
				dialogue.append("\nSet Scale Before Downloading");
		} else if (src == calculatButton) {
			average();
			if (x0 <= 0) {
				dialogue.append("\nNo Data Points to Calculate.");

			} else {
				bigData.append("\n" + grandAll);
				dataFinal = bigData.toString();
				dialogue.append("\nData Set Calculated.");
				System.out.println(dataFinal);
			}

		} else if (src == dataWipe) {
			x0 = 0;
			y0 = 0;
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
			x3 = 0;
			y3 = 0;
			dialogue.append("\nData Points Cleared.");
		} else if (src == newScale) {

			scaler.pack();
			scaler.setVisible(true);
		}

		else if (src == measure) {
			if (measure.getSelectedIndex() == 0) {
				cm = true;
				mm = false;
				nm = false;
				dialogue.append("\nScale is in CM.");
				bigData.append("\nScale is in CM.");
			} else if (measure.getSelectedIndex() == 1) {
				mm = true;
				cm = false;
				nm = false;
				dialogue.append("\nScale is in MM.");
				bigData.append("\nScale is in MM.");
			} else if (measure.getSelectedIndex() == 2) {
				nm = true;
				cm = false;
				mm = false;
				dialogue.append("\nScale is in NM.");
				bigData.append("\nScale is in NM.");
			}
		} else if (src == sHelp) {

			helpPopUp.pack();
			helpPopUp.setVisible(true);

		} else if (src == about) {
			aboutprog.pack();
			aboutprog.setVisible(true);
		}

	}

	public void itemStateChanged(ItemEvent ie) {

		Object iesrc = ie.getSource();
		if (iesrc == scaleOn && scaleOn.isSelected()) {
			dialogue.append("\nCreating New Scale.");
			x00 = 0;
			y00 = 0;
			x11 = 0;
			y11 = 0;
			radio = true;
		} else if (iesrc == scaleOff && scaleOff.isSelected()) {
			// dialogue.append("\nPlease Input Custom Scale.");
			radio = false;
			x0 = 0;
			y0 = 0;
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
			x3 = 0;
			y3 = 0;
			scaler.setVisible(false);
		}

	}

	// Get Fish Coordinates
	public void mouseClicked(MouseEvent me) {
		if (radio == true) {
			if (x00 <= 0) {
				x00 = me.getX();
				y00 = me.getY();
				dialogue.append("\nScale Start at: " + "(" + x00 + "," + y00
						+ ")");
				// System.out.println("fish 1 at: " + "(" + x0 + "," + y0 +
				// ")");
			} else if (x11 <= 0) {

				// COMPLETE THE SCALE CALCULATION ONCE SECOND POINT IS SET!!!!
				x11 = me.getX();
				y11 = me.getY();
				dialogue.append("\nScale End 2 at: " + "(" + x11 + "," + y11
						+ ")");

				preCalcScale = Calc.distance(x00, x11, y00, y11);
				measureDubs = Double.parseDouble(measureInput.getText());
				scaleDubs = (measureDubs / preCalcScale);
				// Conditional based on measure selection box
				if (cm == true) {
					scaleDubs = (scaleDubs * 1);
					scaleBoolean = true;
				} else if (mm == true) {
					scaleDubs = (scaleDubs * 1);
					scaleBoolean = true;
				} else if (nm == true) {
					scaleDubs = (scaleDubs * 1);
					scaleBoolean = true;
				}
				// Reset values to click on fish
				dialogue.append("\nCustom Scale Calculated.");
				// System.out.println("fish 2 at: " + "(" + x1 + "," + y1 +
				// ")");
			}
		}
		// /was an else in front of if!!!!!!
		else if (radio == false) {
			if (x0 <= 0) {
				x0 = me.getX();
				y0 = me.getY();
				dialogue.append("\nFish 1 at: " + "(" + x0 + "," + y0 + ")");
				// System.out.println("fish 1 at: " + "(" + x0 + "," + y0 +
				// ")");
			} else if (x1 <= 0) {
				x1 = me.getX();
				y1 = me.getY();
				dialogue.append("\nFish 2 at: " + "(" + x1 + "," + y1 + ")");
				// System.out.println("fish 2 at: " + "(" + x1 + "," + y1 +
				// ")");
			} else if (x2 <= 0) {
				x2 = me.getX();
				y2 = me.getY();
				dialogue.append("\nFish 3 at: " + "(" + x2 + "," + y2 + ")");
				// System.out.println("fish 3 at: " + "(" + x2 + "," + y2 +
				// ")");
			} else if (x3 <= 0) {
				x3 = me.getX();
				y3 = me.getY();
				dialogue.append("\nFish 4 at: " + "(" + x3 + "," + y3 + ")");
				// System.out.println("fish 4 at: " + "(" + x3 + "," + y3 +
				// ")");
			}
		}

	}

	// Stubs for mouse event
	public void mousePressed(MouseEvent me) {
	}

	public void mouseReleased(MouseEvent me) {
	}

	public void mouseEntered(MouseEvent me) {
	}

	public void mouseExited(MouseEvent me) {
	}

	public void average() {
		/*
		 * PIXEL TO MM CONVERSION 1 PIXEL = 0.264583333 MM
		 */
		DecimalFormat decFmt = new DecimalFormat("0.0000");
		double fish1Avg0, fish1Avg1, fish1Avg2, gMean0;

		// Calculate fish 1
		fish1Avg0 = Calc.distance(x0, x1, y0, y1);
		fish1Avg1 = Calc.distance(x0, x2, y0, y2);
		fish1Avg2 = Calc.distance(x0, x3, y0, y3);
		gMean0 = ((fish1Avg0 + fish1Avg1 + fish1Avg2) / 3);

		double fish2Avg0, fish2Avg1, fish2Avg2, gMean1;

		// Calculate fish 2
		fish2Avg0 = Calc.distance(x1, x0, y1, y0);
		fish2Avg1 = Calc.distance(x1, x2, y1, y2);
		fish2Avg2 = Calc.distance(x1, x3, y1, y3);
		gMean1 = ((fish2Avg0 + fish2Avg1 + fish2Avg2) / 3);

		double fish3Avg0, fish3Avg1, fish3Avg2, gMean2;

		// Calculate fish 3
		fish3Avg0 = Calc.distance(x2, x0, y2, y0);
		fish3Avg1 = Calc.distance(x2, x1, y2, y1);
		fish3Avg2 = Calc.distance(x2, x3, y2, y3);
		gMean2 = ((fish3Avg0 + fish3Avg1 + fish3Avg2) / 3);

		double fish4Avg0, fish4Avg1, fish4Avg2, gMean3;

		// Calculate fish 4

		fish4Avg0 = Calc.distance(x3, x0, y3, y0);
		fish4Avg1 = Calc.distance(x3, x1, y3, y1);
		fish4Avg2 = Calc.distance(x3, x2, y3, y2);
		gMean3 = ((fish4Avg0 + fish4Avg1 + fish4Avg2) / 3);

		// Return means as string
		String fish1 = ("Fish 1's mean is: " + gMean0);
		String fish2 = ("Fish 2's mean is: " + gMean1);
		String fish3 = ("Fish 3's mean is: " + gMean2);
		String fish4 = ("Fish 4's mean is: " + gMean3);

		// The final final bits(means and strings)
		grandAllMean = ((gMean0 + gMean1 + gMean2 + gMean3) / 4);
		if (scaleBoolean == true) {
			gMean0 = (gMean0 * scaleDubs);
			gMean1 = (gMean1 * scaleDubs);
			gMean2 = (gMean2 * scaleDubs);
			gMean3 = (gMean3 * scaleDubs);
			grandAllMean = (grandAllMean * scaleDubs);
			grandAll = (decFmt.format(gMean0) + "\t" + decFmt.format(gMean1)
					+ "\t" + decFmt.format(gMean2) + "\t"
					+ decFmt.format(gMean3) + "\t" + decFmt
					.format(grandAllMean));
		} else if (scaleBoolean == false) {
			grandAll = (decFmt.format(gMean0) + "\t" + decFmt.format(gMean1)
					+ "\t" + decFmt.format(gMean2) + "\t"
					+ decFmt.format(gMean3) + "\t" + decFmt
					.format(grandAllMean));
		}
		// Calculate to set scale

		// System.out.println(grandAll);
	}

	public void fileChooser() {

		JFrame cFrame = new JFrame();
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG & GIF Images", "jpg", "gif");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(cFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			dialogue.append("\nFile Loaded Successfully.");
			try {
				img = ImageIO.read(chooser.getSelectedFile())
						.getScaledInstance(picPanel.getWidth(),
								picPanel.getHeight(), Image.SCALE_SMOOTH);
			} catch (IOException e) {
				e.printStackTrace();
			}
			icon.setImage(img);
			dPictureLabel.setIcon(icon);
			picPanel.repaint();
		}
	}

}
