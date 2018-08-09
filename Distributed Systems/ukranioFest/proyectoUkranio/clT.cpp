#include "Solicitud.h"
#include "Voto.h"
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
using namespace std;
int main(int argc, char const *argv[])
{
	int numeroIte=atoi(argv[1]);
	Solicitud s;
	int total=0;
	int aux;
	int id = 0;
	Voto v;
	char numero[] = "5533446652";
	strcpy(v.numerotelefono, numero);
	char RFC[] = "asd2342345";
	strcpy(v.RFC, RFC);
	char voto[] = "MORENA";
	strcpy(v.partido, voto); 
	
	cout << s.doOperation("127.0.0.1",7300,1,(char*)&v,id) << "\n";
	cout << s.doOperation("127.0.0.1",7300,2,(char*)&v,id) << "\n";
	
	return 0;
}
