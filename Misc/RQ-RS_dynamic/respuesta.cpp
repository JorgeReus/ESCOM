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
	PaqueteDatagrama p(sizeof(mensajeCS));

	int bytes = socketlocal->recibe(p);

	mensajeCS = (struct mensaje*)p.obtieneDatos();

	strcpy(mensajeCS->IP, p.obtieneDireccion());
	printf("Direccion cliente: %s\n", p.obtieneDireccion());
	printf("Tamaño paquete: %s\n", strlen);
	return mensajeCS;
}


void Respuesta::sendReply(char *respuesta, char *ipCliente, int puertoCliente)
{
	struct mensaje msg;
	
	msg.messageType = 1;
	msg.requestId = 1;
	strcpy(msg.IP, ipCliente);
	msg.puerto = puertoCliente;	
	msg.operationId = 0;
	strcpy(msg.arguments, respuesta);
	PaqueteDatagrama paquete((char*)&msg, sizeof(msg), ipCliente, puertoCliente);

	socketlocal->envia(paquete);
	bzero(respuesta, sizeof(respuesta));
}

void Respuesta::cleanReply()
{
	bzero(mensajeCS, sizeof(mensajeCS));
}