se: seT.cpp SocketDatagrama.o PaqueteDatagrama.o Respuesta.o
	g++ -std=c++11 seT.cpp SocketDatagrama.o PaqueteDatagrama.o Respuesta.o -o se `mysql_config --cflags --libs`
Solicitud.o: Solicitud.cpp Solicitud.h
	g++ Solicitud.cpp -c
Respuesta.o: Respuesta.cpp Respuesta.h
	g++ Respuesta.cpp -c
SocketDatagrama.o: SocketDatagrama.cpp SocketDatagrama.h
	g++ SocketDatagrama.cpp -c
PaqueteDatagrama.o: PaqueteDatagrama.cpp PaqueteDatagrama.h
	g++ PaqueteDatagrama.cpp -c