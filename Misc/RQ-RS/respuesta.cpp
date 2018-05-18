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
#include "Msg.h"

#define PUERTO_LOCAL 7200

SocketDatagrama socketlocal(PUERTO_LOCAL);
struct mensaje mensajeCS;
struct mensaje *ap;

struct mensaje *getRequest(void)
{
	ap = mensajeCS;
	PaqueteDatagrama p(TAM_MAX_DATA);
	socketlocal.recibe(p);

	mensajeCS = (struct mensaje*) p.obtieneDatos();

	return mensajeCS;
}


void sendReply(char *respuesta, char *ipCliente, int puertoCliente)
{



}