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
#include <queue>
#include <string>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <utility>
#include <vector>


#define NUM_CLIENTS 4

using namespace std;



int main(int argc, char *argv[])
{ 
   int port = 7200;
   if(argc > 2) 
   {
      printf("Usage: ./server port\n");
      exit(0);
   } else if (argc == 2) {
      port = atoi(argv[1]);
   } 
   SocketDatagrama* s = new SocketDatagrama(port);
   // Create the datagram for the message of the clients
   queue<Coord> coordinates;
   coordinates.push(Coord(0,0));
   coordinates.push(Coord(100,0));
   coordinates.push(Coord(100,100));
   coordinates.push(Coord(0,100));

   vector<pair<PaqueteDatagrama, Coord> > clients;

   for (int i=0; i < NUM_CLIENTS; i++) {
      PaqueteDatagrama p = new PaqueteDatagrama(2 * sizeof(int));
      s->recibe(p); 
      printf("Server has recieved a message from: %s, on port: %d\n",
        p.obtieneDireccion(), htons(p.obtienePuerto()));
      pair<PaqueteDatagrama, Coord> pa;
      pa.first = p;
      pa.second = coordinates.front();
      //clients.push_back(make_pair<p, coordinates.front()>);
      coordinates.pop();
   }
   /*unsigned int *res = (unsigned int*)p.obtieneDatos();
   unsigned int begin = res[0];
   unsigned int end = res[1];
   unsigned int num = res[2];
   printf("    Begin: %u, End: %u, Num: %u\n", begin, end, num);
   char message[] = {'y','\0'};
   for(unsigned int i=begin; i < end; i++) {
      if (num % i==0){
         printf("Num: %u is divisible by: %u\n", num, i);
         strcpy(message, "n\0");
         break;
      }
   }
   PaqueteDatagrama pSend((char*)message, 3 * sizeof(char), p.obtieneDireccion(), p.obtienePuerto());
   s->envia(pSend);*/
   delete s;
   return 0;
}
