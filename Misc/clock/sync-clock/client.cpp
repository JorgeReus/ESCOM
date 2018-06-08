#include <stdio.h>
#include <stdlib.h>
#include "mensaje.h"
#include "solicitud.h"


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

#define SERVER_PORT 7200

//Resolución de la pantalla
#define ANCHURA 700
#define ALTURA 200

void drawHour(int hour);
void drawMinute(int min);
void drawSecond(int sec);
void drawUSecond(int usec);

int main (int argc, char *argv[]) {

	Solicitud cliente;

	char *ip;
	char *args = "get_Time-perro-loko.-alv_asdasdasdasdasdasdasd";
	int num;
	if (argc != 2) {
		printf("Usage: ./client server_ip\n");
		return 1;
	} else {
		ip = argv[1];
	}
	char *ms;
	struct mensaje *msg;

    struct timeval tv;
    struct timezone tz;
    int hour, min, sec, usec;
    long hms;
    
    gettimeofday(&tv, &tz);
    args = (char*)&tv;

    struct timeval first = tv;
    printf("Mi hora actual: %ld\n", tv.tv_sec);

    ms = cliente.doOperation(ip, SERVER_PORT, 1, args);

    if(strcmp(ms, "NO") == 0) {
            printf("Mátate ALV\n");
            return 1;
        }

    msg = (struct mensaje*)ms;

    struct timeval *aux = (struct timeval*)msg->arguments;
    tv = *aux;
    printf("La hora que recibo: %ld\n", tv.tv_sec);

    struct timeval second = tv;
    struct timeval third;
    struct timeval result;
    struct timeval final;

    gettimeofday(&third, &tz);
    
    // Diferencia
    timersub(&third, &first, &result);
    result.tv_sec/=2;
    result.tv_usec/=2;
    
    // Suma
    timeradd(&second, &result, &final);
    printf("Hora final %ld\n", final.tv_sec);

    int z;
    if ((z=settimeofday(&final, NULL)) != -1) {
        printf("%d\n", z);
        printf("Success\n");

    } else {
        perror("settimeofday");     
    }
    gettimeofday(&tv, &tz);
    printf("Hora actualizada %ld\n", tv.tv_sec);

    gfx_open(ANCHURA, ALTURA, "Synclock");
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
    return 0;
}

void drawHour(int hour){
    int aux = hour%10;
    hour/=10;
    
    gfx_display_ascii(0, 20, 10 , hour+48);
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