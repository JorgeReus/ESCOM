#include "Spider.h"
#include "Coord.h"
#include <cmath>
#include "gfx.h"

Spider::Spider(Coord c, int r) {
	gfx_color(500, 0, 0);
	// Circle
	for(double theta = 0; theta < 2 * M_PI; theta += .01) {
		int x = c.x + r * cos(theta);
		int y = c.y - r * sin(theta);
		gfx_point(x, y);
	}
	// Legs
	int rl = r * 2;
	double leg = (2 * M_PI) /14.0;
	int i = 0;
	for(double theta = 0; theta < 2 * M_PI; theta += leg) {
		int x = c.x + rl * cos(theta);
		int y = c.y - rl * sin(theta);
		if (i != 2 && i != 3 && i != 4 && i != 9 && i != 10 && i != 11) {
			gfx_line(c.x, c.y, x, y);
		}
		i++;
	}
	// Spiral
	int round = r / 2;
	int n = 10;
	for(int count = 0; count - 5 < round * n; count++) {
		double angle = .2 * count;
		double theta = .2 * (count + 1);
		int x1 = c.x + angle * cos(angle);
		int y1 = c.y + angle * sin(angle);
		int x2 = c.x + theta * cos(theta);
		int y2 = c.y + theta * sin(theta);
		gfx_line(x1, y1, x2, y2);
	}
	gfx_flush();
}