#include <arpa/inet.h>
#include <cstdlib>
#include <cstring>
#include <cstdio>
#include <cmath>
#include "Coord.h"
#include "gfx.h"
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


int NUM_CLIENTS = 2;

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

void printClient(PaqueteDatagrama p) {
   printf("(%d, %d) to (%d, %d) : %s at %d, chases %s at %d\n", p.obtieneDatos()[0], p.obtieneDatos()[1], 
      p.obtieneDatos()[2], p.obtieneDatos()[3], p.obtieneDireccion(), p.obtienePuerto(), 
      (char*)(p.obtieneDatos() + 4), atoi((char*)(p.obtieneDatos() + 4) + strlen((char*)(p.obtieneDatos() + 4)) + 1));
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
   PaqueteDatagrama p(10 * sizeof(int));
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
   gfx_open(800, 600, "Spiders");
   gfx_color(0, 200, 100);
   char auxIPport[30];
   for (int i=0; i < NUM_CLIENTS; i++) {
      // Unpack the client's address, port and coordinates
      dist[0] = get<2>(clients[i]).x;
      dist[1] = get<2>(clients[i]).y;
      dist[2] = get<3>(clients[i]).x;
      dist[3] = get<3>(clients[i]).y;   
      memcpy(auxIPport, (char*)dist, 4 * sizeof(int));

      // Your chased client
      if (i + 1 == NUM_CLIENTS) {
         memcpy(auxIPport + 16, (char*)get<0>(clients[0]).c_str(), get<0>(clients[0]).size());
         auxIPport[get<0>(clients[0]).size() + 16] = '\0';
         string po = to_string(get<1>(clients[0]));
         memcpy(auxIPport + get<0>(clients[0]).size() + 17, (char*)po.c_str(), po.size());
      } else {
         memcpy(auxIPport + 16, (char*)get<0>(clients[i + 1]).c_str(), get<0>(clients[i + 1]).size() + 1);
         auxIPport[get<0>(clients[0]).size() + 16] = '\0';
         string po = to_string(get<1>(clients[i + 1]));
         memcpy(auxIPport + get<0>(clients[0]).size() + 17, (char*)po.c_str(), po.size());
      }
      p.inicializaIp((char*)get<0>(clients[i]).c_str());
      p.inicializaPuerto(get<1>(clients[i]));
      p.inicializaDatos((int*)auxIPport);
      printClient(p);
      gfx_point(p.obtieneDatos()[0], p.obtieneDatos()[1]);
      s->envia(p);
   }
   vector<pair<string, int> > finished;
   while(!checkFinished(clients)) {
      for (int i=0; i < NUM_CLIENTS; i++) {
         // Recieve raw data
         s->recibe(p);
         // Modify you coords         
         for (int j=0; j < NUM_CLIENTS; j++) { 
            if (strcmp(get<0>(clients[j]).c_str(), p.obtieneDireccion()) == 0 && get<1>(clients[j]) == p.obtienePuerto()) { 
               for (int k = 0; k < NUM_CLIENTS; k++) {
                  if (get<2>(clients[j]).x == get<3>(clients[k]).x && get<2>(clients[j]).y == get<3>(clients[k]).y) {
                     get<3>(clients[k]).x = p.obtieneDatos()[0];
                     get<3>(clients[k]).y = p.obtieneDatos()[1];
                  }
               }
               get<2>(clients[j]).x = p.obtieneDatos()[0];
               get<2>(clients[j]).y = p.obtieneDatos()[1];
               dist[0] = p.obtieneDatos()[0];
               dist[1] = p.obtieneDatos()[1];   
               dist[2] = get<3>(clients[j]).x;
               dist[3] = get<3>(clients[j]).y;                 
               gfx_point(get<2>(clients[i]).x, get<2>(clients[i]).y); 
               break;
            }
         }
         if(gfx_event_waiting() && gfx_wait() == 'q'){
            break;
         }
         // printf("%d, %d - %d, %d - Assigned to: %s at %d\n", dist[0], dist[1], 
         //    dist[2], dist[3], p.obtieneDireccion(), p.obtienePuerto());
         printClients(clients);
         p.inicializaDatos(dist);
         s->envia(p);
         usleep(41666); //24 por segundo
      }
   }
   delete s;
   return 0;
}
