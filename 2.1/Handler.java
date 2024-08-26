/*
 * Phase 2.1 Parser
 * William Balbuena, Brian Huante
*/
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Handler{

  // ignore parenthesis in ast, however it affects   precedence
  // searches for symbols in order of predence, once symbol is found it becomes value of the next node
  // if symbol of lower precedence is before symbol of higher precedence in line, that symbol becomes the node on lower precedence's left
  
  ArrayList<Node> nodes = new ArrayList<Node>();
  File myObj;
  FileWriter myWriter;

  public Handler(String fileName){
    //Create new string name from previous name of the file
    //Format for new file is (file name)_parsed.txt
    String newFileName = fileName.substring(0, fileName.length()-4) + "_parsed.txt";
    try {
      //creates a new file from newFileName
      myObj = new File(newFileName);
      //let user know if file has been created or that the output already exist
      if (myObj.createNewFile()) {
        //System.out.println("File created: " + myObj.getName());
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    //Creates new FileWriter
    try {
      myWriter = new FileWriter(newFileName);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  
  // tab counter used to distinguish between levels of the tree
  int tabCounter = 0;

  //creates nodes from
  void nodeMaker(String line)
  {
    //iterates through each list of tokens
    String value = "";
    String token = "";
    for(int i = 0; i<line.length(); i++)
    {
      if(line.substring(i,i+1).equals(":"))
      {
        value = line.substring(0,i); // everything before the : is value
        token = line.substring(i+2,line.length()); // everything after the : is token
      }
    }
    //create new node and add it to array list
    Node n = new Node(value, token);
    nodes.add(n);
  }

  void separator(){
    //System.out.println("---SEPARATOR()---");
    boolean symFound = false;

    //keeps track of the index of the parenthesis
    int starPar = -1;
    int cloPar = -1;

    //keeps track of the start and end for the various for loops
    int edge1 = 0;
    int edge2 = nodes.size();

    //searches for the open and closed parenthesis
    for(int i = 0; i<nodes.size(); i++){
      Node n = nodes.get(i);
      if(n.value.equals("(")){
        starPar = i; //index of open parenthesis
        for(int j = i; j<nodes.size(); j++){
          n = nodes.get(j);
          if(n.value.equals(")")){
            cloPar = j; //index of closed parenthesis
            break;
          }
        }
      }
    }

    //System.out.println("starPar = "+starPar+", cloPar ="+cloPar);

    //if there is a parenthesis in the line
    if(cloPar != -1 && starPar != -1){
      //System.out.println("PARENTHESIS RECOGNIZED");

      //from the start to the open parenthesis
      edge1 = 0;
      edge2 = starPar;

      //System.out.println("LEFT SIDE OF PARENTHESIS");
      //scans both sides of the parenthesis starting with the left side for a symbol based on precedence, if symbol is on left of parenthesis that symbol is printed and calls left(), whatever is in parenthesis becomes the right. 
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("+")){ //symbol is +
         //System.out.println("SYMBOL FOUND AT nodes.get("+i+")");
          print(i);
          symFound=true;
          tabCounter++; //increase tab size
          left(edge1,i-1); //search the left side of the symbol
        }
      }
      for(int i = edge1; i<edge2; i++){
        if(symFound==true) //if higher precedence symbol has been detected, ignore
          break;
        Node n = nodes.get(i);
        if(n.value.equals("-")){ //symbol is a -
          //System.out.println("SYMBOL FOUND AT nodes.get("+i+")");
          print(i);
          symFound=true;
          tabCounter++;
          left(edge1,i-1);
        }
      }
      for(int i = edge1; i<edge2; i++){
        if(symFound==true) //if higher precedence symbol has been detected, ignore
          break;
        Node n = nodes.get(i);
        if(n.value.equals("/")){ //symbol is a /
          //System.out.println("SYMBOL FOUND AT nodes.get("+i+")");
          print(i);
          symFound=true;
          tabCounter++;
          left(edge1,i-1);
        }
      }
      for(int i = edge1; i<edge2; i++){
        if(symFound==true) //if higher precedence symbol has been detected, ignore
          break;
        Node n = nodes.get(i);
        if(n.value.equals("*")){ //symbol is a *
          //System.out.println("SYMBOL FOUND AT nodes.get("+i+")");
          print(i);
          symFound=true;
          tabCounter++;
          left(edge1,i-1);
        }
      }

      //search the right side of the parenthesis
      edge1 = cloPar+1;
      edge2 = nodes.size();
  
      symFound = false;

      //System.out.println("RIGHT SIDE OF PARENTHESIS");
      //searches for symbol based on precedence.  if symbol is on right of parenthesis that symbol is printed and calls right(), whatever is in parenthesis becomes the left.
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("+")){
          //System.out.println("SYMBOL FOUND AT nodes.get("+i+")");
          print(i);
          symFound=true;
          tabCounter++;
          right(i+1,edge2-1); //searches the right side of the symbol
          //tabCounter--;
        }
      }
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("-")){
          //System.out.println("SYMBOL FOUND AT nodes.get("+i+")");
          print(i);
          symFound=true;
          tabCounter++;
          right(i+1,edge2-1);
          //tabCounter--;
        }
      }
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("/")){
          //System.out.println("SYMBOL FOUND AT nodes.get("+i+")");
          print(i);
          symFound=true;
          tabCounter++;
          right(i+1,edge2-1);
          //tabCounter--;
        }
      }
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("*")){
          //System.out.println("SYMBOL FOUND AT nodes.get("+i+")");
          print(i);
          symFound=true;
          tabCounter++;
          right(i+1,edge2-1);
          //tabCounter--;
        }
      }

      //System.out.println("INSIDE PARENTHESIS");
      //after the left and right sides of the parenthesis are examined we print out the inside of the parenthesis
      symFound = false;

      //boundaries are within parenthesis
      edge1 = starPar + 1;
      edge2 = cloPar;
      //System.out.println("edge1 = "+edge1+", edge2 = "+edge2);

      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("+")){
          //System.out.println("SYMBOL FOUND AT nodes.get("+i+")");
          print(i);
          symFound=true;
          tabCounter++;
          left(edge1,i-1); //search left side of symbol
          right(i+1,edge2-1); //search right side of symbol
        }
      }
      
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("-")){
          print(i);
          symFound=true;
          tabCounter++;
          left(edge1,i-1);
          right(i+1,edge2-1);
        }
      }
      
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("/")){
          print(i);
          symFound=true;
          tabCounter++;
          left(edge1,i-1);
          right(i+1,edge2);
        }
      }
      
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("*")){
          print(i);
          symFound=true;
          tabCounter++;
          left(edge1,i-1);
          right(i+1,edge2-1);
          tabCounter--;
        }
      }
    }
    //when no parenthesis are found on the line, the line is treated normally
    else{
      //searches for symbols in order of precedence
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("+")){
          //System.out.println("SYMBOL FOUND AT nodes.get("+i+")");
          print(i);
          symFound=true;
          tabCounter++;
          left(0,i-1); //from beginning of line to left of the symbol
          right(i+1,nodes.size()-1); //from right of symbol to end of line
        }
      }
      
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("-")){
          print(i);
          symFound=true;
          tabCounter++;
          left(0,i-1);
          right(i+1,nodes.size()-1);
        }
      }
      
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("/")){
          print(i);
          symFound=true;
          tabCounter++;
          left(0,i-1);
          right(i+1,nodes.size()-1);
        }
      }
      
      for(int i = edge1; i<edge2; i++){
        if(symFound==true)
          break;
        Node n = nodes.get(i);
        if(n.value.equals("*")){
          print(i);
          symFound=true;
          tabCounter++;
          left(0,i-1);
          right(i+1,nodes.size()-1);
        }
      }
    }
    try {
      myWriter.close(); //close writer
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  //for the left side of the symbol
  void left(int left, int right){
    //System.out.println("---LEFT()---");
    //System.out.println("left = "+left+", right = "+right);
    //once the right side hits 0 or becomes smaller than the left value, left() ends
    if(right<0 || right<left)
      return;
    boolean symFound = false;

    //searches for symbol in order of precedence
    for(int j = left; j<=right; j++){
      if(symFound==true)
        break;
      Node n = nodes.get(j);
      if(n.token.equals("symbol")&& n.value.equals("+")){ //symbol is +
        print(j);
        tabCounter++; //increment tab counter to signify the leafs
        left(left,j-1); //searches left side of symbol
        right(j+1,right); // searches right side of symbol
        symFound=true;
        tabCounter--; //decrement tab counter once out of the leaf
      }
    }
    for(int j = left; j<=right; j++){
      if(symFound==true)
        break;
      Node n = nodes.get(j);
      if(n.token.equals("symbol")&& n.value.equals("-")){ //symbol is -
        print(j);
        tabCounter++;
        left(left,j-1);
        right(j+1,right);
        symFound=true;
        tabCounter--;
      }
    }
    for(int j = left; j<=right; j++){
      if(symFound==true)
        break;
      Node n = nodes.get(j);
      if(n.token.equals("symbol")&& n.value.equals("/")){ //symbol is /
        print(j);
        tabCounter++;
        left(left,j-1);
        right(j+1,right);
        symFound=true;
        tabCounter--;
      }
    }
    for(int j = left; j<=right; j++){
      if(symFound==true)
        break;
      Node n = nodes.get(j);
      if(n.token.equals("symbol")&& n.value.equals("*")){ //symbol is *
        print(j);
        tabCounter++;
        left(left,j-1);
        right(j+1,right);
        symFound=true;
        tabCounter--;
      }
    }
    if(!symFound)
      print(right); //if no symbol was found, print the next value
    //System.out.println("EXITING LEFT()");
  }

  //for the right side of the symbol
  void right(int left, int right){
    //System.out.println("---RIGHT()---");
    //System.out.println("left = "+left+", right = "+right);
    if(left>=nodes.size() || right < left)
      return;
    boolean symFound = false;
    for(int j = left; j<=right; j++){
      if(symFound==true)
        break;
      Node n = nodes.get(j);
      if(n.token.equals("symbol")&& n.value.equals("+")){
        print(j);
        tabCounter++;
        left(left,j-1);
        right(j+1,right);
        symFound=true;
        tabCounter--;
      }
    }
    for(int j = left; j<=right; j++){
      if(symFound==true)
        break;
      Node n = nodes.get(j);
      if(n.token.equals("symbol")&& n.value.equals("-")){
        print(j);
        tabCounter++;
        left(left,j-1);
        right(j+1,right);
        symFound=true;
        tabCounter--;
      }
    }
    for(int j = left; j<=right; j++){
      if(symFound==true)
        break;
      Node n = nodes.get(j);
      if(n.token.equals("symbol")&& n.value.equals("/")){
        print(j);
        tabCounter++;
        left(left,j-1);
        right(j+1,right);
        symFound=true;
        tabCounter--;
      }
    }
    for(int j = left; j<=right; j++){
      if(symFound==true)
        break;
      Node n = nodes.get(j);
      if(n.token.equals("symbol")&& n.value.equals("*")){
        print(j);
        tabCounter++;
        left(left,j-1);
        right(j+1,right);
        symFound=true;
        tabCounter--;
      }
    }
    if(!symFound)
      print(left); //if no symbol was found, print the next value
    //System.out.println("EXITING RIGHT()");
  }

  void print(int i){
    for(int j = 0; j<tabCounter; j++){
      try {
        myWriter.write("\t");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
    try {
      myWriter.write(nodes.get(i).value+": "+nodes.get(i).token+"\n");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
