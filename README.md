# Description
This is an expression interpreter comprising of three parts: a scanner, a parser, and an evaluator that are connected by a shell script "BashScript.sh"
## Scanner
Written in C++.  Processes the stream of characters using Regular Expressions to tokenize the expression.  Classifies the tokens as "identifier," "number," or "symbol."  Outputs a "tokenlist" into a text file line by line.
## Parser
Written in Java.  Processes the outputted text file from the scanner and creates an object out of each one and adds them to an ArrayList.  Outputs an "ast" into a text file in preorder traversal.
## Evaluator
Written in Java.  Processes the outputted text file from the parser and creates a Stack of token objects.  Checks the top 3 tokens of the stack and evaluates them if they comprise an expression.
# How to Run
To use bash script, script must be in the same folder as 1.2, 2.1, and 3.1

The bash script takes 2 arguments
To run the bash script, the arguments must be name of the input file and output file without the .txt extension

The script will output 3 files ending in "x_tokenlist," "x_ast," and "x_output."  The "x" signifying the name of the output file specified.

Our program was created and tested using repl.it.

file2.txt is an example input file.

## Example:
```
sh BashScript.sh file2 output
```
