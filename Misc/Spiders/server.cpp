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

int puerto = 7200;
using namespace std;


int main(int argc, char *argv[])
{ 
   if(argc > 2) 
   {
      printf("Usage: ./server port\n");
      exit(0);
   } else if (argc == 2) {
      puerto = atoi(argv[1]);
   } 
   SocketDatagrama* s = new SocketDatagrama(puerto);
   PaqueteDatagrama p(3 * sizeof(unsigned int));
   s->recibe(p); 
   printf("Server has recieved a message from: %s, on port: %d\n", p.obtieneDireccion(), htons(p.obtienePuerto()));
   int *res = (int*)p.obtieneDatos();
   // Follower Position
   int xsPos = res[0];
   int ysPos = res[1];
   // Followed Position
   int xdPos = 5;
   int yPos = 1;
   printf("X: %d, y: %d\n", begin, end);

   if (num % i==0){
      printf("Num: %u is divisible by: %u\n", num, i);
      break;
   }
   PaqueteDatagrama pSend((char*)message, 3 * sizeof(char), p.obtieneDireccion(), p.obtienePuerto());
   s->envia(pSend);
   delete s;
   return 0;
}
