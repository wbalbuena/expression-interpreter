#!/bin/bash
# This script will take one argument as an input file for 1.2.  It will then take the output of 1.2 and input it into 2.1, it will then take that output and input it into 3.1.
# Assumes script is in a folder with folders of the 1.2, 2.1, and 3.1.
# Assumes 2 text file arguments are run with script, the first is the input file accessed as $1, the second is the name of the output file accessed as $2

# Call 1.2
cd 1.2
g++ main.cpp
mv ../$1.txt .
./a.out $1.txt
cd ..

mv 1.2/$1.txt .
cp 1.2/$1_ans.txt $2_tokenlist.txt

# Call 2.1
cd 2.1
mv ../1.2/$1_ans.txt .
javac *.java
java Main $1_ans.txt
cd ..
mv 2.1/$1_ans.txt .
cp 2.1/$1_ans_parsed.txt $2_ast.txt

# Call 3.1
cd 3.1
mv ../2.1/$1_ans_parsed.txt .
javac *.java
java Main $1_ans_parsed.txt $2_output.txt
cd ..

mv 3.1/$2_output.txt .
mv 3.1/$1_ans_parsed.txt .

rm $1_ans.txt
rm $1_ans_parsed.txt

echo -e "\nFinished running script."
