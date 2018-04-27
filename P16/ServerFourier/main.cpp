#include <iostream>
#include <unistd.h>
#include "gfx.h"
#include "PaqueteDatagrama.h"
#include "SocketDatagrama.h"

using namespace std;

int main(int argc, char *argv[]) {

	int port;
	if (argc < 2) {
		cout << "Useage: ./server port"<<  endl;
	} else if (argc == 1) {
		port = 6666;
	} else {
		port = atoi(argv[1]);
	}
	gfx_open(800, 600, "Fourier Server");

	// gfx_line(0,300,800,300);
	// gfx_line(400,0,400,600);
	// gfx_flush();

	gfx_color(255,0,0);

	SocketDatagrama socket(port);
	PaqueteDatagrama p(2*sizeof(float));

	while(1) {
		socket.recibe(p);
		char x[4] = {p.obtieneDatos()[0], p.obtieneDatos()[1], p.obtieneDatos()[2], '\0'};
		char y[4] = {p.obtieneDatos()[3], p.obtieneDatos()[4], p.obtieneDatos()[5], '\0'};
		gfx_point(atoi(x),atoi(y));
		gfx_flush();
	}

	return 0;

}