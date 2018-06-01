#include <stdio.h>
#include <stdlib.h>
#include "mensaje.h"
#include "solicitud.h"

#define SERVER_PORT 7200

int nbdLocal = 0;

int main (int argc, char *argv[]) {

	Solicitud cliente;

	char *ip;
	char *args = "100";
	int num;
	if (argc != 3) {
		printf("Usage: ./client num server_ip\n");
		return 1;
	} else {
		num = atoi(argv[1]);
		ip = argv[2];
	}
	char *ms;
	struct mensaje *msg;
	int result = 0;
	for(int i=0; i < num; i++) {
		printf("\n\n------Nueva op------\n");
		ms = cliente.doOperation(ip, SERVER_PORT, 1, args);
		if(strcmp(ms, "NO") == 0) {
			printf("Mátate ALV\n");
			return 1;
		}
		msg = (struct mensaje*)ms;
		if(atoi(msg->arguments) == nbdLocal) {
			printf("Read\n>>>>El servidor: %d\tLocal: %d\n", atoi(msg->arguments), nbdLocal);
		} else {
			printf("Error de lectura\n>>>>El servidor: %d\tLocal: %d\n", atoi(msg->arguments), nbdLocal);
			return 1;
		}

		ms = cliente.doOperation(ip, SERVER_PORT, 2, args);
		nbdLocal += atoi(args);
		if(strcmp(ms, "NO") == 0) {
			printf("Mátate ALV\n");
			return 1;
		}
		msg = (struct mensaje*)ms;
		if(atoi(msg->arguments) == nbdLocal) {
			printf("Write\n>>>>El servidor: %d\tLocal: %d\n", atoi(msg->arguments), nbdLocal);
		} else {
			printf("Error de escritura\n>>>>El servidor: %d\tLocal: %d\n", atoi(msg->arguments), nbdLocal);
			return 1;
		}
		printf("------Fin de op------\n");
	}
	return 0;
}