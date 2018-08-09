#include "SocketDatagrama.h"
class Respuesta
{
public:
	Respuesta(int p1);
	struct mensaje *getRequest(void);
	void sendReply(char *respuesta, char *ipCliente, int puertoCliente, int requestId);
	void cleanReply();
private:
	SocketDatagrama *socketlocal;
	struct mensaje *mensajeCS; 	
};
