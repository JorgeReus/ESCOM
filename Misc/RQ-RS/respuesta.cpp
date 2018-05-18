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
#include "PaqueteDatagrama.h"
#include "SocketDatagrama.h"
#include "mensaje.h"
#include "respuesta.h"

#define PUERTO_LOCAL 7200

Respuesta::Respuesta (int p1){
	socketlocal = new SocketDatagrama(PUERTO_LOCAL);
}

struct mensaje* Respuesta::getRequest(void)
{
	PaqueteDatagrama p(TAM_MAX_DATA);
	socketlocal->recibe(p);
	mensajeCS = (struct mensaje*)p.obtieneDatos();
	return mensajeCS;
}


void Respuesta::sendReply(char *respuesta, char *ipCliente, int puertoCliente)
{
	PaqueteDatagrama paquete((char *)mensajeCS, sizeof(mensajeCS), ipCliente, puertoCliente);
	socketlocal->envia(paquete);
}