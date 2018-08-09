#include "PaqueteDatagrama.h"
#include <iostream>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

PaqueteDatagrama::PaqueteDatagrama(char *data, unsigned int len, char *address, int port) {
	datos = (char *)calloc(len,sizeof(char));
	memcpy(datos, data, len);
	longitud = len;
	ip = (char *)calloc(strlen(address),sizeof(char));
	memcpy(ip, address, strlen(address));
	puerto = port;
}

PaqueteDatagrama::PaqueteDatagrama(unsigned int len) {
	datos = (char *)calloc(len,sizeof(char));
	longitud = len;
	ip = (char *)calloc(16,sizeof(char));
	ip[0] = '\0';
	puerto = 0;
}

PaqueteDatagrama::~PaqueteDatagrama() {
	
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
	ip = (char *)calloc(strlen(address),sizeof(char));
	memcpy(ip, address, strlen(address));
}

void PaqueteDatagrama::inicializaDatos(char *data) {
	datos = (char *)calloc(longitud,sizeof(char));
	memcpy(datos, data, longitud);
}