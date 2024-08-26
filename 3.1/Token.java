/*
 * Phase 3.1 Parser
 * William Balbuena, Brian Huante
*/
class Token {    

  /* Token contains the data for each token in the list.  Including its value, type, and amount of spaces for AST purposes.
  */
  
  String value;
  String type;
  int spaces; 
  
  Token(String value, String type, int spaces){ 
    this.value = value;
    this.type= type;
    this.spaces = spaces;
  } 

  //for when new tokens are created from evaluation
  Token(String value, String type){ 
    this.value = value;
    this.type= type;
  } 
} 