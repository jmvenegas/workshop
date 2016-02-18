/*
 * Author: Venegas, Justin
 * Class: DNA Generator
 * Date: Feb. 1, 2013
 * 
 */
import java.io.*;
import java.util.*;

public class seqGenerator 
{
	public static void main(String[] args) throws IOException
	{
		//Variables
		String bases ="ATCG", finalSequence;
		StringBuilder sequence = new StringBuilder("@");
		Random rGen = new Random( );
		int seqLength, baseLength = bases.length( );
		Writer output = null;
		
		//Introduction
		System.out.println("Sequence Generator will create a randomly generated DNA code sequence.");
		System.out.print("How long is the sequence you would like to generate?: ");
		
		//Read Input
		Scanner keyInput = new Scanner(System.in);
		seqLength = keyInput.nextInt( );
		
		//Generate
		for (int i = 0; i < seqLength; i++)
		{
			//For Printing to Console Use Commented Code
			//System.out.println(bases.charAt(rGen.nextInt(baseLength)));
			sequence.append(bases.charAt(rGen.nextInt(baseLength)));
		}
		
		//Write StringBuilder to String
		finalSequence = sequence.toString();
		
		//Print String to File
		output = new PrintWriter(new FileWriter("C:\\Users\\AtlasBot\\Desktop\\DNA-Sequence.txt"));
		output.write(finalSequence);
		
		//Close Resource
		output.close();
		keyInput.close( );
		System.exit(0);
		
	}
	

	
}
