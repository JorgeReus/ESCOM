#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include "mensaje.h"
#include "respuesta.h"
#include <string>
#include <stack>

#define PUERTO_LOCAL 7200
using namespace std;

int escritura(int nbd) {
	return nbd+1;
}

int main (int argc, char *argv[]) {

	Respuesta respuesta(PUERTO_LOCAL);
	while(1)
	{
		struct mensaje *msg = respuesta.getRequest();
		printf("Tipo: %d\n", msg->messageType);
		printf("Request id: %d\n", msg->requestId);
		printf("IP: %s\n", msg->IP);
		printf("Port: %d\n", msg->puerto);
		printf("Operation 1d: %d\n", msg->operationId);
		printf("Arguments: %s\n", msg->arguments);

		int nbd = 0;

		if(msg->operationId == 1) {
			nbd = nbd;
		} else if(msg->operationId == 2) {
			nbd = escritura(nbd);
		} else {
			printf("ASCO DE CLIENTE");
		}

		char dest[4];
		memcpy(dest,&nbd,4);
		printf("Arguments to send: %d\n", *dest);
		respuesta.sendReply((char*)dest, msg->IP, msg->puerto);
		respuesta.cleanReply();
	}
	
}