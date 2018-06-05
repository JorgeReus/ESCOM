gfxModified: gfxModified.c gfxModified.h
	gcc gfxModified.c -c  -I/usr/X11R6/include
paquet: PaqueteDatagrama.cpp PaqueteDatagrama.h
	g++ -c PaqueteDatagrama.cpp
socket: SocketDatagrama.cpp SocketDatagrama.h
	g++ -c SocketDatagrama.cpp
respuesta: respuesta.cpp respuesta.h
	g++ respuesta.cpp -c
solicitud: solicitud.cpp solicitud.h
	g++ solicitud.cpp -c
client: client.o gfxModified.o PaqueteDatagrama.o SocketDatagrama.o solicitud.o respuesta.o
	g++ -std=c++11  client.o gfxModified.o PaqueteDatagrama.o SocketDatagrama.o solicitud.o respuesta.o -o client -lX11 -L/usr/X11R6/lib 
clock.o: clock.cpp gfxModified.o PaqueteDatagrama.o SocketDatagrama.o solicitud.o respuesta.o
	g++ -std=c++11 clock.cpp -c 
server: server.cpp PaqueteDatagrama.o SocketDatagrama.o solicitud.o respuesta.o
	g++ server.cpp PaqueteDatagrama.o SocketDatagrama.o solicitud.o respuesta.o -o server -std=c++11

