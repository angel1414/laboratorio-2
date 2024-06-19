Aqui esta la base de datos que utilice

CREATE TABLE TabTickets(
    NumeroTicket VARCHAR2 (50),
    TituloTicket VARCHAR2 (50),
    Descripcion VARCHAR2 (500),
    Autor VARCHAR2 (50),
    Email Varchar2 (50),
    FechaDeInicio DATE,
    EstadoTicket Varchar2 (10),
    FechaFinTicket Date
);

Create Table TabUsuarios(
    Usuario varchar2 (50),
    Contrasena varchar2 (8)
    );
