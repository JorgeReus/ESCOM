#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "SocketDatagrama.h"
#include "PaqueteDatagrama.h"
#include "mensaje.h"
#include "solicitud.h"

SocketDatagrama local(0);
char buffer_entrada[TAM_MAX_DATA];

unsigned int numRQ = 0; 

Solicitud::Solicitud()
{
	socketlocal = new SocketDatagrama(0);
}

char* Solicitud::doOperation (char *IP, int puerto, int operationId, char *arguments) {
	struct mensaje msg;
	msg.messageType = RQ; // Request
	msg.requestId = numRQ;
	printf("Num RQ: %d\n", numRQ);
	strcpy(msg.IP, IP);
	numRQ++;
	msg.puerto = socketlocal->getPuerto();
	msg.operationId = operationId;
	strcpy(msg.arguments, arguments);
	PaqueteDatagrama paquete((char *)&msg,TAM_MAX_DATA,
		IP, puerto);
	socketlocal->recibeTimeout(paquete, 1, 20000);
	socketlocal->envia(paquete);
	int tries = 7;
	int flag = 0;
	while(tries > 0) {
		int n = socketlocal->recibe(paquete);
		if(n > 0) {
			struct mensaje *ap = (struct mensaje*)paquete.obtieneDatos();
			printf("Request id mia: %d\tEl recibido: %d\n", msg.requestId, ap->requestId);
			if(msg.requestId != ap->requestId) {
				flag = 1;
			} else {
				flag = 0;
			}
			break;
		} else {
			socketlocal->envia(paquete);
			tries--;
		}
		if(flag == 1) {
			tries = 7;
		}
	}
	if (tries == 0){
		printf("Server not available\n");
		return "NO";
	}
	return paquete.obtieneDatos();
}

