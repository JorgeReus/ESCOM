#include <stdio.h>
#include <stdlib.h>
#include "mensaje.h"
#include "respuesta.h"
#include <string>
#include <stack>


#define PUERTO_LOCAL 7200
using namespace std;

int main (int argc, char *argv[]) {

	Respuesta respuesta(PUERTO_LOCAL);
	struct mensaje *msg = respuesta.getRequest();
	printf("Tipo: %d\n", msg->messageType);
	printf("Request id: %d\n", msg->requestId);
	printf("IP: %s\n", msg->IP);
	printf("Port: %d\n", msg->puerto);
	printf("Operation 1d: %d\n", msg->operationId);
	printf("Arguments: %s\n", msg->arguments);
	string resp = "Recibido";
	respuesta.sendReply((char*)resp.c_str(), msg->IP, msg->puerto);
}

void invertString(char *dest, char *src) {
	stack <string> st;
	string aux = src;
	int j = 0;
	for(int i=0; i < aux.size(); i++) {
		if(aux[i] == " ") {
			st.push(aux.)
			j = i;
		}
			

	}
}