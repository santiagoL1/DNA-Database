import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DNADatabase {
	
	private String file;
	private boolean match;
	

	
	public static void main (String[]args) {
		Scanner input = new Scanner(System.in);
		System.out.println(" What file had the DNA database? ");
		String dna_database = input.nextLine();
		DNADatabase database = new DNADatabase(dna_database);
		System.out.println("What is the DNA String or type Q to quit");
		while(true) {
			String dna_string = input.nextLine();
			if ("Q".equalsIgnoreCase(dna_string)) {
	            break;
	        }
			else {
				System.out.println(database.findExactMatch(dna_string));
				if(database.match == false) {
					System.out.println(database.findClosestMatches(dna_string));
			}
				
			System.out.println("What is the DNA String or type Q to quit");
		}
		}}
	

	/**
	 * Creates a database of DNA profiles. The profiles are
	 * stored as the users name and their counts
	 * of the various short tandem repeats (STRs) that are included.
	 * 
	 * The file will be organized such that the first line is
	 * a header row that will start with name and then list
	 * the STR that the database includes separated by commas
	 * (e.g. name,AGAT,AATG,TATC). Each line after that will have
	 * the data for a single profile (e.g. Alice,28,42,14 which 
	 * indicates that the profile stored is for Alice and she
	 * has 28 AGATs in a row, 42 AATGs in a row and 14 TATCs in a row).
	 * 
	 * @param filename the file that stores the database
	 */
	public DNADatabase(String filename) {
		this.file = filename;
		try {
			
            Scanner infile = new Scanner(new File(filename));
            while (infile.hasNext()) {
                String nextWord = infile.next();
            }
        }
        catch (java.io.FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
        }
	}
	

	/**
	 * Get the numbers of Rows in the file. 
	 * will help to transform the file in a multidimensional array 
	 * the array will be used to compare the file and the dna entered by the user
	 * @return the number of rows in the file
	 * @author santiago
	 * 
	 */
	public int getNumberOfRows() {
		int rows = 0;
		try {
			Scanner infile1 = new Scanner(new File(this.file));
			while (infile1.hasNext()) {
				infile1.next();
				rows +=1;
			}
			return rows;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	

	/**
	 * Get the numbers of Rows columns 
	 * will help to transform the file in a multidimensional array 
	 * the array will be used to compare the file and the dna entered by the user
	 * @return the number of columns in the file 
	 * @author santiago 
	 */
	public int getNumberOfColumns() {
		try {
			Scanner infile2 = new Scanner(new File(this.file));
			String line = infile2.next();
			String first_line[] = line.split(",");
			return first_line.length;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	/**
	 * convert the file in a multidimensional array 
	 * @return the file in the form of a multidimensional array
	 * @author santiago 
	 */	
	public String[][] getFileArray() {
		String[][] file_array = new String[getNumberOfRows()][getNumberOfColumns()];
		int row = 0;
		try {
			Scanner infile3 = new Scanner(new File(this.file));
			while (infile3.hasNext()) {
				String line = infile3.next();
				String arr[] = line.split(",");
				for(int col = 0; col < getNumberOfColumns(); col++) {
					file_array[row][col] = arr[col];
				}
				row +=1;
				}
			return file_array;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file_array;
	}
	
	/**
	 * Helper Method to store the max number of repetition in CountSTRs and FindClosestMatch
	 * @param int number that is supposed to be stored
	 * @return the parameter
	 * @author santiago
	 * 
	 */
	private int getMax(int count){
		return count;
	}
	
	
	
	/**
	 * Determines the maximum number of times the STR occurs in
	 * the DNA sequence in a row
	 * @param dnaSequence the sequence of the DNA as a string of 
	 * As, Gs, Ts, and Cs
	 * @param str the short tandem repeat that you are counting
	 * @return the count of the maximum number of times the STR 
	 * occurred in a row
	 * @author santiago
	 */ 
	public int countSTRs(String dnaSequence, String str) {
		int count = 0;
		int max = 0;
		boolean run = true;
		while(run == true) { 
			if(dnaSequence.contains(str)== true&& dnaSequence.indexOf(str)==0 ) {
				if(dnaSequence.length()< (str.length()*2)){
					dnaSequence  = dnaSequence.substring(dnaSequence.indexOf(str)+1);
					count +=1;
				}
				else {
					dnaSequence  = dnaSequence.substring(dnaSequence.indexOf(str)+ str.length());
					count += 1;
			}}
			else if(dnaSequence.contains(str)== true && dnaSequence.indexOf(str)!=0 ) {
				dnaSequence = dnaSequence.substring(dnaSequence.indexOf(str));
				if(count!=0) {
					if(count>getMax(max)) {
						max = getMax(count);
					}
				}
				count = 0;}
			else {
				if(count!=0) {
					if(count>getMax(max)) {
						max = getMax(count);
						}}
				run  = false;
				}
			}
		return max;}
	
	
	
	
	/**
	 * After counting the Strs. The result is store in an array
	 * Help in the comparison with the elements of the file_array
	 * @param dnaSequence the sequence of the DNA as a string of 
	 * As, Gs, Ts, and Cs
	 * @return the dnaSequence in the form of an array
	 * @author santiago 
	 */	
	public String[] getSTRsArray(String dnaSequence) {
		String[] strs_array = new String[getNumberOfColumns()-1];
		for(int i = 1; i < getNumberOfColumns(); i++) {
			strs_array[i-1] = String.valueOf(countSTRs(dnaSequence,getFileArray()[0][i]));
			}
		return strs_array;
		}
	
	

	/**
	 * Finds the profile in the database that is an exact match
	 * Compare the element in the file array with array that contains the count of STRS
	 * @param dnaSequence the sequence of the DNA as a string of 
	 * As, Gs, Ts, and Cs
	 * @return the name of the profile that matches all the counts
	 * of STRs or the text "No Match" if no exact match is found
	 * @author santiago
	 */
	
	public String findExactMatch(String dnaSequence) {
		String[][] file_array = this.getFileArray();
		String[] dna_array = this.getSTRsArray(dnaSequence);
		this.match = false;
		int count = 0;
		String result = "NO MATCH";
		for(int i = 1; i<this.getNumberOfRows(); i++) {
			if(this.match == true) {
				break;
			}
			for(int j=0; j<dna_array.length; j++) {
				if(Integer.valueOf(dna_array[j])!= Integer.valueOf(file_array[i][j+1])) {
					count = 0;
					break;
				}
				else {
					count +=1;
					if(count == dna_array.length) {
						this.match = true;
						result = file_array[i][0];
						break;
					}}}}
		return "The sequence matches " + result + ".";
		}

	
	/*
	 * If there is no exact match need to see who is the closest match
	 * Compare the file array with the the array of the countSTRs
	 * Create an array with the name of the person followed by 0s or 1s
	 * For each comparison: 
	 * 1 when there is an STRs in common
	 * 0 if not 
	 * ex:
	 * [Name, 0,1,0,0,0
	 * Name, 1,0,0,0,0
	 * Name, 1,1,0,0,1]
	 * 
	 * @param dnaSequence the sequence of the DNA as a string of 
	 * As, Gs, Ts, and Cs
	 * @return multidimensional array
	 * @author santiago 
	 */
	public String[][] arraySTRsMatches(String dnaSequence) {

		String[][] file_array = this.getFileArray();
		String[] dna_array = this.getSTRsArray(dnaSequence);
		String[][] array_strs_matches = new String[this.getNumberOfRows()-1][this.getNumberOfColumns()];
		for(int row = 0; row<array_strs_matches.length; row++) {
			for(int col=0; col<array_strs_matches[row].length; col++) {
				if(col==0) {
					array_strs_matches[row][col] = file_array[row+1][col];}
				else if(col!=0) {
				if(dna_array[col-1].equals(file_array[row+1][col])) {
					array_strs_matches[row][col] = "1";
				}
				else {
					array_strs_matches[row][col] = "0";
				}
				}}}
		return array_strs_matches;
	}
	
	/*
	 * return an array with  just the name of the person followed by the number of STRs in common
	 * addition all the 1s  
	 * ex:
	 * [Name, 2
	 * Name, 5
	 * Name, 0]
	 * @param dnaSequence the sequence of the DNA as a string of 
	 * As, Gs, Ts, and Cs
	 * @return multidimensional array 
	 * @author santiago 
	 * 
	 */
	
	public String[][] arrayNumOfMatches(String dnaSequence){
		String[][] array_strs = arraySTRsMatches(dnaSequence);
		String[][] count_matches = new String[this.getNumberOfRows()-1][2];
		int tot = 0;
		for(int row = 0; row < this.getNumberOfRows()-1; row ++) {
			for(int col = 0; col < 2; col ++) {
				if(col == 0) {
					count_matches[row][col] = array_strs[row][col];
				}
				if(col!=0) {
						for(int col1 = 1; col1 < array_strs[row].length; col1 ++){
							tot += Integer.valueOf(array_strs[row][col1]);
						}
						count_matches[row][col] = String.valueOf(tot);
						tot = 0;
						} 
				}
			}
		return count_matches;
		}

	
	
	/**
	 * Finds the profile(s) in the database that are the closest 
	 * match for the DNA sequence. The closest match is determine
	 * by counting the number of STR counts that are the same and 
	 * selecting the profile that match the largest number of STR
	 * matches.
	 * @param dnaSequence the sequence of the DNA as a string of 
	 * As, Gs, Ts, and Cs
	 * @return the list of the name(s) that have the most STR counts
	 * that are the same followed by the count of the number of matching
	 * STRs. The names should be one per line, followed by a line with the
	 * summary of their similarity. E.g.
	 * 
	 * Alice
	 * Bob
	 * With 1 STRs in common
	 * 
	 * @author santiago 
	 */
	
	public String findClosestMatches(String dnaSequence) {
		String count_str[][] = arrayNumOfMatches(dnaSequence);
		String closest_match[] = new String[this.getNumberOfRows()-1]; 
		int max = 0;
		int index = 0;
		for(int i = 0; i< count_str.length; i++) {
			if(Integer.valueOf(count_str[i][1]) >= getMax(max)) {

			closest_match[index] = count_str[i][0];
			index +=1;
			max= Integer.valueOf(count_str[i][1]);
			
		}}
		return "Closest Matches are: " + "\n" + display(closest_match) + "\n" +
				"With " + String.valueOf(getMax(max)) + " STRs in commun";
		}
				

/*
 * Helper method to convert the result in the format wished in findClosestMatch
 * @param array that needs to be formatted
 * @return array formatted
 * @author santiago
 */
	
	private String display(String[] array) {
		StringBuilder display = new StringBuilder();
		for(int i = 0; i<array.length; i++) {
			if(array[i] == null) {
				continue;}
			else {
			display.append(array[i] + "\n");
		}}
		return display.toString();
		}
	
}
