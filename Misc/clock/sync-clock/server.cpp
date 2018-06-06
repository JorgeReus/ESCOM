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
	gettimeofday(&tv, NULL);

	


	Respuesta respuesta(PUERTO_LOCAL);
	struct mensaje *msg;
	while(1)
	{
		msg = respuesta.getRequest();
	
		
		printf("IP: %s\n", msg->IP);
		printf("Port: %d\n", msg->puerto);
		printf("Operation id: %d\n", msg->operationId);
		printf("Arguments: %s\n", msg->arguments);
		printf("request:%d\n",msg->requestId);

		tvmsg=(struct timeval *)msg->arguments;
		cout<<"Recibo: "<<tvmsg->tv_sec<<endl;
		cout<<"envio: "<<tv.tv_sec<<endl;

		


		respuesta.sendReply((char *)&tv, msg->IP, msg->puerto, msg->requestId);
		printf(">>>>>>>>>>Fin de operacion<<<<<<<<<<\n\n\n");

		respuesta.cleanReply();
	}
	
}