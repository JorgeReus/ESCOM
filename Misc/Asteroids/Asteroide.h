#include <vector>
#include "Coordinate.h"

#ifndef ASTEROID_H_
	#define ASTEROID_H_
	class Asteroid
	{
		private:
			int radio;
  			double posX;
  			double posY;
  			std::vector<Coordinate> v;
  			double vx;
  			double vy;
		public:
			Asteroid();
			void draw();
			void rotate(int);
			void move();
			bool outOfLimits();
			~Asteroid();
	};
#endif