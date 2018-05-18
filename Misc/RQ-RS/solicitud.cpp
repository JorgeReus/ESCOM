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
	numRQ++;
	strcpy(msg.IP, IP);
	msg.puerto = socketlocal->getPuerto();
	msg.operationId = operationId;
	strcpy(msg.arguments, arguments);
	PaqueteDatagrama paquete((char *)&msg, sizeof(msg), IP, puerto);
	socketlocal->envia(paquete);
	socketlocal->recibe(paquete);
	return paquete.obtieneDatos();
}

