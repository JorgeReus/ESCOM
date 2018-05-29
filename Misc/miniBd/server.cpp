#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <sstream>
#include <cstring>
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

	int nbd = 0;
	Respuesta respuesta(PUERTO_LOCAL);
	struct mensaje *msg;
	while(1)
	{
		msg = respuesta.getRequest();
		printf("Tipo: %d\n", msg->messageType);
		printf("Request id: %d\n", msg->requestId);
		printf("IP: %s\n", msg->IP);
		printf("Port: %d\n", msg->puerto);
		printf("Operation id: %d\n", msg->operationId);
		printf("Arguments: %s\n", msg->arguments);

		if(msg->operationId == 1) {
			nbd = nbd;
		} else if(msg->operationId == 2) {
			nbd = escritura(nbd);
		} else {
			printf("ASCO DE CLIENTE");
		}

		/*stringstream strs;
		strs << nbd;
		string temp_str = strs.str();
		char const *dest = temp_str.c_str();*/

		char dest[10];
		sprintf(dest, "%d", nbd);


		printf("Nbd: %d\n", nbd);
		printf("dest: %s\n", dest);
		printf(">>>>>>>>>>Fin de operacion<<<<<<<<<<\n\n\n");

		respuesta.sendReply((char*)dest, msg->IP, msg->puerto);
		respuesta.cleanReply();
	}
	
}