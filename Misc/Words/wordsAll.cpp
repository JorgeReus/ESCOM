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

#define DIRECTORY "./files" 

using namespace std;

queue<string> files;
map<string, int> totalMap;
mutex m, m1;

void calculateWords()
{
    while(!files.empty()) 
    {
        m1.lock();
        string filename = "/" + files.front();
        files.pop();
        m1.unlock();
        Archivo* input = new Archivo(DIRECTORY +filename);
        int nbytes;
        while((nbytes = input->lee()) > 0);
        istringstream iss(input->get_contenido());
        vector<string> tokens{istream_iterator<string>{iss}, istream_iterator<string>{}};
        map<string, int> mapOfWords;
        for(auto const& token: tokens) {
            string s = token;
            // Remove non alpha characters
            for(int i = 0; i <= s.size(); ++i)
            {
                if (s[i] > ' ' && s[i] <= '@')
                s[i] = ' ';
            }
            // Not Found
            if(mapOfWords.find(s) != mapOfWords.end()){  
                mapOfWords[s] += 1;
            }
            else 
                mapOfWords[s] = 1;
        }
        m.lock();
        // cout << "Name: " <<input->nombreArchivo << ": "; 
        // cout << "Tokens size: " <<tokens.size() << "- ";
        // cout << "Content Length: " << strlen(input->get_contenido()) << "- ";
        // cout << "Map size: " <<mapOfWords.size() << endl;      
        delete input;
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
    if (argc != 2)
    {
        cout << "Usage: ./words num_threads" << endl;
        exit(1);
    }
    locale loc("es_MX.utf8");
    int n = atoi(argv[1]);
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
    while ((dir = readdir(directory)) != NULL){       
        if (strcmp(dir->d_name, ".") && strcmp(dir->d_name, "..")) {
            files.push(dir->d_name);
        }
    }
    vector<thread> threads;
    int i;
    for(i=0; i < n; i++)
    {
        thread th(calculateWords);
        threads.push_back(move(th));
    }
    for(i=0; i < n; i++)
    {
        threads[i].join();
    }
    // map<string, int>::iterator it = totalMap.find("de");
    // cout << it->first << ":" << it->second << endl;
    multimap<int, string> finalMap;
    for(auto const& element: totalMap)      
        finalMap.insert(pair<int, string>(element.second, element.first));
    i=0;
    for(multimap<int, string>::reverse_iterator it = finalMap.rbegin(); it != finalMap.rend(); it++)  {
        if (i > 500){
            break;
        }
        std::cout << "   " <<it->second << " :: " << it->first << std::endl;
        i++;
    }    
    exit(0);
}