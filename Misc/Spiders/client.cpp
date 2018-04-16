#include <arpa/inet.h>
#include <cstdlib>
#include <cstring>
#include <cstdio>
#include <cmath>
#include "datagramPaquet.h"
#include "datagramSocket.h"
#include <fstream>
#include <iostream>
#include <netinet/in.h>
#include <netdb.h>
#include <utility>
#include <string>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <vector>
#include <tuple>

int puerto = 7200;
using namespace std;

void loadIPs(vector<tuple<string, int, int> > &v){
   string delimiter = "-";
   vector<string> ips;
   string line;
   ifstream ip_file ("ips.txt");
   // Read the file
   int pos = 0;
   string ip, port, percentage;
   string::size_type sz;
   if (ip_file.is_open()) {
      while (getline (ip_file, line)) {
         pos = line.find(delimiter);
         ip = line.substr(0, pos);
         line.erase(0, pos + delimiter.length());
         pos = line.find(delimiter);
         port = line.substr(0, pos);
         line.erase(0, pos + delimiter.length());
         v.push_back(make_tuple(ip, stoi(port, &sz), stoi(line, &sz)));
      }
      ip_file.close();
   } else {
      cout << "Couldn't open file" << endl;
   }
}

int main(int argc, char *argv[])
{ 
   if(argc != 2) 
   {
      printf("Usage: ./client num\n");
      exit(0);
   }
   unsigned int num = atoi(argv[1]);
   vector<tuple<string, int, int> > ip_s;
   loadIPs(ip_s);
   unsigned int start = 2;
   unsigned int step = ceil(num*(float(get<2>(ip_s[0]))/100));
   unsigned long end = start + step;
   unsigned int dist[3];
   dist[2] = num;
   vector<PaqueteDatagrama> servers;
   SocketDatagrama* s = new SocketDatagrama(0);
   for (int i=0; i < ip_s.size(); i++) {
      if (end >= num)
         end = num;
      dist[0] = start;
      dist[1] = end;
      PaqueteDatagrama p((char*)dist, 3 * sizeof(unsigned int), (char *)get<0>(ip_s[i]).c_str(), get<1>(ip_s[i]));
      s->envia(p);
      cout << start << " : " << end << " assigned to ";
      cout << get<0>(ip_s[i]) << "-" << get<1>(ip_s[i]) << endl;
      servers.push_back(p);
      step = ceil(num*(float(get<2>(ip_s[i + 1]))/100));
      start += step + 1;
      end += step + 1;
   }
   char isPrime = 1;
   for (int i = 0; i < servers.size(); i++) {
      s->recibe(servers[i]);
      printf("%s : %d = %c\n", servers[i].obtieneDireccion(), servers[i].obtienePuerto(), servers[i].obtieneDatos()[0]);
      if(servers[i].obtieneDatos()[0] == 'n') {
         isPrime = 0;
         break;
      }
   }
   if(isPrime != 0) {
      cout << num << " is a prime number" << endl;
   } else {
      cout << num << " is not a prime number" << endl;
   }
   delete s;
   return 0;
}
