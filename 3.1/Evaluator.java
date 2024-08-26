/*
 * Phase 3.1 Parser
 * William Balbuena, Brian Huante
*/
import java.util.*;
import java.io.*;
import java.io.IOException;

class Evaluator{

  Stack<Token> stk = new Stack<>();
  Node root = new Node(null);
  private String output_filename;
  
  public Evaluator(Node root){
    this.root = root;
  }

/* Add to stack in pre-order traversal = root, left, right.

Notes from class regarding 3.1:
- pre_order(t)
- If t is Null return
- fancy_push(t.root, st) //pushes onto stack and also - evaluates the top 3 elements
- pre_order(t.left)
- pre_order(t.mid)
- pre_order(t.right)
*/
  
  void evaluate(Node node){
    if (node != null) {
      //System.out.println("PUSHING: "+node.token.value);
      stk.push(node.token); //push token onto stack
      checkStk(); //checkStk() will check top 3 elements
      evaluate(node.left); //repeat for left child
      evaluate(node.right); //repeat for right child
    }
  }
  
  void checkStk(){
    if(stk.size()<3) //does not check if 3 elements arent available
      return;

    //pop the top 3 elements and keep them stored in temp variables.  if not able to be evaluated push them back.

    Token temp1 = stk.pop(); //push back third
    Token temp2 = stk.pop(); //push back second
    Token temp3 = stk.pop(); //push back first

    //for evaluation, third element from top must be symbol, then second and first elements are numbers
    if(temp1.type.equals("number") && temp2.type.equals("number") && temp3.type.equals("symbol")){

      String str1 = temp1.value.replaceAll("\\s", "");
      String str2 = temp2.value.replaceAll("\\s", "");
      
      int num1 = Integer.parseInt(str1);
      int num2 = Integer.parseInt(str2);
      int num3 = 0;

      //System.out.println("num1 = "+num1);
      //System.out.println("num2 = "+num2);

      //System.out.println("temp3.value = "+temp3.value);
      
      //plus
      if(temp3.value.replaceAll("\\s", "").equals("+")){
        num3 = num2 + num1;
      }
      //minus
      else if(temp3.value.replaceAll("\\s", "").equals("-")){
        num3 = num2 - num1;
        if(num3<0)
          num3=0;
      }
      //times
      else if(temp3.value.replaceAll("\\s", "").equals("*")){
        num3 = num2 * num1;
      }
      //division
      else if(temp3.value.replaceAll("\\s", "").equals("/")){
        num3 = num2 / num1;
      }

      //System.out.println("num3 ="+num3);
      Token temp4 = new Token(Integer.toString(num3), "number");
      stk.push(temp4);
      checkStk(); //checks again after pushing a new number
    }
    else{
      //System.out.println("PUSHING: "+temp3.value);
      stk.push(temp3);
      //System.out.println("PUSHING: "+temp2.value);
      stk.push(temp2);
      //System.out.println("PUSHING: "+temp1.value);
      stk.push(temp1);
    }
  }
  
  public void setOutputName(String output_filename){
    this.output_filename = output_filename;
  }

  public String getOutputName() {
    return output_filename;
  }

  void output(){
    try (FileWriter fw = new FileWriter(output_filename, true);
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter out = new PrintWriter(bw)) {
        out.println("Output: "+stk.pop().value+"\n");
    } catch (IOException e) {
        System.out.println("Couldn't print to file.");
    }
  }
}