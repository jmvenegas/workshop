import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


public class Lab 
{
	
	public static void main(String[] args) 
	{
		intro("intro.txt");
		test(optionStart());
		
		

	}
	
	public static void slowPrint(String in)
	{
			for(int i=0;i<in.length();i++){
				System.out.print(in.charAt(i));
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
			}
			System.out.println();
	}
	
	public static void test(int option){
		if(option==0){
			System.exit(0);
		}
		else{
			Random random = new Random();
			int r = random.nextInt(4);
			switch(r){
			case 0: slowPrint("GlaDos: Good luck completing this next test.");
			break;
			case 1: slowPrint("GlaDos: Your weight variance may be a problem for this next test.");
				break;
			case 2: slowPrint("GlaDos: At the end of this test, there will be cake.");
				break;
			case 3: slowPrint("GlaDos: Your record says you have a degree in art history. How impressive.");
				break;
			}
			System.out.println();  //simple line break
			
			try 
			{
				Scanner scan0 = new Scanner(new File("test.txt"));
				while(scan0.hasNextLine()){
					String nextLine = scan0.nextLine();
					slowPrint(nextLine);
				}scan0.close();
			} 
			catch (FileNotFoundException e) 
			{
				//e.printStackTrace();
			}
			
			//Receive options for test
			boolean stable = false;
			
			//container variables
			int c0 = 20;
			int c1 = 70;
			int c2 = 50;
			
			while(stable!=true){
			Scanner stdin = new Scanner(System.in);
			slowPrint("Which container will you adjust?: ");
				int userIn = stdin.nextInt();
			slowPrint("Increase container by 30%: 0");
			slowPrint("Decrease container by 20%: 1");
				int change = stdin.nextInt();
				

				
				
			if(change==0){
				change = 30;
			}
			else
				change= -20;
			
			
			switch(userIn){
				case 0: c0+=change;
					slowPrint("Container " + userIn + " is now at " + (c0)+" %");
					break;
				case 1: c1+=change;
				slowPrint("Container " + userIn + " is now at " + (c1)+" %");
					break;
				case 2: c2+=change;
				slowPrint("Container " + userIn + " is now at " + (c2)+" %");
					break;
			}
			
			
			if(c0==60 && c1==60 && c2==60)
				stable=true;
			
			}
			try 
			{
				Scanner scanFin = new Scanner(new File("testComplete.txt"));
				while(scanFin.hasNextLine()){
					String next1= scanFin.nextLine();
					slowPrint(next1);
				}scanFin.close();
			} 
			catch (FileNotFoundException e) 
			{
				//e.printStackTrace();
			}
			
			
			
			
		}
		
	}
	
	public static int optionStart()
	{
		slowPrint("Begin Testing: 1"+
				"\nExit the Lab:  2");
		Scanner scan = new Scanner(System.in);
		int option = scan.nextInt();
		return option;

	}
	
	
	
	
	
	
	public static void intro(String filename){
		try 
		{
			Scanner scan = new Scanner(new File(filename));
			while(scan.hasNextLine())
			{
				String next = scan.nextLine();
				slowPrint(next);
				//System.out.println(next);
			}scan.close();
			
			
			
		} catch (FileNotFoundException e) 
		{
			System.out.println("could not read file.");
			//e.printStackTrace();
		}
		
		

	}

}
