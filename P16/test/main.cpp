#include "gfx.h"
#include <unistd.h>
#include "datagramPaquet.h"
#include "datagramSocket.h"
#include <iostream>
#include <cstdlib>

using namespace std;
int main(int argc, char *argv[])
{
	if(argc != 2)
	{
		cout << "Solo 2 argumentos" << endl;
		return 1;
	}
 	gfx_open(800, 600, "Fourier");
 	gfx_color(0, 200, 100);
 	while(1)
 	{
 		gfx_clear();
 		if(gfx_event_waiting()){
 			if(gfx_wait() == 'q'){
 				break;
 			}
 		}
		usleep(41666); //24 por segundo
	}
 	return 0;
}