/*
 * Author: Venegas, Justin
 * Class: DNA Sequence Analyzer
 * Date: Feb. 1, 2013
 */
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class seqAnalyzer 
{
	public static void main(String[] args) 
	{
		//Variables
		String tripletCode, currentLine;
		BufferedReader crawler;
		int lineCounter = 0;
		int tripletCount = 0;
		
		//Take Search
		System.out.print("What is the Triplet Code for the Amino Acid you are looking for?:  ");
		Scanner keyInput = new Scanner(System.in);
		tripletCode = keyInput.next( );
		
		//Read File
		try
		{
			crawler = new BufferedReader(new FileReader("C:\\Users\\AtlasBot\\Desktop\\DNA-Sequence.txt"));
			System.out.println("Searching for " + tripletCode + " in file...");
			Pattern p = Pattern.compile(tripletCode, Pattern.CASE_INSENSITIVE);
			while (( currentLine = crawler.readLine( ) ) != null)
			{
				lineCounter++;
				Matcher m = p.matcher(currentLine);
				
				//Find All Matches
				while(m.find( ) )
				{
					tripletCount++;
					System.out.println("Amino Acid " +"\"" + tripletCode + "\"" + " was found at index " + m.start( ) + " on line " + lineCounter);
					

				}

			}
			
			//Statistics Data
			System.out.println("Triplet count for \"" + tripletCode + "\" is : " + tripletCount);
			
		crawler.close( );
		}
		catch (IOException exception)
		{
			System.out.println("IO Error Occurred: " + exception.toString( ) );
		}
	}
}
