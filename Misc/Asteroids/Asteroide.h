#ifndef ASTEROID_H_
	#define ASTEROID_H_
	class Asteroid
	{
		private:
			int radio;
			int numPts;
			double angle;
  			double posX;
  			double posY;
		public:
			Asteroid(int r = 5, int pts = 8);
			void draw();
			void showAttrs();
	};
#endif