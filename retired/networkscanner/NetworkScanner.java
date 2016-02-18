/*
 * date 2015/11/7
 * TODO - Add thread pool
 * 		- General code cleanup 
 */

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/*
 * Scan the current network for active ip address
 */
public class NetworkScanner {
	
	static int subnetMask = 255;
	static int timeout = 1700;
	
	public static void main(String[] args) {

		System.out.println("Starting network scanner...");
		String localhostaddress = "NullAddress";
		try {
			localhostaddress = Inet4Address.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			System.out.println("Could not get host address.");
			e.printStackTrace();
		}
		System.out.println("localhost: " + localhostaddress);
		
		// Parse the string to get gateway
		String gateway = localhostaddress.substring(0,localhostaddress.lastIndexOf('.'));
		System.out.println("Subnet: " + gateway);
		
		for(int i=0;i<subnetMask;i++) {
			// Get next ip to check
			String ipToCheck = gateway + "." + i;
				
			try {
				//Use threadpool to manage later
				if (InetAddress.getByName(ipToCheck).isReachable(timeout)) {
					System.out.println(ipToCheck + " AVAILABLE.");
				}
				else {
					System.out.println(ipToCheck + " unavilable.");
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
