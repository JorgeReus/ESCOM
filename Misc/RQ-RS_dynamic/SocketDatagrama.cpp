#include "PaqueteDatagrama.h"
#include "SocketDatagrama.h"
#include <iostream>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <stdio.h>
#include <arpa/inet.h>
#include <stdlib.h>

SocketDatagrama::SocketDatagrama(int port) {
   s = socket(AF_INET, SOCK_DGRAM, 0);
   bzero((char *)&direccionLocal, sizeof(direccionLocal));
   direccionLocal.sin_family = AF_INET;
   direccionLocal.sin_addr.s_addr = INADDR_ANY;
   direccionLocal.sin_port = htons(port);
   bind(s, (struct sockaddr *)&direccionLocal,sizeof(direccionLocal));
}

SocketDatagrama::~SocketDatagrama() {
   close(s);
}

int SocketDatagrama::getPuerto() {
   struct sockaddr_in localAddress;
   socklen_t addressLength = sizeof(localAddress);
   getsockname(s, (struct sockaddr*)&localAddress, &addressLength);
   return (int) ntohs(localAddress.sin_port);
}

int SocketDatagrama::recibe(PaqueteDatagrama &p) {
   bzero((char *)&direccionForanea, sizeof(direccionForanea));
   char *data = (char *)malloc(p.obtieneLongitud());
   socklen_t clilen = sizeof(direccionForanea);
   int tamPaq = recvfrom(s, (char *)data, p.obtieneLongitud(), 0, (struct sockaddr*)&direccionForanea, &clilen);
   p.inicializaDatos(data);
   p.inicializaIp(inet_ntoa(direccionForanea.sin_addr));
   p.inicializaPuerto(htons(direccionForanea.sin_port));

   return tamPaq;
}

int SocketDatagrama::envia(PaqueteDatagrama &p) {
   bzero((char *)&direccionForanea, sizeof(direccionForanea));
   direccionForanea.sin_family = AF_INET;
   direccionForanea.sin_addr.s_addr = inet_addr(p.obtieneDireccion());
   direccionForanea.sin_port = htons(p.obtienePuerto());
   int len = sizeof(direccionForanea);
   sendto(s, p.obtieneDatos(), p.obtieneLongitud(), 0, (struct sockaddr *) &direccionForanea, sizeof(direccionForanea));

}