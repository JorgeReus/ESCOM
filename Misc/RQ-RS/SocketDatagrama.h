#ifndef SOCKETDATAGRAMA_H_
#define SOCKETDATAGRAMA_H_

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


class SocketDatagrama{
public:
	SocketDatagrama(int);
	~SocketDatagrama();
	int recibe(PaqueteDatagrama & p);
	int envia(PaqueteDatagrama & p);
	char *getIP();
	int getPuerto();
private:
	struct sockaddr_in direccionLocal;
	struct sockaddr_in direccionForanea;
    int s;
};

#endif