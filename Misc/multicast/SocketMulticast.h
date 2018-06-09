#ifndef SOCKETMULTICAST_H_
#define SOCKETMULTICAST_H_

#define EMITTER_MODE 1
#define RECIEVER_MODE 0

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <string.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include "PaqueteDatagrama.h"
#include <sys/time.h>

class SocketMulticast {
public:
	SocketMulticast(unsigned int , unsigned int, unsigned int);
	~SocketMulticast();
	int envia(PaqueteDatagrama & p, char *ipE);
	int recibe(PaqueteDatagrama & p, unsigned char TTL);
private:
	struct sockaddr_in direccionLocal;
	struct sockaddr_in direccionForanea;
    int s;
};

#endif