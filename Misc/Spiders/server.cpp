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
#include <tuple>
#include <vector>


#define NUM_CLIENTS 1

using namespace std;


void printClients(vector<tuple<string, int, Coord, Coord> > &clients) {
   for (int i=0; i < NUM_CLIENTS; i++) {    
      printf("Begin: %d, %d - End: %d, %d - Assigned to: %s at %d\n", get<2>(clients[i]).x, get<2>(clients[i]).y, 
         get<3>(clients[i]).x, get<3>(clients[i]).y, get<0>(clients[i]).c_str(), get<1>(clients[i]));
   }
}


int main(int argc, char *argv[])
{ 
   int port = 6666;
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
   Coord start = coordinates.front();
   // For Storing the client's addresses and ports
   vector<tuple<string, int, Coord, Coord> > clients;
   PaqueteDatagrama p(4 * sizeof(int));
   int dist[4];
   // Wait for all client's message
   for (int i=0; i < NUM_CLIENTS; i++) {
      // Recieve the wake up
      s->recibe(p);
      printf("Server has recieved a message from: %s, on port: %d\n", p.obtieneDireccion(), 
         htons(p.obtienePuerto()));
      // Save the client's address, port and coordinates
      Coord start = coordinates.front();
      coordinates.pop();
      clients.push_back(make_tuple(p.obtieneDireccion(), htons(p.obtienePuerto()), start, coordinates.front()));
   }
   // Begin the distribution
   for (int i=0; i < NUM_CLIENTS; i++) {
      // Unpack the client's address, port and coordinates
      dist[0] = get<2>(clients[i]).x;
      dist[1] = get<2>(clients[i]).y;
      dist[2] = get<3>(clients[i]).x;
      dist[3] = get<3>(clients[i]).y;   
      p.inicializaDatos(dist);
      p.inicializaIp((char*)get<0>(clients[i]).c_str());
      p.inicializaPuerto(get<1>(clients[i]));
      printf("Begin: %d, %d - End: %d, %d - Assigned to: %s at %d\n", p.obtieneDatos()[0], p.obtieneDatos()[1], 
         p.obtieneDatos()[2], p.obtieneDatos()[3], p.obtieneDireccion(), p.obtienePuerto());
      s->envia(p);
   }
   //printClients(clients);
   delete s;
   return 0;
}
