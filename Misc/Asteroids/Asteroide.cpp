#include "Asteroide.h"
#include "gfx.h"
#include <iostream>
#include <cstdlib>
#include <cmath>

using namespace std;
const int MAX_X = 1850;
const int MAX_Y = 1050;
Asteroid::Asteroid(int r, int pts)
{
	radio = r;
	numPts = pts;
	//angle = 1 + rand() % 360;
	posX = 1 + rand() % (MAX_X + 100);
	posY = 1 + rand() % (MAX_Y + 100);
}
void Asteroid::draw()
{
	int step = 2*M_PI/20;  // see note 1
    int r = 50;
    int x1;
    int y1;
    int x2;
    int y2;
    x1 = posX;
    y1 = posY;
    for(int theta=0;  theta < 2*M_PI;  theta+=step)
    { 
        
        x2 = posX + r*cos(theta);
        y2 = posY - r*sin(theta);
        x1 = x2;
        y1 = y2;
        gfx_line(x1, y1, x2, y2);
    }
	return;
}
void Asteroid::showAttrs(){
	cout << "X: " << posX << endl;
	cout << "Y: " << posY << endl;
}