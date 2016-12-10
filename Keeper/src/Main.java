public class Main {

	public static void main(String[] args) {

		String path = "./mydata.json";
		KeeperParser kp = new KeeperParser(path);
		kp.init();
		kp.run();
	}

}

/*
 * Notes:
 * My Epoch was December 1, 2016
 */