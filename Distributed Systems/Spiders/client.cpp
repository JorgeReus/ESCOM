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

void intsToArray(int x0, int y0, int *dist) {
   dist[0] = x0;
   dist[1] = y0;
}

void arrayToInts(int *x0, int *y0, int *xf, int *yf, int *dist) {
   *x0 = dist[0];
   *y0 = dist[1];
   *xf = dist[2];
   *yf = dist[3];
}

void arrayToPortIp(char *ip, int *port, int *dist) {
   char *ipPort = (char*)(dist + 4);
   strcpy(ip, ipPort);
   *port = atoi(ipPort + strlen(ipPort) + 1);
}

int main(int argc, char *argv[])
{ 
   if(argc > 4 || argc < 2) 
   {
      printf("Usage: ./client speed serverIP serverPort\n");
      exit(0);
   } else if (argc == 4){
      serverIP = argv[2];
      serverPort = atoi(argv[3]);
      speed = atof(argv[1]); 
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
   PaqueteDatagrama p(dist, 10 * sizeof(int), (char*)serverIP.c_str(), serverPort);
   // Send the first message to server
   s->envia(p);
   // printf("Client send a message from: %s, on port: %d\n", p.obtieneDireccion(), 
   //    htons(p.obtienePuerto()));
   s->recibe(p);
   // printf("Client has recieved a message from: %s, on port: %d\n", p.obtieneDireccion(), 
   //    htons(p.obtienePuerto()));
   arrayToInts(&x0, &y0, &xf, &yf, p.obtieneDatos());
   char chasedIp[16];
   int chasedPort;
   arrayToPortIp(chasedIp, &chasedPort, p.obtieneDatos());
   // printf("I am chasing : %s on %d\n", chasedIp, chasedPort);
   int distance;
   // Cicle of send and recive
   while(x0 != xf || y0 != yf) {
      distance += speed;
      //printf("(%d, %d) to (%d, %d) of %s on %d\n", x0, y0, xf, yf, chasedIp, chasedPort);
      if (strcmp(serverIP.c_str(), p.obtieneDireccion()) != 0 || serverPort != p.obtienePuerto()) {
         printf("Distance : %d\n", distance);
         exit(0);
      }
      // Increase the position
      if (x0 < xf) {
         x0 += speed;
         if (x0 > xf) 
            x0 = xf;
      } else {
        x0 -= speed;
        if (x0 < xf) 
         x0 = xf;
   }
   if (y0 < yf) {
      y0 += speed;
      if (y0 > yf) 
         y0 = yf;
   } else {
      y0 -= speed;
        if (y0 < yf) 
         y0 = yf;
   }
      // Conver corrds to array
   intsToArray(x0, y0, dist);
   p.inicializaDatos(dist);
      // Send the coords and recive the new Ones
   s->envia(p);
   if (x0==xf && y0==yf) {
      p.inicializaIp(chasedIp);
      p.inicializaPuerto(chasedPort);
      s->envia(p);
      printf("Distance : %d\n", distance);
      exit(0);
   }
   s->recibe(p);
   arrayToInts(&x0, &y0, &xf, &yf, p.obtieneDatos());
}
delete s;
return 0;
}
