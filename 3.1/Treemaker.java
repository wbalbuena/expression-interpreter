/*
 * Phase 3.1 Parser
 * William Balbuena, Brian Huante
*/
import java.util.ArrayList;
class Treemaker{
  
  ArrayList<Token> tokens = new ArrayList<Token>();
  int tab = 1;
  int count = 0; //handles which index of the arraylist program is on
  Node root = new Node(null);

  public Treemaker(ArrayList<Token> tokens){
    this.tokens = tokens;
    root = new Node(tokens.get(0));
    make(root);
  }

  /* Starting from the first token create that as the root node.  Go to the next token in the list, and keep reference to last node that you came from in case it is the parent of current token.  Check the spaces of current token, if its a child of last token insert.  Repeat for next token in list.  If not child then break out of recursion until you hit a node that is the parent of the token by again comparing the number of spaces.
  */
  void make(Node parent){
    count++; //everytime make is called, check the next token
    if(count>=tokens.size())
      return;
    //parent.t.spaces is how to access # of spaces
    if(parent.token.spaces + tab == tokens.get(count).spaces)
      insertLeft(parent, tokens.get(count));
    if(parent.token.spaces + tab == tokens.get(count).spaces)
      insertRight(parent, tokens.get(count));
  }
  
  public void insertLeft(Node n, Token t) {
    Node m = new Node(t);
    n.left = m;
    make(m);
  }
  public void insertRight(Node n, Token t) {
    Node m = new Node(t);
    n.right = m;
    make(m);
  }

  /*
  void traversePreOrder(Node node) {
    if (node != null) {
        System.out.print(" " + node.token.value+"\n");
        traversePreOrder(node.left);
        traversePreOrder(node.right);
    }
  }
  */
}