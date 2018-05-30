#include <arpa/inet.h>
#include <cstdlib>
#include <cstring>
#include <cstdio>
#include <cmath>
#include <iostream>
#include <netinet/in.h>
#include <netdb.h>
#include <utility>
#include <string>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include "SocketDatagrama.h"
#include "PaqueteDatagrama.h"
#include "mensaje.h"
#include "respuesta.h"

Respuesta::Respuesta (int p1){
	socketlocal = new SocketDatagrama(p1);
}

struct mensaje* Respuesta::getRequest(void)
{
	PaqueteDatagrama p(sizeof(struct mensaje));
	unsigned int bytes = socketlocal->recibe(p);
	mensajeCS = (struct mensaje*)p.obtieneDatos();
	strcpy(mensajeCS->IP, p.obtieneDireccion());
	mensajeCS->puerto = p.obtienePuerto();
	return mensajeCS;
}


void Respuesta::sendReply(char *respuesta, char *ipCliente, int puertoCliente)
{
	struct mensaje msg;
	msg.messageType = 0;
	msg.requestId = 1;
	strcpy(msg.IP, ipCliente);
	msg.puerto = puertoCliente;	
	msg.operationId = 0;
	strcpy(msg.arguments, respuesta);
	PaqueteDatagrama paquete((char*)&msg, sizeof(struct mensaje) - TAM_MAX_DATA + strlen(msg.arguments), 
		ipCliente, puertoCliente);
	socketlocal->envia(paquete);
	bzero(respuesta, sizeof(respuesta));
}

void Respuesta::cleanReply()
{
	bzero(mensajeCS, sizeof(mensajeCS));
}