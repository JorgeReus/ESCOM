#include <stdio.h>
#include <stdlib.h>
#include "solicitud.h"
#include "mensaje.h"


#define SERVER_PORT 7200


int main (int argc, char *argv[]) {
	char *ip;
	int operation;
	char *args;
	if (argc != 4) {
		printf("Usage: ./client args operation server_ip\n");
	} else {
		args = argv[1];
		operation = atoi(argv[2]);
		server_ip = argv[3];
	}
	char *ms = doOperation(ip, SERVER_PORT, operation, args);
	struct mensaje *msg = ();
}