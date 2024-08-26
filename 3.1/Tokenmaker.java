/*
 * Phase 3.1 Parser
 * William Balbuena, Brian Huante
*/
import java.util.ArrayList;
class Tokenmaker{
  
  ArrayList<Token> tokens = new ArrayList<Token>(); //all tokens stored in an ArrayList
  
  void addNode(String line)
  {
    String value = "";
    String type = "";
    int count = 0;

    for(int i=0;i<line.length();i++)
    {
      if(!Character.isWhitespace(line.charAt(i)))
        break;
      else
        count++;
    }
    
    for(int i = 0; i<line.length(); i++)
    {
      if(line.substring(i,i+1).equals(":"))
      {
        value = line.substring(0,i);
        type = line.substring(i+2,line.length());
      }
    }
    Token n = new Token(value, type, count);
    //System.out.println("Adding token of "+value+" "+type+" "+count);
    tokens.add(n);
  }

  ArrayList<Token> getNodes(){
    return tokens;
  }
}