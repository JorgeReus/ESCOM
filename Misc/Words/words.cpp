#include <iostream>
#include <map>
#include <string>
#include <iterator>
#include <sys/types.h> 
#include <dirent.h>
#include <unistd.h>
#include <cstdlib>


int main(int argc, char *argv[])
{   
    if (argc != 2) {
        exit(1);
    }
    DIR *directory = opendir("./files");
    struct dirent *dir;
    while ((dir = readdir(directory)) != NULL) {
        std::cout <<  dir->d_name << std::endl;
    }
    //std::map<std::string, int> mapOfWords;
    // mapOfWords["sun"] = 3;
    // Will replace the value of already added key i.e. earth
    // mapOfWords["earth"] = 4;
    // Iterate through all elements in std::map
    // std::map<std::string, int>::iterator it = mapOfWords.begin();
    /*while(it != mapOfWords.end())
    {
        std::cout<<it->first<<" :: "<<it->second<<std::endl;
        it++;
    }
    // Check if insertion is successful or not
    if(mapOfWords.insert(std::make_pair("earth", 1)).second == false)
    {
        std::cout<<"Element with key 'earth' not inserted because already existed"<<std::endl;
    }
    // Searching element in std::map by key.
    if(mapOfWords.find("sun") != mapOfWords.end())
        std::cout<<"word 'sun' found"<<std::endl;
    if(mapOfWords.find("mars") == mapOfWords.end())
    std::cout<<"word 'mars' not found"<<std::endl;*/    
    return 0;
}