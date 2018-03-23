#include <iostream>
#include <map>
#include <stdio.h>
#include <string>
#include <iterator>
#include <sys/types.h> 
#include <dirent.h>
#include <unistd.h>
#include <cstdlib>
#include <queue>
#include <algorithm>
#include <cstring>
#include <fcntl.h>
#include <string>
#include "file.h"
#include <vector>
#include <sstream>
#include <locale>
#include <set>


using namespace std;
int main(int argc, char *argv[])
{   
    locale loc("es_MX.utf8");
    if (argc != 2)
        exit(1);
    queue<string> files;
    DIR *directory = opendir("./files");
    struct dirent *dir;
    while ((dir = readdir(directory)) != NULL)
        if (strcmp(dir->d_name, ".") && strcmp(dir->d_name, ".."))
            files.push(dir->d_name);
    // Declaring the type of Predicate that accepts 2 pairs and return a bool
    typedef std::function<bool(std::pair<std::string, int>, std::pair<std::string, int>)> Comparator;
    // Defining a lambda function to compare two pairs. It will compare two pairs using second field
    Comparator compFunctor = [](std::pair<std::string, int> elem1 ,std::pair<std::string, int> elem2)
    {
        return elem1.second > elem2.second;
    };
    string filename = files.front();
    Archivo input("./files/" + filename);
    int nbytes;
    while((nbytes = input.lee(BUFSIZ)) > 0);
    istringstream iss(input.get_contenido());
    vector<string> tokens{istream_iterator<string>{iss}, istream_iterator<string>{}};
    map<string, int> mapOfWords;
    for(auto const& token: tokens) {
        string s = token;
        // Remove non alpha characters
        for(int i = 0; i <= s.size(); ++i)
        {
            //if(!isalpha(s[i]))
            if ((s[i] > ' ' && s[i] <= '@') || s[i]== *u8"¿")
                s[i] = ' ';
        }
        std::string::iterator end_pos = remove(s.begin(), s.end(), ' ');
        s.erase(end_pos, s.end());
        // Not Found
        if(mapOfWords.find(s) != mapOfWords.end()){          
            mapOfWords[s]++;
        }
        else 
            mapOfWords[s] = 1;
    }
    set<std::pair<string, int>, Comparator> setOfWords(
            mapOfWords.begin(), mapOfWords.end(), compFunctor);
    int i=0; 
    for (std::pair<std::string, int> element : setOfWords){
        if (i <= 40){
            std::cout << element.first << " :: " << element.second << std::endl;
            i++;
        }
    }
    return 0;
}