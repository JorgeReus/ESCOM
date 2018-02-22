#include "Asteroide.h"
#include "gfx.h"
#include <iostream>
#include <cstdlib>
using namespace std;
const int MAX_X = 1850;
const int MAX_Y = 1050;
Asteroid::Asteroid(int r, int pts)
{
	radio = r;
	numPts = pts;
	angle = 1 + rand() % 360;
	posX = 1 + rand() % (MAX_X + 100);
	posY = 1 + rand() % (MAX_Y + 100);
}
void Asteroid::draw()
{
	for (int i=0; i < 360; i+=360/numPts)
  	{
    	double degInRad = i*DEG2RAD;
    	glVertex2f(cos(degInRad)*radius, sin(degInRad)*radius);
  	}
	return;
}
void Asteroid::showAttrs(){
	cout << "X: " << posX << endl;
	cout << "Y: " << posY << endl;
}