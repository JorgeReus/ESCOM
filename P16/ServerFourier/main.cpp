#include <iostream>
#include <unistd.h>
#include "gfx.h"
#include "PaqueteDatagrama.h"
#include "SocketDatagrama.h"

using namespace std;

int main(int argc, char *argv[]) {

	char datos[6];
	char *ap;
	ap = datos;

	gfx_open(800, 600, "Fourier");

	gfx_line(0,300,800,300);
	gfx_line(400,0,400,600);
	gfx_flush();

	gfx_color(255,0,0);

	SocketDatagrama socket(9999);
	PaqueteDatagrama paquete(2*sizeof(float));

	while(1) {
		socket.recibe(paquete);
		ap = paquete.obtieneDatos();
		char x[4] = {ap[0], ap[1], ap[2], '\0'};
		char y[4] = {ap[3], ap[4], ap[5], '\0'};
		cout << "Recibe\t" << atoi(x) << "\t" << atoi(y) << endl;
		gfx_point(atoi(x),atoi(y));
		gfx_flush();
	}

	return 0;

}