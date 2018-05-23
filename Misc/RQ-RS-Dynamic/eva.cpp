#include <iostream>
#include <inttypes.h>
#include "PaqueteDatagrama.h"
#include "SocketDatagrama.h"

using namespace std;

struct messageSC{
 	char nombre[45]; //Nombre del alumno con fin de cadena incluido
 	char temporal[101]; //Cadena temporal con fin de cadena incluido
 	uint32_t num; //Numero entero que se multiplica por 10E-4
};

int main(int argc, char *argv[]) {
	
	struct messageSC *paq;
	char datos[100];
	char *ap;
	ap = datos;

	SocketDatagrama socket(7300);
	PaqueteDatagrama paquete(sizeof(datos));
	PaqueteDatagrama paquete2(sizeof(paq));

	


	socket.recibe(paquete2);
	paq = (struct messageSC *) paquete.obtieneDatos();

	/*int i = 0;
	int numProm = 0;
	float prom = 0.0;
	strcpy(paq.nombre, "Pacheco Diaz Fernando Jair\0");

	for(i=0; i<100; i++) {
		if((ap[i] > 64 || ap[i] < 91) && (ap[i] > 96 || ap[i] < 123)) {
			prom += ap[i];
			paq.temporal[i] = ap[i];
			numProm++;
		}
	}
	paq.temporal[100] = '\0';

	prom = prom/numProm;
	prom = prom * 100000.0;
	cout << "Promedio \t" << prom << endl;
	paq.num = prom;
	cout << "Num \t" << paq.num << endl;

	PaqueteDatagrama pEnviar((char *)&paq, sizeof(paq), paquete.obtieneDireccion(), paquete.obtienePuerto());
	socket.envia(pEnviar);
*/
	return 0;
	
}
