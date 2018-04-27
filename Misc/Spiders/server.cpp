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


#define NUM_CLIENTS 2

using namespace std;


void printClients(vector<tuple<string, int, Coord, Coord> > &clients) {
   for (int i=0; i < NUM_CLIENTS; i++) {    
      printf("Begin: %d, %d - End: %d, %d - Assigned to: %s at %d\n", get<2>(clients[i]).x, get<2>(clients[i]).y, 
         get<3>(clients[i]).x, get<3>(clients[i]).y, get<0>(clients[i]).c_str(), get<1>(clients[i]));
   }
}


bool checkFinished(vector<tuple<string, int, Coord, Coord> > &clients) {
   bool finished = true;
   for (int i=0; i < NUM_CLIENTS; i++) {
      if (get<2>(clients[i]).x != get<3>(clients[i]).x || get<3>(clients[i]).y != get<2>(clients[i]).y)  {
         finished = false;
      }
      break;
   }
   return finished;
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
      // printf("Server has recieved a message from: %s, on port: %d\n", p.obtieneDireccion(), p.obtienePuerto());
      // Save the client's address, port and coordinates
      Coord start = coordinates.front();
      coordinates.pop();
      clients.push_back(make_tuple(p.obtieneDireccion(), p.obtienePuerto(), start, coordinates.front()));
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
      // printf("%d, %d - %d, %d - Assigned to: %s at %d\n", p.obtieneDatos()[0], p.obtieneDatos()[1], 
      //    p.obtieneDatos()[2], p.obtieneDatos()[3], p.obtieneDireccion(), p.obtienePuerto());
      s->envia(p);
   }
   while(!checkFinished(clients)) {
      for (int i=0; i < NUM_CLIENTS; i++) {
         // Recieve raw data
         s->recibe(p);
         // if (strcmp(get<0>(clients[i]).c_str(), p.obtieneDireccion()) == 0 && get<1>(clients[i]) == p.obtienePuerto())  {
         //       //Your beginning is what you send me          
         //    get<2>(clients[i]).x = p.obtieneDatos()[0];
         //    get<2>(clients[i]).y = p.obtieneDatos()[1];
         //    dist[0] = p.obtieneDatos()[0];
         //    dist[1] = p.obtieneDatos()[1];
         //       //Your end is the next client beginning
         //    if (i + 1 == NUM_CLIENTS) {
         //       get<3>(clients[i]).x = get<2>(clients[0]).x;
         //       get<3>(clients[i]).y = get<2>(clients[0]).y;
         //       dist[2] = get<2>(clients[0]).x;
         //       dist[3] = get<2>(clients[0]).y; 
         //    } else {
         //       get<3>(clients[i]).x = get<2>(clients[i + 1]).x;
         //       get<3>(clients[i]).y = get<2>(clients[i + 1]).y;
         //       dist[2] = get<3>(clients[i + 1]).x;
         //       dist[3] = get<3>(clients[i + 1]).y; 
         //    }
         // } else {
         //    continue;
         // }
         printf("%d, %d - %d, %d - Assigned to: %s at %d\n", dist[0], dist[1], 
            dist[2], dist[3], p.obtieneDireccion(), p.obtienePuerto());
         p.inicializaDatos(dist);
         s->envia(p);
      }
   }
   //printClients(clients);
   delete s;
   return 0;
}
