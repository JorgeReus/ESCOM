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
#include <unistd.h>
#include <thread>

using namespace std;

#include <queue>
#include <mutex>

#define DIRECTORY "./files" 

template<typename T>
class my_queue
{
public:
    void push( const T& value )
    {
        std::lock_guard<std::mutex> lock(m_mutex);
        m_queque.push(value);
    }

    void pop()
    {
        std::lock_guard<std::mutex> lock(m_mutex);
        m_queque.pop();
    }
    T front() const
    {
        return m_queque.front();
    }
    bool empty()
    {
        return  m_queque.empty();
    }

private:
    std::queue<T> m_queque;
    mutable std::mutex m_mutex;
};

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
        Archivo input(DIRECTORY +filename);
        int nbytes;
        while((nbytes = input.lee(BUFSIZ)) > 0);
        istringstream iss(input.get_contenido());
        vector<string> tokens{istream_iterator<string>{iss}, istream_iterator<string>{}};
        map<string, int> mapOfWords;
        cout << input.nombreArchivo << endl; 
        cout << tokens.size() << endl;      
        for(auto const& token: tokens) {
            string s = token;
            // // Remove non alpha characters
            // for(int i = 0; i <= s.size(); ++i)
            // {
            //     if (s[i] > ' ' && s[i] <= '@')
            //     s[i] = ' ';
            // }
            // string::iterator end_pos = remove(s.begin(), s.end(), ' ');
            // s.erase(end_pos, s.end());
            // Not Found
            if(mapOfWords.find(s) != mapOfWords.end()){  
                mapOfWords[s] += 1;
            }
            else 
                mapOfWords[s] = 1;
        }
        // map<string, int>::iterator it = mapOfWords.find("de");
        // cout << it->first << ":" << it->second << endl;
        m.lock();
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