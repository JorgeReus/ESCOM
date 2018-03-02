#include "gfx.h"
#include <unistd.h>
#include <iostream>
#include "Asteroide.h"
#include <cstdlib>
#include <vector>


using namespace std;
int main(int argc, char *argv[])
{
 	srand(time(0));
 	Asteroid a;
 	a.showVertex();
 	a.showCenter();
 	cout << endl << endl;
 	a.rotate(90.0);
 	a.showVertex();
 	a.showCenter();
 	return 0;
}