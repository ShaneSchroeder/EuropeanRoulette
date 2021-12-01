import java.util.Scanner;
import java.io.*;
/** This program is a simulation of European Roulette.
  * 
  * @author Shane Schroeder, Noah Malinowski
  * 
  * @version 1.0
  * 
  * */
public class Schroeder_Malinowski_Roulette{
  /** The main method will call all methods, created a template table, and a modified by user table, and make everything into usable data
    * 
    * @author Shane Schroeder, Noah Malinowski
    * 
    * @param none
    * 
    * @return none
    * */
  public static void main(String[] args){
    try
    {
      Scanner in = new Scanner(System.in);
      PrintWriter pw = new PrintWriter("results.txt");
      pw.println("");
      pw.close();
      final String[][] standardTable = 
      {{"", "03", "|", "06", "|", "09", "|", "12", "|", "15", "|", "18", "|", "21", "|", "24", "|", "27", "|", "30", "|", "33", "|", "36", "2:1"},
        {"", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", ""},
        {"", "", "", " |r", "", " |b", "", " |b", "", " |r", "", " |b", "", " |b", "", " |r", "", " |b", "", " |b", "", " |r", "", " |b", ""},
        {"00", "02", "|", "05", "|", "08", "|", "11", "|", "14", "|", "17", "|", "20", "|", "23", "|", "26", "|", "29", "|", "32", "|", "35", "2:1"},
        {"", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", ""},
        {"", "", "", " |b", "", " |r", "", " |b", "", " |b", "", " |r", "", " |r", "", " |b", "", " |r", "", " |b", "", " |b", "", " |r", ""},
        {"", "01", "|", "04", "|", "07", "|", "10", "|", "13", "|", "16", "|", "19", "|", "22", "|", "25", "|", "28", "|", "31", "|", "34", "2:1"},
        {"", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", "_|_", "__", ""},
        {"", "", "", "", "", "1st 12", "", "", "", "", "", "", "2nd 12", "", "", "", "", "", "", "3rd 12", "", "", "", "", ""},
        {"", "", "", "", "1 to 18", "", "", "Even", "", "", "Red", "", "", "Black", "", "", "", "Odd", "", "", "19 to 36", "", "", "", ""}
      }; //creates template table
      
      String[][] userTable = standardTable;
      while(userTable[0][0].equals(""))
      {
        userTable = standardTable;
        int userColumn = 0;
        int userRow = 0;
        present(userTable);
        userTable = userInput(standardTable, userRow, userColumn);
        if(!userTable[0][0].equals("99"))
        {
          present(userTable);
          
          for(int i = 0; i < 10; i++)
          {
            for(int j = 0; j < 25; j++)
            {
              if(!userTable[i][j].equals(""))
              {
                if(userTable[i][j].charAt(0) == '*')
                {
                  userRow = i;
                  userColumn = j;
                }
              }
            }
          }
          System.out.println("How much money would you like to bet?");
          int bet = in.nextInt();
          
          int spin = spin(); //gets number from roulette wheel
          
          boolean split = split(userRow, userColumn, spin, standardTable); //tests if the split bet won
          
          boolean straight = straight(userRow, userColumn, spin, standardTable); //tests if the straight bet won
          
          boolean corner = corner(userRow, userColumn, spin, standardTable); //tests if the corner bet won
          
          boolean streetColumn = streetColumn(userRow, userColumn, spin, standardTable); //tests if the street or column bet won
          
          boolean twelve = twelve(userRow, userColumn, spin, standardTable); //tests if the twelve bet won
          
          boolean halves = halves(userRow, userColumn, spin, standardTable); //tests if the half bet won
          
          boolean evenOdd = evenOdd(userRow, userColumn, spin, standardTable); //tests if the odd or even bet won
          
          boolean line = line(userRow, userColumn, spin, standardTable); //tests if the line bet won
          
          boolean color = color(userRow, userColumn, spin, standardTable); //tests if the color bet won
          
          output(straight, corner, split, streetColumn, twelve, halves, evenOdd, color, line, userRow, userColumn, bet, spin); //calls output method to write 'results.txt'
        }
        point4(standardTable);
      }
    }
    catch(java.io.FileNotFoundException e)
    {
      System.out.println("No File Found.");
    }
  }
  
  
  /* userInput takes in user input
   * @author Noah Malinowski
   * @param final String[][] standardTable, int userRow, int userColumn
   */
  public static String[][] userInput(final String[][] standardTable, int userRow, int userColumn){
    Scanner in = new Scanner(System.in);
    String userInput = "";
    String[][] userTable = new String[10][25];
    String temp = "";
    for(int a = 0; a < 10; a++){
      for(int b = 0; b < 25; b++){
        temp = standardTable[a][b];
        userTable[a][b] = temp;
      }
    }
    while(!userInput.equals("Bet") && !userInput.equals("Done")){
      if(!userInput.equals("Done"))
      {
        for(int a = 0; a < 10; a++){
          for(int b = 0; b < 25; b++){
            temp = standardTable[a][b];
            userTable[a][b] = temp;
          }
        }
        userInput = "";
        System.out.println("Please input up, down, left, right, or a number to jump straight there.");
        System.out.println("If you are finished, type 'Bet' to place your bet.");
        System.out.println("If you are finished betting, type 'Done' to end the program.");
        userInput = in.nextLine();
        if(userInput.equals("up") && userRow > 0){
          userRow = userRow - 1;
        } else if(userInput.equals("down") && userRow < 9 ){
          userRow = userRow + 1;
        } else if(userInput.equals("left") && userColumn > 0){
          userColumn = userColumn -1;
        } else if(userInput.equals("right") && userColumn < 24){
          userColumn = userColumn + 1;
        } else {
          for(int i = 0; i < 10; i++){
            for(int j = 0; j < 25; j++){
              if(userInput.equals(standardTable[i][j])){
                userColumn = j;
                userRow = i;
              }
            }
          }
        }
        if(userRow == 2 || userRow == 5){
          if(userInput.equals("up")){
            userRow--;
          }
          if(userInput.equals("down")){
            userRow++;
          }
        }
        if(userTable[userRow][userColumn].equals("")){
          int g = 0;
          while(true){
            g++;
            System.out.println(userColumn - g);
            if(userColumn - g >= 0){
              if(!userTable[userRow][userColumn - g].equals("")){
                userColumn = userColumn - g;
                break;
              }
            }
            if(userColumn + g <= 24){
              if(!userTable[userRow][userColumn + g].equals("")){
                userColumn = userColumn + g;
                break;
              }
            }
          }
        }
        String temp2 = "";
        for(int c = 0; c < standardTable[userRow][userColumn].length(); c++){
          temp2 = temp2 + "*";
        }
        userTable[userRow][userColumn] = temp2;
        if(userRow == 1 || userRow == 4){
          userRow = userRow + 1;
          userColumn++;
          temp2 = "";
          for(int d = 0; d < standardTable[userRow][userColumn].length(); d++){
            temp2 = temp2 + "*";
          }
          userTable[userRow][userColumn] = temp2;
          userRow--;
          userColumn--;
        }
        if(!userInput.equals("Bet") && !userInput.equals("Done")){
          present(userTable);
        }
        if(userInput.equals("Done")){
          userTable[0][0] = "99";
        }
      }
    }
    return userTable;
  }
  
  /*present displays the current table 
   * @author Noah Malinowski
   * @param String[][] userTable
   */
  public static void present(String[][] userTable){
    System.out.println("    _ ____ ____ ____ ____ ____ ____ ____ ____ ____ ____ ____ ____ _____ ");
    System.out.println("   / |r   |b   |r   |r   |b   |r   |r   |b   |r   |r   |b   |r   |     |");
    System.out.print("  /  |");
    for(int i = 0; i < 14; i++){
      for(int j = 0; j < 25; j++){
//first row
        if(i == 0){
          if(j == 24){
            System.out.print("| " + userTable[i][j] + " |");
          } else{
            System.out.print(userTable[i][j] + " ");
          }
        }
//second row
        if(i == 1){
          if(j == 0){
            System.out.print(" /   |_");
          }
          if(j == 24){
            System.out.print("_|_____|");
          }
          System.out.print(userTable[i][j]);
        }
//third row
        if(i == 2){
          if(j == 0){
            System.out.print("|    |b ");
          }
          if(j == 24){
            System.out.print("  |     |");
          }
          if(j == 1 || j== 2){
            System.out.print("");
          } else{
            System.out.print(userTable[i][j] + " ");
          }
        }
//fourth row
        if(i == 3){
          if(j == 0){
            System.out.print("| ");
          }
          if(j == 1){
            System.out.print("| ");
          }
          if(j == 24){
            System.out.print("| " + userTable[i][j] + " |");
          } else{
            System.out.print(userTable[i][j] + " ");
          }
        }
//fifth row
        if(i == 4){
          if(j==0){
            System.out.print("|    |_");
          }
          if(j==24){
            System.out.print("_|_____|");
          }
          System.out.print(userTable[i][j]);
        }
//sixth row
        if(i == 5){
          if(j==0){
            System.out.print(" \\   |r");
          }
          if(j==24){
            System.out.print("  |     |");
          }
          if(j == 1){
          } else{
            System.out.print(userTable[i][j] + " ");
          }
        }
//seventh row
        if(i == 6){
          if(j==0){
            System.out.print("  \\  |");
          }
          if(j==24){
            System.out.print("| " + userTable[i][j] + " |");
          } else{
            System.out.print(userTable[i][j] + " ");
          }
        }
//eighth row
        if(i == 7){
          if(j==0){
            System.out.print("   \\_|_");
          }
          if(j==24){
            System.out.print("_|_____|");
          }
          System.out.print(userTable[i][j]);
        }
//ninth row
        if(i == 8 && j == 0){
          System.out.print("     |                   |                   |                   |");
        }
//tenth row
        if(i == 9){
          if(j == 0){
            System.out.print("   ");
          }
          if(j == 1 || j == 8 || j == 15 || j == 22){
            System.out.print("| ");
          } else{
            System.out.print(userTable[i-1][j] + "  ");
          }
          if(j == 24){
            System.out.print("");
          }
        }
//eleventh row
        if(i == 10 && j == 0){
          System.out.print("     |___________________|___________________|___________________|");
        }
//twelvth row
        if(i == 11 && j == 0){
          System.out.print("     |           |       |        |          |       |           |");
        }
//thirteenth row
        if(i == 12){
          if(j == 0){
            System.out.print("    ");
          }
          if(j == 1 || j == 5 || j == 8 || j == 12 || j == 15 || j == 18 || j == 22){
            System.out.print("|");
          } else{
            System.out.print(userTable[i-3][j] + " ");
          }
          if(j == 12){
            System.out.print(" ");
          }
          if(j == 4 || j == 7 || j == 8 || j == 10|| j == 12 || j == 13 || j == 15 || j == 17){
            System.out.print(" ");
          }
          if(j == 24){
            System.out.print("");
          }
        }
//fourteenth row
        if(i == 13 && j == 0){
          System.out.print("     |___________|_______|________|__________|_______|___________|");
        }
//fifteenth row
        
      }
      System.out.println("");
    }
  }
  
  /* This method will generate a random number between 0 and 36 to act as if a roulette wheel was spun
   * 
   * @author Shane Schroeder
   * 
   * @param none
   * 
   * @return int, random number that was generated
   * */
  public static int spin()
  {
    int spin = (int) (Math.random() * (36 - 0 + 1) - 0);
    return spin;
  }
  
  /* This method will check if your bet was a split bet and if it won
   * 
   * @author Shane Schroeder
   * 
   * @param int userRow, the row the user is at in the String[][]
   * @param int userColumn, the column the user is at in the String[][]
   * @param int spin, the random generated number
   * @param String[][] standardTable, the template table to check for values
   * 
   * @return boolean split, if they won
   * */
  public static boolean split(int userRow, int userColumn, int spin, String[][] standardTable)
  {
    boolean split = false;
    
    
    if(userRow > 0 && userRow < 8) //makes sure they are in the correct range for rows
    {
      if(!standardTable[userRow+2][userColumn].equals("") && !standardTable[userRow-1][userColumn].equals(""))
      {
        if(standardTable[userRow+2][userColumn].charAt(0) == '0' || standardTable[userRow+2][userColumn].charAt(0) == '1' || standardTable[userRow+2][userColumn].charAt(0) == '2'  || 
           standardTable[userRow+2][userColumn].charAt(0) == '3')
        {
          if(standardTable[userRow-1][userColumn].charAt(0) == '0' || standardTable[userRow-1][userColumn].charAt(0) == '1' || standardTable[userRow-1][userColumn].charAt(0) == '2' ||
             standardTable[userRow-1][userColumn].charAt(0) == '3')
          {
            if(Integer.parseInt(standardTable[userRow - 1][userColumn]) == spin || Integer.parseInt(standardTable[userRow + 2][userColumn]) == spin) //vertical split
            {
              split = true;
            }
          }
        }
      }
    }
    
    if(userColumn != 0 && userColumn != 24)
    {
      if(!(standardTable[userRow][userColumn+1].equals("") && standardTable[userRow][userColumn-1].equals("")))
      {
        if(standardTable[userRow][userColumn+1].charAt(0) == '0' || standardTable[userRow][userColumn+1].charAt(0) == '1' || standardTable[userRow][userColumn+1].charAt(0) == '2' ||
           standardTable[userRow][userColumn+1].charAt(0) == '3')
        {
          if(standardTable[userRow][userColumn-1].charAt(0) == '0' || standardTable[userRow][userColumn-1].charAt(0) == '1' || standardTable[userRow][userColumn-1].charAt(0) == '2' ||
             standardTable[userRow][userColumn-1].charAt(0) == '3')
          {
            if(Integer.parseInt(standardTable[userRow][userColumn - 1]) == spin || Integer.parseInt(standardTable[userRow][userColumn + 1]) == spin) //horizontal split
            {
              split = true;
            }
          } 
        }
      }
    }
    return split;
  }
  
  /* This method will check if your bet was a straight bet and if it won
   * 
   * @author Shane Schroeder
   * 
   * @param int userRow, the row the user is at in the String[][]
   * @param int userColumn, the column the user is at in the String[][]
   * @param int spin, the random generated number
   * @param String[][] standardTable, the template table to check for values
   * 
   * @return boolean straight, if they won
   * */
  public static boolean straight(int userRow, int userColumn, int spin, String[][] standardTable)
  {
    boolean straight = false;
    
    if(standardTable[userRow][userColumn].charAt(0) == '0' || standardTable[userRow][userColumn].charAt(0) == '1' || standardTable[userRow][userColumn].charAt(0) == '2' ||
       standardTable[userRow][userColumn].charAt(0) == '3')
    {
      if(!(standardTable[userRow][userColumn].length() > 2))
      {
        if(Integer.parseInt(standardTable[userRow][userColumn]) == spin) 
        {
          straight = true;
        }
      }
    }
    return straight;
  }
  
  /* this method checks to see if you bet on a corner and if it won
   * @author Noah Malinowski
   * @param int userRow, int userColumn, int spin, String[][] standardTable
   */
  public static boolean corner(int userRow, int userColumn, int spin, String[][] standardTable)
  {
    boolean corner = false;
    if(userRow > 0 && userRow < 8 && userColumn > 1 && userColumn < 24)
    {
      userRow = userRow - 1;
      userColumn = userColumn - 1;
      if(!standardTable[userRow+2][userColumn + 1].equals("") && !standardTable[userRow-1][userColumn + 1].equals(""))
      {
        if(standardTable[userRow+2][userColumn + 1].charAt(0) == '0' || standardTable[userRow+2][userColumn + 1].charAt(0) == '1' || standardTable[userRow+2][userColumn + 1].charAt(0) == '2' ||
           standardTable[userRow+2][userColumn + 1].charAt(0) == '3')
        {
          
          if(standardTable[userRow-1][userColumn + 1].charAt(0) == '0' || standardTable[userRow-1][userColumn + 1].charAt(0) == '1' || standardTable[userRow-1][userColumn + 1].charAt(0) == '2' ||
             standardTable[userRow-1][userColumn + 1].charAt(0) == '3')
          {
            if(Integer.parseInt(standardTable[userRow - 1][userColumn + 1]) == spin || Integer.parseInt(standardTable[userRow + 2][userColumn + 1]) == spin) //vertical corner
            {
              corner = true;
            }
          }
        }
      }
      
      
      
      if(!standardTable[userRow+2][userColumn + 1].equals("") && !standardTable[userRow-1][userColumn + 1].equals(""))
      {
        if(standardTable[userRow+2][userColumn - 1].charAt(0) == '0' || standardTable[userRow+2][userColumn - 1].charAt(0) == '1' || standardTable[userRow+2][userColumn - 1].charAt(0) == '2' ||
           standardTable[userRow+2][userColumn - 1].charAt(0) == '3')
        {
          
          if(standardTable[userRow-1][userColumn - 1].charAt(0) == '0' || standardTable[userRow-1][userColumn - 1].charAt(0) == '1' || standardTable[userRow-1][userColumn - 1].charAt(0) == '2' ||
             standardTable[userRow-1][userColumn - 1].charAt(0) == '3')
          {
            if(Integer.parseInt(standardTable[userRow - 1][userColumn - 1]) == spin || Integer.parseInt(standardTable[userRow + 2][userColumn - 1]) == spin) //vertical corner
            {
              corner = true;
            }
          }
        }
      }
    }
    return corner;
  }
  
  /* This method will check if your bet was a street or column bet and if it won
   * 
   * @author Shane Schroeder
   * 
   * @param int userRow, the row the user is at in the String[][]
   * @param int userColumn, the column the user is at in the String[][]
   * @param int spin, the random generated number
   * @param String[][] standardTable, the template table to check for values
   * 
   * @return boolean streetColumn, if they won
   * */
  public static boolean streetColumn(int userRow, int userColumn, int spin, String[][] standardTable)
  {
    boolean streetColumn = false;
    if(userRow == 7)
    {
      if(userColumn == 1 || userColumn == 3 || userColumn == 5 || userColumn == 7 || userColumn == 9 || userColumn == 11 || userColumn == 13 || userColumn == 15 || userColumn == 17 ||
         userColumn == 19 || userColumn == 21)
      {
        if(Integer.parseInt(standardTable[userRow - 1][userColumn]) == spin || Integer.parseInt(standardTable[userRow - 4][userColumn]) == spin || 
           Integer.parseInt(standardTable[userRow - 7][userColumn]) == spin)
        {
          streetColumn = true;
        }
      }
    }
    
    if(userColumn == 24)
    {
      if(userRow == 0 || userRow == 3 || userRow == 6)
      {
        if(Integer.parseInt(standardTable[userRow][userColumn - 1]) == spin || Integer.parseInt(standardTable[userRow][userColumn - 3]) == spin || 
           Integer.parseInt(standardTable[userRow][userColumn - 5]) == spin || Integer.parseInt(standardTable[userRow][userColumn - 7]) == spin || 
           Integer.parseInt(standardTable[userRow][userColumn - 9]) == spin || Integer.parseInt(standardTable[userRow][userColumn - 11]) == spin ||
           Integer.parseInt(standardTable[userRow][userColumn - 13]) == spin || Integer.parseInt(standardTable[userRow][userColumn - 15]) == spin || 
           Integer.parseInt(standardTable[userRow][userColumn - 17]) == spin || Integer.parseInt(standardTable[userRow][userColumn - 19]) == spin || 
           Integer.parseInt(standardTable[userRow][userColumn - 21]) == spin || Integer.parseInt(standardTable[userRow][userColumn - 23]) == spin )
        {
          streetColumn = true;
        }
      }
    }
    return streetColumn;
  }
  
  /* This method will check if your bet was a twelve bet and if it won
   * 
   * @author Shane Schroeder
   * 
   * @param int userRow, the row the user is at in the String[][]
   * @param int userColumn, the column the user is at in the String[][]
   * @param int spin, the random generated number
   * @param String[][] standardTable, the template table to check for values
   * 
   * @return boolean twelve, if they won
   * */
  public static boolean twelve(int userRow, int userColumn, int spin, String[][] standardTable)
  {
    boolean twelve = false;
    if(userRow == 8)
    {
      if(userColumn == 5 || userColumn == 12 || userColumn == 19)
      {
        if(userColumn == 5)
        {
          if(12 >= spin && 0 < spin)
          {
            twelve = true;
          }                        
        }
        if(userColumn == 12)
        {
          if(24 >= spin && 12 < spin)
          {
            twelve = true;
          }
        }
        if(userColumn == 19)
        {
          if(36 >= spin && 24 < spin)
          {
            twelve = true;
          }
        }
      }
    }
    return twelve;
  }
  
  /* This method will check if your bet was either of the halves and if it won
   * @author Noah Malinowski
   * @param int userRow, int userColumn, int spin, String[][] standardTable
   */
  public static boolean halves(int userRow, int userColumn, int spin, String[][] standardTable){
    boolean halves = false;
    if(userRow == 9){
      if(userColumn == 4){
        if(spin >= 1 && spin <= 18){
          halves = true;
        }
      } else if(userColumn == 20){
        if(spin >= 19 && spin <= 36){
          halves = true;
        }
      }
    }
    return halves;
  }
  
  /* This method will check if your bet was a even or odd bet and if it won
   * 
   * @author Shane Schroeder
   * 
   * @param int userRow, the row the user is at in the String[][]
   * @param int userColumn, the column the user is at in the String[][]
   * @param int spin, the random generated number
   * @param String[][] standardTable, the template table to check for values
   * 
   * @return boolean evenOdd, if they won
   * */
  public static boolean evenOdd(int userRow, int userColumn, int spin, String[][] standardTable)
  {
    boolean evenOdd = false;
    if(userRow == 9)
    {
      if(userColumn == 7) //even
      {
        if(spin %2 == 0)
        {
          evenOdd = true;
        }
      }
      
      if(userColumn == 17) //odd
      {
        if(spin %2 != 0)
        {
          evenOdd = true;
        }
      }
    }
    return evenOdd;
  }
  
  /* this method checks to see if your bet was either color
   * @author Noah Malinowski
   * @param int userRow, int userColumn, int spin, String[][] standardTable
   */
  public static boolean color(int userRow, int userColumn, int spin, String[][] standardTable){
    boolean color = false;
    if(userRow == 9){
      if(userColumn == 10){
        if(spin == 1 || spin == 3 || spin == 5 || spin == 7 || spin == 9 || spin == 12 || spin == 14 || spin == 16 || spin == 18 || spin == 19 || spin == 21 || spin == 23 || 
           spin == 25 || spin == 27 || spin == 30 || spin == 32 || spin == 34 || spin == 36){
          color = true;
        }
      } else if(userColumn == 13){
        if(spin == 2 || spin == 4 || spin == 6 || spin == 8 || spin == 10 || spin == 11 || spin == 13 || spin == 15 || spin == 17 || spin == 18 || spin == 20 || spin == 22 || 
           spin == 24 || spin == 26 || spin == 28 || spin == 29 || spin == 33 || spin == 35){
          color = true;
        }
      }
    }
    return color;
  }
  
  
  /* This method will check if your bet was a line bet and if it won
   * 
   * @author Shane Schroeder
   * 
   * @param int userRow, the row the user is at in the String[][]
   * @param int userColumn, the column the user is at in the String[][]
   * @param int spin, the random generated number
   * @param String[][] standardTable, the template table to check for values
   * 
   * @return boolean line, if they won
   * */
  public static boolean line(int userRow, int userColumn, int spin, String[][] standardTable)
  {
    boolean line = false;
    if(userRow == 7) //makes sure the user row is in the row where the line bets are
    {
      if(userColumn == 2 || userColumn == 4 || userColumn == 6 || userColumn == 8 || userColumn == 10 || userColumn == 12 || userColumn == 14 || userColumn == 16 || 
         userColumn == 18 || userColumn == 20 || userColumn == 22) //all line bets
      {
        //checks each line bet to see if they won
        if(userColumn == 2) 
        {
          if(1 <= spin && 6 >= spin)
          {
            line = true;
          }
        }
        
        if(userColumn == 4)
        {
          if(4 <= spin && 9 >= spin)
          {
            line = true;
          }
        }
        
        if(userColumn == 6)
        {
          if(7 <= spin && 12 >= spin)
          {
            line = true;
          }
        }
        
        if(userColumn == 8)
        {
          if(10 <= spin && 15 >= spin)
          {
            line = true;
          }
        }
        
        if(userColumn == 10)
        {
          if(13 <= spin && 18 >= spin)
          {
            line = true;
          }
        }
        
        if(userColumn == 12)
        {
          if(16 <= spin && 21 >= spin)
          {
            line = true;
          }
        }
        
        if(userColumn == 14)
        {
          if(19 <= spin && 24 >= spin)
          {
            line = true;
          }
        }
        
        if(userColumn == 16)
        {
          if(22 <= spin && 27 >= spin)
          {
            line = true;
          }
        }
        
        if(userColumn == 18)
        {
          if(25 <= spin && 30 >= spin)
          {
            line = true;
          }
        }
        
        if(userColumn == 20)
        {
          if(28 <= spin && 33 >= spin)
          {
            line = true;
          }
        }
        
        if(userColumn == 22)
        {
          if(31 <= spin && 36 >= spin)
          {
            line = true;
          }
        }
      }
    }
    return line;
  }
  
  /* This method will print results of the bets placed
   * 
   * @author Shane Schroeder
   * 
   * @param boolean straight, if straight bet won
   * @param boolean corner, if corner bet won
   * @param boolean split, if split bet won
   * @param boolean streetColumn, if streetColumn bet won
   * @param boolean twelve, if twelve bet won
   * @param boolean havles, if halves bet won
   * @param boolean evenOdd, if evenOdd bet won
   * @param boolean color, if color bet won
   * @param boolean line, if line bet won
   * @param int userRow, row user is at in String[][]
   * @param int userColumn, column user is at in String[][]
   * @param int bet, the amount the user placed for a bet
   * @param int spin, the number the roulette wheel returned
   * 
   * @return none
   * */
  public static void output(Boolean straight, Boolean corner, Boolean split, Boolean streetColumn, Boolean twelve, Boolean halves, Boolean evenOdd, Boolean color, Boolean line,
                            int userRow, int userColumn, int bet, int spin)
  {
    try
    {
      File file = new File("results.txt");
      
      Scanner sc = new Scanner(file);
      
      String tempString = "";
      while(sc.hasNextLine()){
        tempString = tempString + sc.nextLine() + "!";
      }
      tempString = tempString + "!";
      PrintWriter pw = new PrintWriter(file);
      for(int r = 0; r < tempString.length(); r++){
        if(tempString.charAt(r) == '!'){
          pw.println("");
        } else{
          pw.print(tempString.charAt(r));
        }
      }
      
      int winning = 0;
      if(straight)
      {
        pw.println("Winning Bet Type: Straight");
        pw.println("Number spun: " + spin);
        winning = 35 * bet;
        pw.println("Amount Bet: " + bet);
        pw.println("Amount Won: " + winning);
      }
      
      if(corner)
      {
        pw.println("Winning Bet Type: Corner");
        pw.println("Number spun: " + spin);
        winning = 8 * bet;
        pw.println("Amount Bet: " + bet);
        pw.println("Amount Won: " + winning);
      }
      
      if(split)
      {
        pw.println("Winning Bet Type: Split");
        pw.println("Number spun: " + spin);
        winning = 17 * bet;
        pw.println("Amount Bet: " + bet);
        pw.println("Amount Won: " + winning);
      }
      
      if(streetColumn)
      {
        if(userRow == 7)
        {
          pw.println("Winning Bet Type: Street");
          pw.println("Number spun: " + spin);
          winning = 11 * bet;
          pw.println("Amount Bet: " + bet);
          pw.println("Amount Won: " + winning);
        }
        
        if(userColumn == 24)
        {
          pw.println("Winning Bet Type: Column");
          pw.println("Number spun: " + spin);
          winning = 2 * bet;
          pw.println("Amount Bet: " + bet);
          pw.println("Amount Won: " + winning);
        }
        
      }
      
      if(twelve)
      {
        pw.println("Winning Bet Type: Twelve");
        pw.println("Number spun: " + spin);
        winning = 2 * bet;
        pw.println("Amount Bet: " + bet);
        pw.println("Amount Won: " + winning);
      }
      
      if(halves)
      {
        pw.println("Winning Bet Type: Halves");
        pw.println("Number spun: " + spin);
        winning = bet;
        pw.println("Amount Bet: " + bet);
        pw.println("Amount Won: " + winning);
      }
      
      if(evenOdd)
      {
        if(userColumn == 7)
        {
          pw.println("Winning Bet Type: Even");
        }
        
        if(userColumn == 17)
        {
          pw.println("Winning Bet Type: Odd");
        }
        pw.println("Number spun: " + spin);
        winning = bet;
        pw.println("Amount Bet: " + bet);
        pw.println("Amount Won: " + winning);
      }
      
      if(color)
      {
        if(userColumn == 10)
        {
          pw.println("Winning Bet Type: Red");
        }
        
        if(userColumn == 13)
        {
          pw.println("Winning Bet Type: Black");
        }
        pw.println("Number spun: " + spin);
        winning = bet;
        pw.println("Amount Bet: " + bet);
        pw.println("Amount Won: " + winning);
      }
      
      if(line)
      {
        pw.println("Winning Bet Type: Line");
        pw.println("Number spun: " + spin);
        winning = 5 * bet;
        pw.println("Amount Bet: " + bet);
        pw.println("Amount Won: " + winning);
      }
      
      if(!streetColumn && !straight &&!line &&!split &&!corner &&!twelve &&!color &&!halves &&!evenOdd)
      {
        pw.println("No Winning Bet.");
        pw.println("Number spun: " + spin);
        pw.println("Amount Lost: " + bet);
      }
      
      pw.close();
    }
    
    catch(java.io.FileNotFoundException e)
    {
      System.out.println("File not found.");
    }
    
  }
  
  /* This method will simulate betting on the first half for 10000 spins of the wheel and write results to a file 'single_halves_10000.txt'.
   * 
   * @author Shane Schroeder
   * 
   * @param String[][] standardTable, to use a reference table for checks
   * 
   * @return none
   * */
  public static void point4(String[][] standardTable)
  {
    try
    {
      File file = new File("single_havles_10000.txt");
      PrintWriter pw = new PrintWriter(file);
      int spin = 0;
      boolean halvesA = false;
      for(int i = 0; i < 10000; i++)
      {
        spin = spin();
        halvesA = halves(9, 4, spin, standardTable);
        if(halvesA)
        {
          pw.println("Won.");
          pw.println("Number Spun: " + spin);
          pw.println();
        }
        
        else
        {
          pw.println("Lost.");
          pw.println("Number Spun: " + spin);
          pw.println();
        }
        
      }
      pw.close();
      
    }
    catch(java.io.FileNotFoundException e)
    {
      System.out.println("File could not be found.");
    }
    
  }
 
}