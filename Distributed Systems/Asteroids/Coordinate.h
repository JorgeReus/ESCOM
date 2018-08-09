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