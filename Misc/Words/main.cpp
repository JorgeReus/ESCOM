#include <stdlib.h>
#include <fcntl.h>
#include <iostream>
#include <string>
#include <stdio.h>
#include "file.h"

using namespace std;

int main(int argc, char *argv[])
{
	int nbytes = 0;

	if(argc != 3) {
		cout << "Forma de uso: " << argv[0] << "archivo_origen archivo_destino" << endl;
		exit(-1);
	}
	Archivo origen(argv[1]);
	Archivo destino(argv[2], O_WRONLY|O_TRUNC|O_CREAT, 0666);
	while((nbytes = origen.lee(BUFSIZ)) > 0);
	destino.escribe((char*) origen.get_contenido(), origen.obtieneNum_bytes());
	exit(0);
}
