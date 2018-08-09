#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "Respuesta.h"
#include "mensaje.h"
#include "Voto.h" 
#include <my_global.h>
#include <mysql.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <my_global.h>
#include <mysql.h>
using namespace std;

int Anaya=0;
int Meade=0;
int Amlo=0;
int Bronco=0;
int PAN=0;
int PRI=0;
int PRD=0;
int VERDE=0;
int PT=0;
int MC=0;
int NA=0;
int MORENA=0;
int ES=0;
ofstream myfile;
void registrarVoto(Voto v){

    MYSQL *con = mysql_init(NULL);

    if (con == NULL) 
    {
        fprintf(stderr, "%s\n", mysql_error(con));
        exit(1);
    }

    if (mysql_real_connect(con, "localhost", "root", "root", 
            "ukranio_fest", 0, NULL, 0) == NULL) 
    {
        fprintf(stderr, "%s\n", mysql_error(con));
        mysql_close(con);
        exit(1);
    }

    string query = "insert into voto(telefono, partido, RFC) values";
    string p = string(v.partido);
    string n = string(v.numerotelefono);
    //string r = "hola";
    string r = string(v.RFC);
    //cout << p " " << n << " " << r << "\n";
    string coma = ",";
    string pa = "(";
    string pc = ")";
    string c = "\"";
    string queryf = query + pa + c + n + c + coma + c + p + c + coma +  c + r + c + pc;
    cout << "final: " << queryf << "\n";

    if (mysql_query(con, queryf.c_str())) 
    {
        fprintf(stderr, "%s\n", mysql_error(con));
        mysql_close(con);
        exit(1);
    }

    string cand, part;
    if(strcmp(v.partido,"PAN")==0){
        cand = "Ricardo%";
        part = "PAN";
        /*string q1 = "Update candidato set votos = votos +1 where nombre like \"Ricardo%\"";    
        if (mysql_query(con, q1.c_str())) 
        {
            fprintf(stderr, "%s\n", mysql_error(con));
            mysql_close(con);
            exit(1);
        }
        string q2 = "Update partido set votos = votos +1 where nombre like \"PAN\"";
        if (mysql_query(con, q2.c_str())) 
        {
            fprintf(stderr, "%s\n", mysql_error(con));
            mysql_close(con);
            exit(1);
        }*/
    }else if(strcmp(v.partido,"PRI")==0){
        cand = "Antonio%";
        part = "PRI";
        //PRI++;
        ///Meade++;
    }else if(strcmp(v.partido,"VERDE")==0){
        cand = "Antonio%";
        part = "VERDE";
        //VERDE++;
        //Meade++;
    }else if(strcmp(v.partido,"PT")==0){
        cand = "AMLO%";
        part = "PT";
        //PT++;
        //Amlo++;
    }else if(strcmp(v.partido,"MC")==0){
        cand = "Ricardo%";
        part = "MC";
        //MC++;
        //Anaya++;
    }else if(strcmp(v.partido,"NA")==0){
        cand = "Antonio%";
        part = "NA";
        //NA++;
        //Meade++;
    }else if(strcmp(v.partido,"MORENA")==0){
        cand = "AMLO%";
        part = "MORENA";
        //MORENA++;
        //Amlo++;
    }else if(strcmp(v.partido,"ES")==0){
        cand = "AMLO%";
        part = "ES";
        //ES++;
        //Amlo++;
    }else if(strcmp(v.partido,"PRD")==0){
        cand = "Ricardo%";
        part = "PRD";
        //PRD++;
        //Anaya++;
    }else{
      cand = "Bronco%";
        //Bronco++;
    }

    string q1 = "Update candidato set votos = votos +1 where nombre like" + c+ cand + c;    
    if (mysql_query(con, q1.c_str())) 
    {
        fprintf(stderr, "%s\n", mysql_error(con));
        mysql_close(con);
        exit(1);
    }
    if(strcmp(v.partido,"BRONCO")!=0){
      string q2 = "Update partido set votos = votos +1 where nombre like"+ c + part + c;
      if (mysql_query(con, q2.c_str())) 
      {
          fprintf(stderr, "%s\n", mysql_error(con));
          mysql_close(con);
          exit(1);
      } 
    }
    

    //myfile.open ("votos.txt", std::ofstream::out | std::ofstream::app);
    //myfile << "Tel:"<< v.numerotelefono << "\n";
    //myfile << "RFC:"<< v.RFC << "\n";
    //myfile << "Voto:"<< v.partido << "\n";
    //myfile.close();
}
void recuperarDatos(){
    cout << "Anaya:"<<Anaya<<",Meade:"<<Meade<<",AMLO:"<<Amlo<<",Bronco:"<<Bronco<<"-PAN:"<<PAN<<",PRI:"<<PRI<<",PRD:"<<PRD<<",VERDE:"<<VERDE<<",PT:"<<PT<<",MC:"<<",NA:"<<NA<<",MORENA"<<MORENA<<",ES:"<<ES<<",INDEPENDIENTES:"<<Bronco<<endl;
}

#include <sstream>
using namespace std;

int nbd = 0;

int opLectura()
{
    return nbd;
}

int opEscritura(int c){
    nbd +=c;
    return nbd;
}

int consultaEspecifica(){
	
}

int main(){
  Voto v;
  /*cout << "Incia servior...\n";
  Voto v;
  char numero[] = "5533446652";
  strcpy(v.numerotelefono, numero);
  char RFC[] = "asd2342345";
  strcpy(v.RFC, RFC);
  char voto[] = "BRONCO";
  strcpy(v.partido, voto); 
  registrarVoto(v);*/
  int msgRequestIdS=1;
  char ok[] = "1";
  Respuesta r(7300);
  struct mensaje msg;
  int cantidad;
  char num[TAM_MAX_DATA];
  string stx="";
  stringstream cvstr;
  while(1){
    msg = r.getRequest();
    Voto v;
    memcpy(&v, msg.arguments, sizeof(struct Voto));
    if(msg.operationId==1){
        if(msg.requestId != msgRequestIdS){
            cout << "Inconsistencia" << endl;
            cout << msgRequestIdS << endl;
            r.sendReply(ok,msg.IP,msg.puerto, msg.requestId);
        } else{
          registrarVoto(v);
          cout << v.partido << "\n";
          r.sendReply(ok,msg.IP,msg.puerto, msg.requestId);
          msgRequestIdS++;
      }
  }
  if(msg.operationId==2){
   recuperarDatos();
}
if(msg.operationId==3){
    consultaEspecifica();
}
if(msg.operationId==1){
  if(msg.requestId != msgRequestIdS){
					//cvstr<<opLectura();
  }else{
     registrarVoto(v);
 }
}
if(msg.operationId==2){
  recuperarDatos();
}
if(msg.operationId==3){
   consultaEspecifica();
}

}
return 0;
}
