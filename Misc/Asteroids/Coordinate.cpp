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