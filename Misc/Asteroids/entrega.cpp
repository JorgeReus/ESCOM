// Begin: Asteroid.h
#include <vector>
#include "Coordinate.h"

#ifndef ASTEROID_H_
	#define ASTEROID_H_
	class Asteroid
	{
		private:
			double radio;
  			double posX;
  			double posY;
  			std::vector<Coordinate> v;
  			double vx;
  			double vy;
  			double vw;
		public:
			Asteroid();
			void draw();
			void rotate(double);
			void move();
			bool outOfLimits();
			void showVertex();
			void showCenter();
			~Asteroid();
	};
#endif
// End: Asteroid.h
// Begin: Asteroid.cpp
#include "Asteroid.h"
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
// End: Asteroid.cpp
// Begin: Coordinate.h
#ifndef COORDINATE_H_
#define COORDINATE_H_
class Coordinate{
private:
	double x;
	double y;
public:
    Coordinate(double = 0, double = 0);
    double getX();
    double getY();
    void setX(double);
    void setY(double);
 };
#endif
// Begin: Coordinate.h
// Begin: Coordinate.cpp
#include "Coordinate.h"
#include <iostream>

Coordinate::Coordinate(double xx, double yy) : x(xx), y(yy) { }

double Coordinate::getX()
{
	return x;
}

double Coordinate::getY()
{
	return y;
}

void Coordinate::setX(double xx)
{
	x = xx;
	return;
}

void Coordinate::setY(double yy)
{
	y = yy;
	return;
}
// End: Coordinate.cpp
// Begin: main.cpp
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
// End: main.cpp
// Begin: makefile
main: main.cpp Asteroid.o Coordinate.o
	g++ gfx.o main.cpp Asteroid.o Coordinate.o -o main -lX11
	rm *.o
Asteroid.o: Asteroid.cpp Coordinate.o gfx.o Asteroid.h
	g++ Asteroid.cpp -c
gfx.o: gfx.c gfx.h
	gcc gfx.c -c
Coordinate.o: Coordinate.cpp Coordinate.h
	g++ Coordinate.cpp -c
// End: makefile