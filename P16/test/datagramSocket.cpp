#include "datagramPaquet.h"
#include "datagramSocket.h"
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <stdio.h>
#include <iostream>
#include <arpa/inet.h>
#include <stdlib.h>

SocketDatagrama::SocketDatagrama(int port) {
	s = socket(AF_INET, SOCK_DGRAM, 0);
	bzero((char *)&direccionLocal, sizeof(direccionLocal));
	direccionLocal.sin_family = AF_INET;
	direccionLocal.sin_addr.s_addr = INADDR_ANY;
	direccionLocal.sin_port = htons(port);
	bind(s, (struct sockaddr *)&direccionLocal,sizeof(direccionLocal));
	printf("Started at: %d\n", htons(direccionLocal.sin_port));
}

SocketDatagrama::~SocketDatagrama() {
	close(s);
}

int SocketDatagrama::recibe(PaqueteDatagrama &p) {
	bzero((char *)&direccionForanea, sizeof(direccionForanea));
	socklen_t clilen = sizeof(direccionForanea);
	int *data = (int*)malloc(p.obtieneLongitud());
	recvfrom(s, (int*)data, p.obtieneLongitud(), 0, (struct sockaddr *)&direccionForanea, &clilen);
	p.inicializaDatos(data);
	p.inicializaIp(inet_ntoa(direccionForanea.sin_addr));
	p.inicializaPuerto(htons(direccionForanea.sin_port));
	//free(data);
}

int SocketDatagrama::envia(PaqueteDatagrama &p) {
	bzero((char *)&direccionForanea, sizeof(direccionForanea));
	direccionForanea.sin_family = AF_INET;
	direccionForanea.sin_addr.s_addr = inet_addr(p.obtieneDireccion());
	direccionForanea.sin_port = htons(p.obtienePuerto());
	sendto(s, p.obtieneDatos(), p.obtieneLongitud(), 0, (struct sockaddr *) &direccionForanea, sizeof(direccionForanea));

}
