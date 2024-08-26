/*
 * Authors: William Balbuena, Brian Huante Lopez
 * PROJECT PHASE 1.2
 */

#include <iostream>
#include <regex>
#include <ctype.h>
#include <stdio.h>
#include <cstring>
#include <fstream>
#include <sstream>
using namespace std;

void tokenScanner(string line, ofstream& writeFile) {
    //writeFile << "Line: " << line << endl;

    // cout << "--- SIZE: ";
    // cout << line.length();
    // cout << " ---\n";

    string token;
    bool toke = false;
    bool error = false;

    for (int i = 0; i <= line.length() && error == false; i++) {

        toke = false;
        char x = line[i];

        // cout << "--- CURRENT CHARACTER: ";
        // cout << x;
        // cout << " ---\n";

        //if bad character detected no longer examine string
        // if(error==true){
        //   break;
        // }

        //reaching a white space or end of line
        if (isspace(x) || i == line.length()) {
            //cout << "--- REACHED WHITE SPACE OR END OF TOKEN ---\n";

            //if statement with regex_match that identifies tokens
            if (regex_match(token, regex("(if)|(then)|(else)|(endif)|(while)|(do)|(endwhile)|(skip)"))) {
                writeFile << token + ": keyword\n";
                token = "";
            }
            else if (regex_match(token, regex("([a-zA-Z])([a-zA-Z0-9])*"))) {
                writeFile << token + ": identifier\n";
                token = "";
            }
            else if (regex_match(token, regex("[0-9]+"))) {
                writeFile << token + ": number\n";
                token = "";
            }
            else if (regex_match(token, regex("(:=)|[\\+ | \\* | \\( | \\) | \\- | \\; | /]"))) {
                writeFile << token + ": symbol\n";
                token = "";
            }
            //if token does not fit above criteria
            else {
                //tokened is activated when a pattern below is recognized and prevents repeat output from the rest of the for loop
                bool tokened = false;
                string errorsymbol;
                //looks at each character in the current token
                for (int j = 0; j <= token.length() && tokened == false; j++) {
                    // if(tokened==true)
                    //   break;

                    //cout << "TOKEN SUBSTR: "+token.substr(0,j) + "\n";

                    //if there's any invalid symbols it examines the remaining token before the symbol then prints error reading afterwards
                    if (regex_match(token.substr(0, j), regex("([a-zA-Z0-9]|(:=?)|[\\+ | \\* | \\( | \\) | \\- | \\; | /])*"))) {
                    }
                    else {
                        errorsymbol = token.substr(j - 1, 1);
                        token = token.substr(0, j - 1);
                        error = true;
                        //INSERT if statement for keywoard
                        if (regex_match(token, regex("(if)|(then)|(else)|(endif)|(while)|(do)|(endwhile)|(skip)")))
                            writeFile << token + ": keyword\n";
                        else if (regex_match(token, regex("([a-zA-Z])([a-zA-Z0-9])*")))
                            writeFile << token + ": identifier\n";
                        else if (regex_match(token, regex("[0-9]+")))
                            writeFile << token + ": number\n";
                        else if (regex_match(token, regex("(:=)|[\\+ | \\* | \\( | \\) | \\- | \\; | /]")))
                            writeFile << token + ": symbol\n";
                    }

                    //below if statements print out the first token, the rest of the string is sent back through the loop as the token to be scanned may contain more than one token

                    //number and anything
                    if (regex_match(token.substr(0, j), regex("[0-9]+([a-zA-Z]|(:=?)|[\\+ | \\* | \\( | \\) | \\- | \\; | /])+"))) {
                        writeFile << token.substr(0, j - 1) + ": number\n";
                        tokened = true;
                        token = token.substr(j - 1, 100);
                        i--; //needed so the token is looked at again
                    }
                    //keyword and symbol
                    else if (regex_match(token.substr(0, j), regex("((if)|(then)|(else)|(endif)|(while)|(do)|(endwhile)|(skip))((:=?)|[\\+ | \\* | \\( | \\) | \\- | \\; | /])"))) {
                        writeFile << token.substr(0, j - 1) + ": keyword\n";
                        tokened = true;
                        token = token.substr(j - 1, 100);
                        i--;
                    }
                    //identifier and symbol
                    else if (regex_match(token.substr(0, j), regex("[a-zA-Z]+[0-9]*((:=?)|[\\+ | \\* | \\( | \\) | \\- | \\; | /])"))) {
                        writeFile << token.substr(0, j - 1) + ": identifier\n";
                        tokened = true;
                        token = token.substr(j - 1, 100);
                        i--;
                    }
                    //symbol and anything after
                    else if (regex_match(token, regex("([\\+ | \\* | \\( | \\) | \\- | \\; | /])([a-zA-Z0-9]|(:=?)|[\\+ | \\* | \\( | \\) | \\- | \\; | /])+"))) {
                        writeFile << token.substr(0, 1) + ": symbol\n";
                        token = token.substr(1, 100);
                        tokened = true;
                        i--;
                    }
                    else if (regex_match(token, regex("(:=)([a-zA-Z0-9]|(:=?)|[\\+ | \\* | \\( | \\) | \\- | \\; | /])+"))) {
                        writeFile << token.substr(0, 2) + ": symbol\n";
                        token = token.substr(2, 100);
                        tokened = true;
                        i--;
                    }

                    if (error == true) {
                        writeFile << "ERROR READING \"" + errorsymbol + "\"\n";
                    }
                }
            }
            toke = true;
        }

        if (toke == false)
            token = token + x;

        //cout << "--- CURRENT TOKEN: "+token+" ---\n";
    }

    writeFile << "\n";
}

int main(int argc, char* argv[]) {
    string line;
    string fileName;
    ifstream readFile;
    ofstream writeFile;

    for (int i = 1; i < argc; i++) {
        readFile.open(argv[i]);

        fileName = argv[i];
        fileName.erase(fileName.end() - 4, fileName.end());
        fileName += "_ans.txt";
        writeFile.open(fileName.c_str());

        while (getline(readFile, line)) {
            tokenScanner(line, writeFile);
        }
        readFile.close();
        writeFile.close();
    }

    return 0;
}