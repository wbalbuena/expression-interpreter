/*
 * Phase 3.1 Parser
 * William Balbuena, Brian Huante
*/

/* --------------- PSEUDOCODE ---------------
Count how many spaces a single tab is (hard code this into program).  Read in AST text file.  Iterate through entire list of tokens.  Track how many tabs the current token has.  If the next token has 4 more tabs than current token then it is a leaf of the current node.  If the next token has less tabs then it is not related to current node.  Add these tokens into a tree.

Once tokens are all added to a tree, traverse them in pre-order.  For each node, push each node onto a stack and check the top 3 elements of the stack.  If the top two elements are numbers and the third one is a symbol then evaluate by popping the top 3 elements and pushing the new value onto the stack.
---------------------------------------------*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
class Main {
  public static void main(String[] args) {  

    if (args.length != 2) {
      System.exit(0);
    }
    
    Tokenmaker t = new Tokenmaker();

    // 3.1 should take input from 2.1, which should have taken input from 1.2
    // 1.2 --> 2.1 --> 3.1
    
    try {
      File myObj = new File(args[0]);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        t.addNode(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    Treemaker tr = new Treemaker(t.getNodes()); //creates a tree from the tokens inputted
    Evaluator e = new Evaluator(tr.root); //takes the root from the tree made in Treemaker
    e.setOutputName(args[1]); //creates file and filewriter to write output
    e.evaluate(e.root); //starts evaluating from the root node of the tree
    e.output(); //pops the final value from the stack
  }
}