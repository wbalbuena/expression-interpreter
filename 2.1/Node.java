/*
 * Phase 2.1 Parser
 * William Balbuena, Brian Huante
*/
class Node {    
  String value;
  String token;
  Node left, right; 
        
  Node(String value, String token){ 
    this.value = value;
    this.token = token;
    left = null; 
    right = null; 
  } 
} 