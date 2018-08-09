#include <cmath>
#include "Coord.h"
#include "gfx.h"
#include "Spider.h"
#include <cstdlib>
#include <unistd.h>


using namespace std;

int main(int argc, char *argv[])
{ 
   // For Storing the client's addresses and ports
   // Begin the distribution
   gfx_open(800, 600, "Spiders");
   gfx_color(0, 200, 100);
   while(1) {
      Coord c(200,200);
      Spider s(c, 70);

      Coord c1(400, 100);
      Spider s1(c1, 70);
      if(gfx_event_waiting() && gfx_wait() == 'q') {
         break;
      }
      usleep(41666); //24 por segundo
   }
   return 0;
}

