#include <stdio.h>
#include <stdlib.h>
#include "mensaje.h"
#include "solicitud.h"

#define SERVER_PORT 7200


int main (int argc, char *argv[]) {

	Solicitud cliente;

	char *ip;
	int operation;
	char *args;
	if (argc != 4) {
		printf("Usage: ./client args operation server_ip\n");
	} else {
		args = argv[1];
		operation = atoi(argv[2]);
		ip = argv[3];
	}
	char *ms = cliente.doOperation(ip, SERVER_PORT, operation, args);
	struct mensaje *msg = (mensaje*)ms;
}