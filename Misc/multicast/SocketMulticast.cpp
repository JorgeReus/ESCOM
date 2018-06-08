#include "PaqueteDatagrama.h"
#include "SocketMulticast.h"
#include <iostream>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <stdio.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <errno.h>

SocketMulticast::SocketMulticast(unsigned int port) {
   s = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP);
   if (s < 0) {   
      perror("Cannot create multicast socket");
      exit(0); 
   }
   bzero((char *)&direccionLocal, sizeof(direccionLocal));
   direccionLocal.sin_family = AF_INET;
   direccionLocal.sin_addr.s_addr = INADDR_ANY;
   direccionLocal.sin_port = htons(port);
   bind(s, (struct sockaddr *)&direccionLocal,sizeof(direccionLocal));
}

SocketMulticast::~SocketMulticast() {
   close(s);
}

int SocketMulticast::recibe(PaqueteDatagrama & p, char *ipE) {
   struct ip_mreq multicast;
   multicast.imr_multiaddr.s_addr = inet_addr(p.obtieneDireccion());
   multicast.imr_interface.s_addr = htonl(INADDR_ANY);
   setsockopt(s, IPPROTO_IP, IP_ADD_MEMBERSHIP, (void *) &multicast, sizeof(multicast))
   bzero((char *)&direccionForanea, sizeof(direccionForanea));
   char *data = (char *)calloc(p.obtieneLongitud(), sizeof(char));
   socklen_t clilen = sizeof(direccionForanea);
   recvfrom(s, (char *)data, p.obtieneLongitud(), 0, (struct sockaddr*)&direccionForanea, &clilen);
   p.inicializaDatos(data);
   p.inicializaIp(inet_ntoa(direccionForanea.sin_addr));
   p.inicializaPuerto(htons(direccionForanea.sin_port));

   return n;
}


int SocketMulticast::envia(PaqueteDatagrama & p, unsigned char TTL) {
   setsockopt(s, IPPROTO_IP, IP_MULTICAST_TTL, (void *) &TTL, sizeof(TTL))
   bzero((char *)&direccionForanea, sizeof(direccionForanea));
   direccionForanea.sin_family = AF_INET;
   direccionForanea.sin_addr.s_addr = inet_addr(p.obtieneDireccion());
   direccionForanea.sin_port = htons(p.obtienePuerto());
   int len = sizeof(direccionForanea);
   sendto(s, p.obtieneDatos(), p.obtieneLongitud(), 0, (struct sockaddr *) &direccionForanea, sizeof(direccionForanea));

}
