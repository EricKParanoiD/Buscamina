var io = require("socket.io")(7001);
var facil=[];
var intermedio=[];
var dificil=[];
io.on("connection", function(socket){
	console.log("Cliente conectado");  
	socket.on("infoJugador", function(id, nombre, dificultad){
		if(usuarioLibre(nombre)){
		console.log(id+" Jugador: " +nombre+" con dificultad "+dificultad);

		socket.emit("setRoom", unirRoom(nombre, dificultad));
		for(var i=0; i< nombres.length; i++){
		socket.emit("actualizaNombre",nombres[i]);
	}
		socket.broadcast.to('primer').emit("actualizaNombre", nombre);
		nombres.push(nombre);
	}else{
		socket.emit("enUso", "cuenta en uso")
	}
	});

})

function unirRoom(nombre, dificultad){
	switch(dificultad){
		case 1:
			for (var i = 0; i < facil.length; i++) {

				if(facil[i].estado=="espera"){
					if(facil[i].jugadores.length<8){
						facil[i].jugadores.push(nombre);
						return facil[i].nombre;
					}
				}
			}
			var aux=new Object();
			var auxJugadores=[];
			auxJugadores.push(nombre);
			aux.jugadores=auxJugadores;
			aux.nombre="facil"+facil.length;
			aux.estado="espera";
			facil.push(aux);
		break;

		case 2:
			for (var i = 0; i < intermedio.length; i++) {

				if(intermedio[i].estado=="espera"){
					if(intermedio[i].jugadores.length<8){
						intermedio[i].jugadores.push(nombre);
						return intermedio[i].nombre;
					}
				}
			}
			var aux=new Object();
			var auxJugadores=[];
			auxJugadores.push(nombre);
			aux.jugadores=auxJugadores;
			aux.nombre="intermedio"+intermedio.length;
			aux.estado="espera";
			intermedio.push(aux);
		break;

		case 3:
			for (var i = 0; i < dificil.length; i++) {

				if(dificil[i].estado=="espera"){
					if(dificil[i].jugadores.length<8){
						dificil[i].jugadores.push(nombre);
						return dificil[i].nombre;
					}
				}
			}
			var aux=new Object();
			var auxJugadores=[];
			auxJugadores.push(nombre);
			aux.jugadores=auxJugadores;
			aux.nombre="dificil"+dificil.length;
			aux.estado="espera";
			dificil.push(aux);
		break;

		default:
			for (var i = 0; i < intermedio.length; i++) {

				if(intermedio[i].estado=="espera"){
					if(intermedio[i].jugadores.length<8){
						intermedio[i].jugadores.push(nombre);
						return intermedio[i].nombre;
					}
				}
			}
			var aux=new Object();
			var auxJugadores=[];
			auxJugadores.push(nombre);
			aux.jugadores=auxJugadores;
			aux.nombre="intermedio"+intermedio.length;
			aux.estado="espera";
			intermedio.push(aux);
		break;
	}
}
/**
 * metodo que te dice si un usuario esta libre o no dado su nombre
 */
function usuarioLibre(nombre){
	for (var i = 0; i < facil.length; i++) {
		if(contains(facil[i].nombres, nombre){
			return false;
		}
	}

	for (var i = 0; i < intermedio.length; i++) {
		if(contains(intermedio[i].nombres, nombre){
			return false;
		}
	}

	for (var i = 0; i < dificil.length; i++) {
		if(contains(dificil[i].nombres, nombre){
			return false;
		}
	}
return true;
}
function contains(a, obj) {
    var i = a.length;
    while (i--) {
       if (a[i] === obj) {
           return true;
       }
    }
    return false;
}