#include <vector>

#ifndef ASTEROID_H_
	#define ASTEROID_H_
	class Asteroid
	{
		private:
			int radio;
  			double posX;
  			double posY;
  			std::vector<float> v1;
  			std::vector<float> v2;
  			double vw;
		public:
			Asteroid();
			void draw();
			void rotate(int);
	};
#endif