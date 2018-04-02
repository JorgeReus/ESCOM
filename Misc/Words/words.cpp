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

my_queue<string> files;

// Declaring the type of Predicate that accepts 2 pairs and return a bool
typedef std::function<bool(std::pair<std::string, int>, std::pair<std::string, int>)> Comparator;
// Defining a lambda function to compare two pairs. It will compare two pairs using second field
Comparator compFunctor = [](std::pair<std::string, int> elem1 ,std::pair<std::string, int> elem2)
{
    return elem1.second > elem2.second;
};

void calculateWords()
{
    while(!files.empty()) 
    {
        string filename = files.front();
        files.pop();
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
        cout << filename << " :" << endl;
        for (std::pair<std::string, int> element : setOfWords){
            if (i <= 500){
                std::cout << "   " <<element.first << " :: " << element.second << std::endl;
                i++;
            }
        }
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
    DIR *directory = opendir("./files");
    if(directory == NULL)
    {
        cout << "Folder '/files' not found" << endl;
        exit(1);
    }
    struct dirent *dir;
    while ((dir = readdir(directory)) != NULL)
        if (strcmp(dir->d_name, ".") && strcmp(dir->d_name, ".."))
            files.push(dir->d_name);
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
    exit(0);
}