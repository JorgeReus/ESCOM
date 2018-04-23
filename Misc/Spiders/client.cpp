#include <arpa/inet.h>
#include <cstdlib>
#include <cstring>
#include <cstdio>
#include <cmath>
#include "Coord.h"
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

using namespace std;
int serverPort = 6666;
string serverIP = "127.0.0.1";

int main(int argc, char *argv[])
{ 
   if(argc > 3) 
   {
      printf("Usage: ./client serverIP serverPort\n");
      exit(0);
   } else if (argc == 3){
      serverIP = argv[1];
      serverPort = atoi(argv[2]);
   }
   Coord coord(0,0);
   int dist[4];
   SocketDatagrama* s = new SocketDatagrama(0);
   PaqueteDatagrama p(dist, 4 * sizeof(unsigned int), (char*)serverIP.c_str(), serverPort);
   s->envia(p);
   s->recibe(p);
   printf("Server has recieved a message from: %s, on port: %d\n", p.obtieneDireccion(), 
         htons(p.obtienePuerto()));
   dist[0] = p.obtieneDatos()[0];
   dist[1] = p.obtieneDatos()[1];
   dist[2] = p.obtieneDatos()[2];
   dist[3] = p.obtieneDatos()[3];
   printf("Start-x:%d,y:%d End-x:%d,y:%d\n", dist[0],dist[1],dist[2],dist[3]);
   /*for (int i=0; i < ip_s.size(); i++) {
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
   }*/
   delete s;
   return 0;
}
