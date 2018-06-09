#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <sstream>
#include <cstring>
#include "mensaje.h"
#include "respuesta.h"
#include <string>
#include <stack>
#include <time.h>
#include <sys/time.h>

#define PUERTO_LOCAL 7200
using namespace std;

int main (int argc, char *argv[]) {

	struct timeval tv;
	struct timeval *tvmsg;

	Respuesta respuesta(PUERTO_LOCAL);
	struct mensaje *msg;

	while(1)
	{
		msg = respuesta.getRequest();
		gettimeofday(&tv, NULL);			//Get the actual time after every request
		printf("%d\n", sizeof(struct timeval));
		printf("IP: %s\n", msg->IP);
		printf("Port: %d\n", msg->puerto);
		printf("Operation id: %d\n", msg->operationId);
		printf("Arguments: %s\n", msg->arguments);
		printf("request:%d\n",msg->requestId);

		tvmsg=(struct timeval *)msg->arguments;

		printf("Recibo: %ld\n", tvmsg->tv_sec);
		printf("Envio : %ld\n", tv.tv_sec);

		respuesta.sendReply((char *)&tv, msg->IP, msg->puerto, msg->requestId);
		printf(">>>>>>>>>>Fin de operacion<<<<<<<<<<\n\n\n");

		respuesta.cleanReply();
	}
	
}