import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KeeperParser {

	static String DEFAULT_PATH = "./mydata.json";

	private String path;
	private ArrayList<Keeper> keeperList;
	private ObjectMapper mapper;
	private KeeperCommandInterface kci;

	public KeeperParser(String path) {
		this.path = path;
		this.keeperList = new ArrayList<Keeper>();
		this.mapper = new ObjectMapper();
		mapper.disable(MapperFeature.AUTO_DETECT_CREATORS,
				MapperFeature.AUTO_DETECT_FIELDS,
				MapperFeature.AUTO_DETECT_GETTERS,
				MapperFeature.AUTO_DETECT_IS_GETTERS);
		this.kci = new KeeperCommandInterface(this);
	}

	public void init() {
		ArrayList<Keeper> keeperList = null;
		BufferedReader bufferedReader = null;
		// Try Specified Path
		try {
			bufferedReader = new BufferedReader(new FileReader(this.path));
		} catch (FileNotFoundException fnfe) {
			System.out
					.println("[KPREXCEPT]Specified Keeper Data File Not Found.");
			// Try Default Path
			try {
				bufferedReader = new BufferedReader(
						new FileReader(DEFAULT_PATH));
			} catch (FileNotFoundException fnfe2) {
				System.out
						.println("[KPREXCEPT]Default Keeper Data File Not Found.");
				return;
			}
		}
		try {
			keeperList = new ArrayList<Keeper>();
			String line = null;
			this.mapper.disable(MapperFeature.AUTO_DETECT_CREATORS,
					MapperFeature.AUTO_DETECT_FIELDS,
					MapperFeature.AUTO_DETECT_GETTERS,
					MapperFeature.AUTO_DETECT_IS_GETTERS);
			while ((line = bufferedReader.readLine()) != null) {
				keeperList.add(this.mapper.readValue(line, Keeper.class));
			}
			bufferedReader.close();
		} catch (IOException ioe) {
			System.out
					.println("[KPREXCEPT]Exception Occured Reading/Parsing File.");
		}
		this.keeperList = keeperList;
	}

	public void run() {
		this.kci.interact();
	}

	public ArrayList<Keeper> getKeeperList() {
		return this.keeperList;
	}

	private String getKeeperDataAsString() {
		StringBuilder output = new StringBuilder();
		for (Keeper keeper : this.keeperList) {
			try {
				output.append(this.mapper.writeValueAsString(keeper));
				output.append("\n");
			} catch (JsonProcessingException e) {
				System.out
						.println("[KPREXCEPT]Exception Parsing to JSON for Disk.");
			}
		}
		return output.toString();
	}

	public void writeKeepersToDisk() {
		String outputData = this.getKeeperDataAsString();
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(this.path);
		} catch (IOException e) {
			System.out
					.println("[KPREXCEPT]Exception Opening Keeper Data File for Writing.");
			return;
		}
		bufferedWriter = new BufferedWriter(fileWriter);
		try {
			bufferedWriter.write(outputData);
		} catch (IOException e) {
			System.out.println("[KPREXCEPT]Exception Writing to Data File");
		}
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("[KPREXCEPT]Exception Closing Data File");
		}
	}
}
