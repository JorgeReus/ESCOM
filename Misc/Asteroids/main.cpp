#include "gfx.h"
#include <unistd.h>
#include <iostream>
#include "Asteroid.h"
#include <cstdlib>
#include <vector>


using namespace std;
int main(int argc, char *argv[])
{
	if(argc != 2)
	{
		cout << "Solo 2 argumentos" << endl;
		return 1;
	}
	int num_asteroids = atoi(argv[1]);
 	srand(time(0));
 	gfx_open(800, 600, "Simulación Asteroides");
 	gfx_color(0, 200, 100);
 	vector<Asteroid> asteroids;
 	for(int i=0; i < num_asteroids; i++)
 	{
 		Asteroid a;
 		asteroids.push_back(a);
 	}
 	int t=10;
 	while(1)
 	{
 		gfx_clear();
		for(int i=0; i < num_asteroids; i++)
 		{
 			asteroids[i].move();
 		}
 		for(int i=0; i < num_asteroids; i++)
 		{
 			asteroids[i].rotate(1);
 		}
		for(int i=0; i < num_asteroids; i++)
 		{
 			if (asteroids[i].outOfLimits()){
 				asteroids.erase(asteroids.begin() + i);
 				asteroids.push_back(Asteroid());
 			}
 			asteroids[i].draw();
 		}
 		if(gfx_event_waiting()){
 			if(gfx_wait() == 'q'){
 				break;
 			}
 		}
		usleep(41666); //24 por segundo
	}
 	return 0;
}