#include "Asteroide.h"
#include "gfx.h"
#include <iostream>
#include <cstdlib>
#include <cmath>

using namespace std;
const int MAX_X = 500;
const int MAX_Y = 500;
const int MIN_X = 0;
const int MIN_Y = 0;
Asteroid::Asteroid()
{
    posX = 1 + rand() % (MAX_X);
    posY = 1 + rand() % (MAX_Y);
    int minRadius = 30;
    int maxRadius = 50; 
    int granularity = 15;
    int minVary = 25;
    int maxVary = 75;
    radio = 1 + rand()%2;
    vx = (1 + rand()%7)/radio;
    vy = (1 + rand()%7)/radio;
    vw = 1 + rand() % 360;
    for (double theta=0; theta<=2*M_PI; theta+=2*M_PI/granularity)
    {
        int randVal = rand()%(maxVary - minVary + 1) + minVary;
        double points = (2 * M_PI / granularity) * static_cast<double>(randVal) / 100;
        double angle = theta + points - (M_PI / granularity);
        int radius = rand()%(maxRadius - minRadius + 1) + minRadius;
        double x = static_cast<double>(sin(angle) * radius);
        double y = static_cast<double>(-cos(angle) * radius);

        v1.push_back(posX + radio * x);
        v2.push_back(posY + radio * y);
    }
}
void Asteroid::rotate(int angle)
{
    int theta=M_PI*10/180; // Radians
    for(int i=0; i<v1.size(); i++){
        v1[i]=cos(theta)*(v1[i] - posX) - sin(theta)*(v2[i] - posY) + posX;
        v2[i]=sin(theta)*(v1[i] - posX) + cos(theta)*(v2[i] - posY) + posY;
    }
    return;
}

void Asteroid::draw()
{
    gfx_point(posX, posY);
    for(int i=1; i<v1.size(); i++){
        gfx_line(v1[i-1], v2[i-1], v1[i], v2[i]);
    }
    gfx_line(v1[v1.size()-1], v2[v2.size()-1], v1[0], v2[0]);
    return;
}
void Asteroid::move()
{
    if(posX > MAX_X)
        vx = -vx;
    if(posX < MIN_X)
        vx = -vx;
    if(posY > MAX_Y)
        vy = -vy;
    if(posY < MIN_Y)
        vy = -vy;
    posX+=vx;
    posY+=vy;
    for(int i=0; i<v1.size(); i++){
        v1[i]+=vx;
        v2[i]+=vy;
    }
}