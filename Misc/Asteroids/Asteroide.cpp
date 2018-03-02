#include "Asteroide.h"
#include "gfx.h"
#include <iostream>
#include <cstdlib>
#include <cmath>
#include "Coordinate.h"

using namespace std;
const int MAX_X = 1300;
const int MAX_Y = 740;
const int MIN_X = 0;
const int MIN_Y = 0;
Asteroid::Asteroid()
{
    int cuadrant = 1 + rand() % 4;
    if(cuadrant == 1) // Upper Cuadrant
    {
        posX = rand() % (MAX_X - MIN_X + 1) + MIN_X;
        posY = rand() % (1 - MAX_Y) - MAX_Y;
    }
    else if(cuadrant == 2) // Lower Cuadrant
    {
        posX = rand() % (MAX_X - MIN_X + 1) + MIN_X;
        posY = rand() % (MAX_Y + 1) + MAX_Y;
    }
    else if(cuadrant == 3) // Left Cuadrant
    {
        posX = rand() % (1 - MAX_X) - MAX_X;
        posY = rand() % (MAX_Y - MIN_Y + 1) + MIN_Y;
    }
    else if(cuadrant == 4) // Right Cuadrant
    {
        posX = rand() % (MAX_X + 1) + MAX_X;
        posY = rand() % (MAX_Y - MIN_Y + 1) + MIN_Y;
    }
    // radius multiplier of the asteoroid
    radio = 1 + rand()%2;
    // Set the directional velocity
    vx = (rand()%(40) - 20)/radio;
    vy = (rand()%(40) - 20)/radio;
    vw = rand()%(21) -10;
    // Create cirlce around the main circle
    int minRadius = 30;
    int maxRadius = 50; 
    int granularity = 15;
    int minVary = 25;
    int maxVary = 75;
    for (double theta=0; theta<=2*M_PI; theta+=2*M_PI/granularity)
    {
        //Create the asteroid
        int randVal = rand()%(maxVary - minVary + 1) + minVary;
        double points = (2 * M_PI / granularity) * randVal / 100.0;
        double angle = theta + points - (M_PI / granularity);
        int radius = rand()%(maxRadius - minRadius + 1) + minRadius;
        double x = static_cast<double>(sin(angle) * radius);
        double y = static_cast<double>(-cos(angle) * radius);
        // Store in vector
        v.push_back(Coordinate(posX + radio * x, posY + radio * y));
    }
}
Asteroid::~Asteroid()
{

}
void Asteroid::rotate(double angle)
{
    double theta=M_PI*(angle + vw)/180.0; // Radians
    for(int i=0; i < v.size(); i++){
        double x1 = cos(theta)*(v[i].getX() - posX);
        double x2 = sin(theta)*(v[i].getY() - posY);
        double y1 = sin(theta)*(static_cast<double>(v[i].getX()) - posX);
        double y2 = cos(theta)*(static_cast<double>(v[i].getY()) - posY);
        v[i].setX(x1 - x2 + posX);
        v[i].setY(y1 + y2 + posY);
    }
    return;
}

void Asteroid::draw()
{
    gfx_point(posX, posY);
    for(int i=1; i<v.size(); i++){
        gfx_line(v[i-1].getX(), v[i-1].getY(), v[i].getX(), v[i].getY());
    }
    gfx_line(v[v.size()-1].getX(), v[v.size()-1].getY(), v[0].getX(), v[0].getY());
    return;
}
void Asteroid::showVertex(){
    for(int i=0; i < v.size(); i++)
        cout << "X:" << v[i].getX() << " Y:" << v[i].getY() << endl;
    return;
}
void Asteroid::showCenter(){
    cout << posX << "," << posY << endl;
    return;
}
void Asteroid::move()
{
    posX+=vx;
    posY+=vy;
    for(int i=0; i<v.size(); i++)
    {
        v[i].setX(v[i].getX() + vx);
        v[i].setY(v[i].getY() + vy);
    }
}
bool Asteroid::outOfLimits() 
{
    double aux = radio * 100;
    bool isOut = false;
    if(posX > MAX_X + aux)
        isOut = true;
    if(posX < MIN_X - aux)
        isOut = true;
    if(posY > MAX_Y + aux)
        isOut = true;
    if(posY < MIN_Y - aux)
        isOut = true;

    return isOut;
}