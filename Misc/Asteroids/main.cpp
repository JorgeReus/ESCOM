#include "gfx.h"
#include <unistd.h>
#include <iostream>
#include "Asteroide.h"
#include <cstdlib>
using namespace std;
int main()
{
	int t = 1;
 	srand(time(0));
 	gfx_open(800, 600, "Ejemplo Micro Animacion GFX");
 	gfx_color(0, 200, 100);
 	Asteroid asteroid;
 	asteroid.showAttrs();
 	while(1)
 	{
 		gfx_clear();
		asteroid.draw();
		if(gfx_event_waiting())
		{
			if(gfx_wait() == 'q')
				break;			
		} 
		usleep(41666); //24 por segundo
	}
	/*
	while(1)
	{
		t++;
		gfx_clear();
		gfx_line(t*1+80, t*2+40, t*2+40, t*3+80);
	 	gfx_line(t*5+80, t*3+40, t*3+40, t*5+80);
	 	usleep(41666); //24 por segundo
		if(t>=100) 
			t=0;
		if(gfx_event_waiting())
		{
			if(gfx_wait() == 'q')
				break;			
		} 
	}
	*/
 	return 0;
}