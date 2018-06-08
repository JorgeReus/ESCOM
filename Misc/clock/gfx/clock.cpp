#include <iostream>
#include <string.h>
#include <cmath>
#include "gfxModified.h"
#include <unistd.h>
#include <time.h>
#include <sys/time.h>
#define SEC_PER_DAY   86400
#define SEC_PER_HOUR  3600
#define SEC_PER_MIN   60

using namespace std;

//Resolución de la pantalla
#define ANCHURA 700
#define ALTURA 200

void drawHour(int hour);
void drawMinute(int min);
void drawSecond(int sec);
void drawUSecond(int usec);

int main()
{
    int i, contador;

    struct timeval tv;
    struct timezone tz;
    int hour, min, sec, usec;
    long hms;

    gfx_open(ANCHURA, ALTURA, "Ejemplo gfx_display_ascii");
    gfx_color(0,255,0);
    
    while(1) {

        gettimeofday(&tv, &tz);

        hms = tv.tv_sec % SEC_PER_DAY;
        hms += tz.tz_dsttime * SEC_PER_HOUR;
        hms -= tz.tz_minuteswest * SEC_PER_MIN;
        
        hms = (hms + SEC_PER_DAY) % SEC_PER_DAY;
          
        hour = hms / SEC_PER_HOUR;
        min = (hms % SEC_PER_HOUR) / SEC_PER_MIN;
        sec = (hms % SEC_PER_HOUR) % SEC_PER_MIN; 
        usec = tv.tv_usec/100000;
        
        gfx_clear();

        drawHour(hour);
        drawMinute(min);        
        drawSecond(sec);
        drawUSecond(usec);
        
        usleep(100000);
    }
    

}

void drawHour(int hour){
    int aux = hour%10;
    hour/=10;
    
    gfx_display_ascii(10, 20, 10 , hour+48);
    gfx_display_ascii(80, 20, 10 , aux+48);
    gfx_fill_rectangle(175,70,10,10);
    gfx_fill_rectangle(175,110,10,10);
    gfx_flush();
}

void drawMinute(int minute){
    int aux = minute%10;
    minute/=10;
    gfx_display_ascii(200, 20, 10 , minute+48);
    gfx_display_ascii(280, 20, 10 , aux+48);
    gfx_fill_rectangle(370,70,10,10);
    gfx_fill_rectangle(370,110,10,10);
    gfx_flush();
}

void drawSecond(int sec){
    int aux = sec%10;
    sec/=10;
    gfx_display_ascii(400, 20, 10 , sec+48);
    gfx_display_ascii(480, 20, 10 , aux+48);
    gfx_fill_rectangle(570, 70,10,10);
    gfx_fill_rectangle(570, 110,10,10);
    gfx_flush();
}

void drawUSecond(int usec){
    gfx_display_ascii(600, 20, 10 , usec+48);
    gfx_flush();
}
