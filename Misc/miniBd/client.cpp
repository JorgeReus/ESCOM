#include <stdio.h>
#include <stdlib.h>
#include "mensaje.h"
#include "solicitud.h"

#define SERVER_PORT 7200


int main (int argc, char *argv[]) {

	Solicitud cliente;

	char *ip;
	char *args = "Hola";
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
		ms = cliente.doOperation(ip, SERVER_PORT, 1, args);
		if(strcmp(ms, "NO") == 0) {
			printf("Mátate ALV\n");
			return 1;
		}
		msg = (struct mensaje*)ms;
		printf("\n\nNueva op\nRead Operation: %d\n", atoi(msg->arguments));
		ms = cliente.doOperation(ip, SERVER_PORT, 2, args);
		if(strcmp(ms, "NO") == 0) {
			printf("Mátate ALV\n");
			return 1;
		}
		msg = (struct mensaje*)ms;
		printf("Write Operation: %d\n", atoi(msg->arguments));
	}
	return 0;
}