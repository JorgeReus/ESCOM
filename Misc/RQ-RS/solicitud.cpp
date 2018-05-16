#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include "solicitud.h"
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <string.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include "PaqueteDatagrama.h"
#include "mensaje.h"

SocketDatagrama local(0);
char buffer_entrada[TAM_MAX_DATA];

unsigned int numRQ = 0; 

char *doOperation (char *IP, int puerto, int operationId, char *arguments) {
	struct mensaje msg;
	msg.messageType = RQ; // Request
	msg.requestId = numRQ;
	numOp++;
	strcpy(msg.IP, ip);
	msg.puerto = puerto;
	msg.operationId = operationId;
	strcpy(msg.arguments, arguments);
	PaqueteDatagrama paquete((char *)&msg, sizeof(msg), IP, puerto);
	socket.envia(paquete);
	socket.recibe(paquete);
	return paquete.obtieneDatos();
}

