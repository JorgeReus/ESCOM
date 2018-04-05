#include <iostream>
#include <map>
#include <stdio.h>
#include <sys/types.h> 
#include <dirent.h>
#include <cstring>
#include <fcntl.h>
#include <string>
#include "file.h"
#include <vector>
#include <unistd.h>
#include <thread>

using namespace std;

#include <queue>
#include <mutex>

#define DIRECTORY "./files" 


int main(int argc, char *argv[])
{   
    DIR *directory = opendir(DIRECTORY);
    if(directory == NULL)
    {
        cout << "Folder" << DIRECTORY << " not found" << endl;
        exit(1);
    }
    struct dirent *dir;
    vector<string> files;
    while ((dir = readdir(directory)) != NULL){       
        if (strcmp(dir->d_name, ".") && strcmp(dir->d_name, "..")) {
            string filename = DIRECTORY;
            filename += "/";
            filename += dir->d_name;
            files.push_back(filename);
        }
    }
    for(int i=0; i<files.size(); i++) {
        int nbytes = 0;
        Archivo input(files[i]);
        while((nbytes = input.lee(BUFSIZ)) > 0);
        cout << strlen(input.get_contenido()) << endl;
    }
    // for (it = files.begin(); it != files.end(); it++){
    //     cout << it->nombreArchivo << ": ";
    //     cout << it->get_contenido() << endl << endl;
    // }
    // map<string, int>::iterator it = totalMap.find("de");
    // cout << it->first << ":" << it->second << endl;
    // multimap<int, string> finalMap;
    // for(auto const& element: totalMap)      
    //     finalMap.insert(pair<int, string>(element.second, element.first));
    // i=0;
    // for(multimap<int, string>::reverse_iterator it = finalMap.rbegin(); it != finalMap.rend(); it++)  {
    //     if (i > 500){
    //         break;
    //     }
    //     std::cout << "   " <<it->second << " :: " << it->first << std::endl;
    //     i++;
    // }    

    exit(0);
}