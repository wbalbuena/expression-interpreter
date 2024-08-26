/*
 * Phase 2.1 Parser
 * William Balbuena, Brian Huante
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
class Main {
  public static void main(String[] args) {  
    //Passes the first arguement from the commnad line which is the file name 
    Handler h = new Handler(args[0]);
    try {
      File myObj = new File(args[0]);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        h.nodeMaker(data); // passes each token individually to the nodeMaker() function
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    h.separator(); // starts the process of printing out the AST
  }
}