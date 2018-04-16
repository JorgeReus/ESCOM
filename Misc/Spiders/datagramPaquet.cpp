#include "datagramPaquet.h"
#include <iostream>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

PaqueteDatagrama::PaqueteDatagrama(char *data, unsigned int len, char *address, int port) {
	datos = (char*)malloc(len * sizeof(char));
	memcpy(datos, data, len);
	longitud = len;
	memcpy(ip, address, strlen(address));
	puerto = port;
}

PaqueteDatagrama::PaqueteDatagrama(unsigned int len) {
	datos = (char*)malloc(len * sizeof(char));
	longitud = len;
	ip[0] = '\0';
	puerto = 0;
}

PaqueteDatagrama::~PaqueteDatagrama() {
	//free(datos);
}

char *PaqueteDatagrama::obtieneDireccion() {
	return ip;
}

unsigned int PaqueteDatagrama::obtieneLongitud() {
	return longitud;
}

int PaqueteDatagrama::obtienePuerto() {
	return puerto;
}

char *PaqueteDatagrama::obtieneDatos() {
	return datos;
}

void PaqueteDatagrama::inicializaPuerto(int port) {
	puerto = port;
} 

void PaqueteDatagrama::inicializaIp(char *address) {
	memcpy(ip, address, strlen(address));
	ip[strlen(address)] = '\0';
}

void PaqueteDatagrama::inicializaDatos(char *data) {
	memcpy(datos, data, longitud);
}