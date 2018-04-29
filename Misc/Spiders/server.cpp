#include <arpa/inet.h>
#include <cstdlib>
#include <cstring>
#include <cstdio>
#include <cmath>
#include "Coord.h"
#include "gfx.h"
#include "Spider.h"
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


int NUM_CLIENTS = 4;

using namespace std;


void printClients(vector<tuple<string, int, Coord, Coord, string, int> > &clients) {
   for (int i=0; i < NUM_CLIENTS; i++) {  
      Spider s2(get<2>(clients[i]), 10); 
      // printf("(%d, %d) to: (%d, %d) : %s at %d\n",get<2>(clients[i]).x, get<2>(clients[i]).y, 
      //    get<3>(clients[i]).x, get<3>(clients[i]).y, get<0>(clients[i]).c_str(), get<1>(clients[i]));
   }
}


bool checkFinished(vector<tuple<string, int, Coord, Coord, string, int> > &clients) {
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
   int length = 500;
   if(argc > 3) 
   {
      printf("Usage: ./server port length\n");
      exit(0);
   } else if (argc == 3) {
      port = atoi(argv[1]);
   } 
   SocketDatagrama* s = new SocketDatagrama(port);
   // Create the datagram for the message of the clients
   vector<Coord> coordinates;
   coordinates.push_back(Coord(10, 10));
   coordinates.push_back(Coord(10, 10 + length));
   coordinates.push_back(Coord(10 + length, 10 + length));
   coordinates.push_back(Coord(10 + length, 10));
   // For Storing the client's addresses and ports
   vector<tuple<string, int, Coord, Coord, string, int> > clients;
   PaqueteDatagrama p(10 * sizeof(int));
   int dist[4];
   // Wait for all client's message
   for (int i=0; i < NUM_CLIENTS; i++) {
      // Recieve the wake up
      s->recibe(p);
      // Save the client's address, port and coordinates
      Coord start(0,0);
      Coord end(0,0);
      if (i + 1 == NUM_CLIENTS) {
         start = coordinates[i];
         end = coordinates[0];
      } else {
         start = coordinates[i];
         end = coordinates[i + 1];
      }
      clients.push_back(make_tuple(p.obtieneDireccion(), p.obtienePuerto(), start, end, "0,0,0,0", 0));
   }
   // Begin the distribution
   gfx_open(length, length, "Spiders");
   char auxIPport[30];
   for (int i=0; i < NUM_CLIENTS; i++) {
      // Your chased client
      if (i + 1 == NUM_CLIENTS) {
         // Unpack the client's address, port and coordinates
         dist[0] = get<2>(clients[i]).x;
         dist[1] = get<2>(clients[i]).y;
         dist[2] = get<2>(clients[0]).x;
         dist[3] = get<2>(clients[0]).y;   
         memcpy(auxIPport, (char*)dist, 4 * sizeof(int));
         memcpy(auxIPport + 16, (char*)get<0>(clients[0]).c_str(), get<0>(clients[0]).size());
         auxIPport[get<0>(clients[0]).size() + 16] = '\0';
         string po = to_string(get<1>(clients[0]));
         // Set the chased
         get<4>(clients[i]) = get<0>(clients[0]);
         get<5>(clients[i]) = get<1>(clients[0]);
         memcpy(auxIPport + get<0>(clients[0]).size() + 17, (char*)po.c_str(), po.size());
      } else {
         // Unpack the client's address, port and coordinates
         dist[0] = get<2>(clients[i]).x;
         dist[1] = get<2>(clients[i]).y;
         dist[2] = get<2>(clients[i + 1]).x;
         dist[3] = get<2>(clients[i + 1]).y;   
         memcpy(auxIPport, (char*)dist, 4 * sizeof(int));
         memcpy(auxIPport + 16, (char*)get<0>(clients[i + 1]).c_str(), get<0>(clients[i + 1]).size() + 1);
         auxIPport[get<0>(clients[0]).size() + 16] = '\0';
         string po = to_string(get<1>(clients[i + 1]));
         // Set the chased
         get<4>(clients[i]) = get<0>(clients[i+1]);
         get<5>(clients[i]) = get<1>(clients[i+1]);
         memcpy(auxIPport + get<0>(clients[0]).size() + 17, (char*)po.c_str(), po.size());
      }
      p.inicializaIp((char*)get<0>(clients[i]).c_str());
      p.inicializaPuerto(get<1>(clients[i]));
      p.inicializaDatos((int*)auxIPport);
      s->envia(p);
   }
   vector<pair<string, int> > finished;
   while(!checkFinished(clients)) {
      s->recibe(p);
         // Modify you coords         
      for (int j=0; j < NUM_CLIENTS; j++) {
            // Found the correct client 
         if (strcmp(get<0>(clients[j]).c_str(),p.obtieneDireccion()) == 0 && get<1>(clients[j])==p.obtienePuerto()) { 
               // Modify the clients coords
            //cout << p.obtienePuerto() << ":";
            for (int k = 0; k < NUM_CLIENTS; k++) {
               string ip = p.obtieneDireccion();
               if (ip == get<4>(clients[k]) && p.obtienePuerto() == get<5>(clients[k])) {
                  get<3>(clients[k]).x = p.obtieneDatos()[0];
                  get<3>(clients[k]).y = p.obtieneDatos()[1];
                  //cout << get<1>(clients[k]) << endl;
                  break;
               }
            }
            // Modify your coords
            dist[0] = p.obtieneDatos()[0];
            dist[1] = p.obtieneDatos()[1]; 
            get<2>(clients[j]).x = p.obtieneDatos()[0];
            get<2>(clients[j]).y = p.obtieneDatos()[1];   
            if (j + 1 == NUM_CLIENTS) { 
               dist[2] = get<2>(clients[0]).x;
               dist[3] = get<2>(clients[0]).y; 
            } else {
               dist[2] = get<3>(clients[j]).x;
               dist[3] = get<3>(clients[j]).y; 
            }          
            break;
         }
      }
      printClients(clients);
      p.inicializaDatos(dist);
      s->envia(p);
      gfx_clear();
         usleep(41666); //24 por segundo
      }
      getchar();
      delete s;
      return 0;
   }
