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
double speed = 10.0;

void intsToArray(int x0, int y0, int xf, int yf, int *dist) {
   dist[0] = x0;
   dist[1] = y0;
   dist[2] = xf;
   dist[3] = yf;
}

void arrayToInts(int *x0, int *y0, int *xf, int *yf, int *dist) {
   *x0 = dist[0];
   *y0 = dist[1];
   *xf = dist[2];
   *yf = dist[3];
}

int main(int argc, char *argv[])
{ 
   if(argc > 4 || argc < 2) 
   {
      printf("Usage: ./client speed serverIP serverPort\n");
      exit(0);
   } else if (argc == 4){
      serverIP = argv[2];
      serverPort = htons(atoi(argv[3]));
   } else if (argc == 2) {
      speed = atof(argv[1]); 
   }
   // Initialize coords
   Coord coord(0,0);
   int dist[4];
   int x0;
   int y0;
   int xf;
   int yf;
   // Initialize Socket
   SocketDatagrama* s = new SocketDatagrama(0);
   PaqueteDatagrama p(dist, 4 * sizeof(unsigned int), (char*)serverIP.c_str(), htons(serverPort));
   // Send the first message to server
   s->envia(p);
   printf("Client send a message from: %s, on port: %d\n", p.obtieneDireccion(), 
      htons(p.obtienePuerto()));
   s->recibe(p);
   printf("Client has recieved a message from: %s, on port: %d\n", p.obtieneDireccion(), 
      htons(p.obtienePuerto()));
   arrayToInts(&x0, &y0, &xf, &yf, p.obtieneDatos());
   printf("Start-x:%d,y:%d End-x:%d,y:%d\n", x0, y0, xf, yf);
   // Cicle of send and recive
   while(x0 < xf || y0 < yf) {
      // Increase the position
      if (x0 < xf) {
         x0 += speed;
      } 
      if (y0 < yf) {
         y0 += speed;
      }
      // Conver corrds to array
      intsToArray(x0, y0, xf, yf, dist);
      p.inicializaDatos(dist);
      // Send the coords and recive the new Ones
      s->envia(p);
      s->recibe(p);
      arrayToInts(&x0, &y0, &xf, &yf, p.obtieneDatos());
      printf("Start-x:%d,y:%d End-x:%d,y:%d\n", x0, y0, xf, yf);
   }
   delete s;
   return 0;
}
