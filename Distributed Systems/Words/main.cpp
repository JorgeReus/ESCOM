#include <iostream>
#include <map>
#include <stdio.h>
#include <cctype>
#include <iterator>
#include <sys/types.h> 
#include <dirent.h>
#include <cstdlib>
#include <cstring>
#include <fcntl.h>
#include "file.h"
#include <vector>
#include <sstream>
#include <locale>
#include <set>
#include <algorithm>
#include <thread>
#include <mutex>
#include <queue>

using namespace std;

queue<string> files;
char *DIRECTORY;
map<string, int> totalMap;
mutex m, m1;

void calculateWords()
{
    while(!files.empty()) 
    {
        // get one file out of the string queue
        m1.lock();
        string filename = "/" + files.front();
        files.pop();
        m1.unlock();
        // Create a new file based on the filename
        Archivo* input = new Archivo(DIRECTORY +filename);
        int nbytes;
        // Read the file
        while((nbytes = input->lee()) > 0);
        istringstream iss(input->get_contenido());
        // Tokenize the string
        vector<string> tokens{istream_iterator<string>{iss}, istream_iterator<string>{}};
        map<string, int> mapOfWords;
        // Process Tokens
        for(auto const& token: tokens) {
            string s = token;
            // Remove non alpha characters
            for(int i = 0; i <= s.size(); ++i)
            {
                if (s[i] > ' ' && s[i] <= '@')
                s[i] = ' ';
            }
            // If found increment the count
            if(mapOfWords.find(s) != mapOfWords.end())
                mapOfWords[s] += 1;
            // If not add a new entry
            else 
                mapOfWords[s] = 1;
        }
        m.lock();
        delete input;
        // Add the entries into the total map
        for(auto const& element: mapOfWords) {
            if(totalMap.find(element.first) != totalMap.end()){          
                totalMap[element.first] += element.second;
            }
            else 
                totalMap[element.first] = 0;
        }
        m.unlock();
    } 
    return;
}


int main(int argc, char *argv[])
{   
    if (argc != 3)
    {
        cout << "Usage: ./main num_threads directory" << endl;
        exit(1);
    }
    locale loc("es_MX.utf8");
    int n = atoi(argv[1]);
    DIRECTORY = argv[2];
    if (n > 8)
    {
        cout << "A maximum of 8 threads are allowed" << endl;
        exit(1);
    }
    DIR *directory = opendir(DIRECTORY);
    if(directory == NULL)
    {
        cout << "Folder" << DIRECTORY << " not found" << endl;
        exit(1);
    }
    struct dirent *dir;
    while ((dir = readdir(directory)) != NULL)       
        if (strcmp(dir->d_name, ".") && strcmp(dir->d_name, ".."))
            files.push(dir->d_name);    
    vector<thread> threads;
    int i;
    // 1 thread for each file
    for(i=0; i < n; i++)
    {
        thread th(calculateWords);
        threads.push_back(move(th));
    }
    for(i=0; i < n; i++)
        threads[i].join();
    multimap<int, string> finalMap;
    for(auto const& element: totalMap)      
        finalMap.insert(pair<int, string>(element.second, element.first));
    i=0;
    for(multimap<int, string>::reverse_iterator it = finalMap.rbegin(); it != finalMap.rend(); it++)  {
        if (i > 500)
            break;
        std::cout << "   " <<it->second << " :: " << it->first << std::endl;
        i++;
    }    
    exit(0);
}