/*
 * Phase 3.1 Parser
 * William Balbuena, Brian Huante
*/
class Node {    

  /* Manages nodes on the tree.  Each node is a token.
  */
  
  Token token; 
  Node left, right; 
    
  public Node(Token token){ 
      this.token = token; 
      left = null; 
      right = null; 
  } 
}