#include "Solicitud.h"
#include <iostream>
#include <cstdlib>
#include <stdlib.h> 
#include "Voto.h"
#include <thread>
#include <mutex>
#include <atomic>
#include <map>
#include <string.h>
#include <unistd.h>



std::atomic<unsigned long> global(5500000000);
int var_local(0);

std::map<std::string, unsigned long> ponderaciones;
std::mutex m, m2;


struct Voto votoAux;


void generarPonderacion() {
	ponderaciones["MORENA"] = 16366000;
	ponderaciones["PT"] = 2057000;
	ponderaciones["ES"] = 3220000;
	ponderaciones["PAN"] = 2431000;
	ponderaciones["MC"] = 11220000;
	ponderaciones["PRD"] = 2671428;
	ponderaciones["PRI"] = 13090000;
	ponderaciones["VERDE"] = 2618000;
	ponderaciones["NA"] = 11220000;
	ponderaciones["BRONCO"] = 9350000;
}

std::string generaPartido() {
	int index = (rand()%(10-1))+1;
	switch(index) {
		case 1:
		return "MORENA";
		case 2:
		return "PT";
		case 3:
		return "ES";
		case 4:
		return "PAN";
		case 5:
		return "MC";
		case 6:
		return "PRD";
		case 7:
		return "PRI";
		case 8:
		return "VERDE";
		case 9:
		return "NA";
		case 10:
		return "BRONCO";
	} 
}


// Función que redibe un apuntdor al char en el que guarda un rfc aleatorio
void generarRFC (char *destRFC) {
	// Primeras Cuatro Letras aleatorias
	char nombre[4];
	for (int i = 0; i < 4; i++)
	{
		nombre[i] = 'A' + (random() % 26);
	}
	// Fecha Nacimiento
	char dia = (rand()%(28-1))+1;
	char mes = (rand()%(12-1))+1;
	int anio = (rand()%(1998-1950))+1950;
	// Homoclave
	char homoClave[3];
	for (int i = 0; i < 3; i++)
	{
		homoClave[i] = 'A' + (random() % 26);
	}

	sprintf(destRFC, "%c%c%c%c%02d%02d%4d%c%c%c", 
		nombre[0], nombre[1], nombre[2], nombre[3], dia, mes, anio, homoClave[0], 
		homoClave[1], homoClave[2]);

}

//Funcion que retorna el siguiente numero de telefono "aleatorio". Recibe un numero de telefono.
void generaNumeroTelefono( char* numero ){
	unsigned long auxiliar=global++;
	sprintf( numero, "%u" );
}





void enviaVoto(char ip[]){
	//Ya se genero el voto, mandar a traves de una solicitud rpc
	printf("Mandando el voto %d al servidor %s\n", var_local, ip);
	Solicitud s;
	char* respuesta;
	int intento=0;
	respuesta = s.doOperation(ip,7300,1,(char*)&votoAux,var_local) ;
	if( respuesta[0]!='1' ){
		std::cout<<"Perdido! Volviendo a intentar"<<std::endl;
	}
}

//Funcion que genera un voto 
struct Voto generarVoto(){
	struct Voto voto;
	generaNumeroTelefono( voto.numerotelefono);
	voto.numerotelefono[11]=0;
	generarRFC( voto.RFC);
	do{	 
		strcpy(voto.partido, generaPartido().c_str());
	}
	while(ponderaciones[generaPartido()] == 0);
	m.lock();
	ponderaciones[generaPartido()]-=1;
	var_local++;
	m.unlock();
	

	// printf("Voto generado desde "+ ip+ " telefono: "+ voto.numerotelefono + " rfc: "+ voto.RFC);
	// printf("Voto generado desde %s   rfc: %s telefono: %s partido: %s . Es el voto numero %d \n",ip, voto.RFC, voto.numerotelefono, voto.partido, var_local );
	// std::cout<<"\nVoto generado.\n"<< " RFC: "<<voto.RFC<< " telefono: "<<voto.numerotelefono<<" partido: "<<voto.partido<<" . Voto #"<<var_local<<"\n";

	return voto;
}

int main(int argc, char const *argv[])
{
	srand(time(NULL));
	char ip1[]="10.100.72.162";
	char ip2[]="10.100.72.161"; 
	char ip3[]="192.168.43.232";
	generarPonderacion();
	for(;;){

		votoAux=generarVoto();
		std::thread th1(enviaVoto, ip1), th2(enviaVoto, ip2), th3(enviaVoto, ip3);
		th1.join();
		th2.join();
		th3.join();
	}
	return 0;
}
