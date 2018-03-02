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