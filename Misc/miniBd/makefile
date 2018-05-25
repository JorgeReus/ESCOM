paquet: PaqueteDatagrama.cpp PaqueteDatagrama.h
	g++ -c PaqueteDatagrama.cpp
socket: SocketDatagrama.cpp SocketDatagrama.h
	g++ -c SocketDatagrama.cpp
respuesta: respuesta.cpp respuesta.h
	g++ respuesta.cpp -c
solicitud: solicitud.cpp solicitud.h
	g++ solicitud.cpp -c
client: client.cpp PaqueteDatagrama.o SocketDatagrama.o solicitud.o respuesta.o
	g++ client.cpp PaqueteDatagrama.o SocketDatagrama.o solicitud.o respuesta.o -o client -std=c++11
server: server.cpp PaqueteDatagrama.o SocketDatagrama.o solicitud.o respuesta.o
	g++ server.cpp PaqueteDatagrama.o SocketDatagrama.o solicitud.o respuesta.o -o server -std=c++11