#include "PaqueteDatagrama.h"
#include <iostream>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

PaqueteDatagrama::PaqueteDatagrama(char *data, unsigned int len, char *address, int port) {
	datos = (char *)malloc(len*sizeof(char));
	memcpy(datos, data, len);
	longitud = len;
	ip = (char *)malloc(strlen(address));
	memcpy(ip, address, strlen(address));
	puerto = port;
}

PaqueteDatagrama::PaqueteDatagrama(unsigned int len) {
	datos = (char *)malloc(len*sizeof(char));
	longitud = len;
	ip = (char *)malloc(16);
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
	ip = (char *)malloc(strlen(address));
	memcpy(ip, address, strlen(address));
}

void PaqueteDatagrama::inicializaDatos(char *data) {
	datos = (char *)malloc(sizeof(char)*longitud);
	memcpy(datos, data, longitud);
}