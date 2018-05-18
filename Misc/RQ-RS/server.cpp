#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include "mensaje.h"
#include "respuesta.h"
#include <string>
#include <stack>

#define PUERTO_LOCAL 7200
using namespace std;

void invertString(char *dest, char *src);

int main (int argc, char *argv[]) {

	Respuesta respuesta(PUERTO_LOCAL);
	struct mensaje *msg = respuesta.getRequest();
	printf("Tipo: %d\n", msg->messageType);
	printf("Request id: %d\n", msg->requestId);
	printf("IP: %s\n", msg->IP);
	printf("Port: %d\n", msg->puerto);
	printf("Operation 1d: %d\n", msg->operationId);
	printf("Arguments: %s\n", msg->arguments);
	
	char dest[sizeof(msg->arguments)+1];
	invertString(dest, (char *)msg->arguments);
	respuesta.sendReply((char*)dest, msg->IP, msg->puerto);
	
}

void invertString(char *dest, char *src) {
	stack <string> st;
	string aux = src;
	int j = 0;
	char *token;
	token = strtok(src, " ");

	while(token != NULL)
	{
		st.push(token);
		token = strtok(NULL, " ");
	}

	while(!st.empty())
	{
		strcat(dest, st.top().c_str());
		strcat(dest, " ");
		st.pop();
	}

}