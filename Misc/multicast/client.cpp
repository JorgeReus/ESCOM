#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <string>
#include <cmath>
#include "PaqueteDatagrama.h"
#include "SocketMulticast.h"
#include <unistd.h>

#define SERVER_PORT 6666


using namespace std;


int main (int argc, char *argv[]) {

	PaqueteDatagrama p("hola\0", 5, "224.0.0.225");
	string ip;
	int port;
	int ttl;
	if (argc != 4) {
		printf("Usage ./client ip port ttl\n");
	} else {
		ip = argv[1];
		port argv[2];
		ttl = argv[3];
	}
	SocketMulticast *s = new SocketMulticast(port);
	s->envia();
	return 0;
}