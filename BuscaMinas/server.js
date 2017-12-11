var io = require("socket.io")(7001); //Inicio
var facil=[]; //Declaracion de arreglo para guardar datos de room facil
var intermedio=[]; //intermedio
var dificil=[];	//Dificil
//Acciones en conexion
io.on("connection", function(socket){
	console.log("Cliente conectado");  //Mensaje de conexion
	//Acciones en recibo de informacion de jugador id, nombre del jugador, dificultad
	socket.on("infoJugador", function(id, nombre, dificultad){
		//Si es el usuario esta libre acciones
		if(usuarioLibre(nombre)){
			//Imprime informacion
		console.log(id+" Jugador: " +nombre+" con dificultad "+dificultad);
		//Emite el set room con la informacion devolvida por el metodo que busca una room libre en su dificultad
		var roomUsuario=unirRoom(nombre, dificultad, socket);
		console.log("usuario unido a "+roomUsuario);
		socket.emit("setRoom", roomUsuario);

		if(dificultad==1){
			var roomNumero=buscarRoom(roomUsuario, socket);
			for(var i=0; i< facil[roomNumero].jugadores.length; i++){
			socket.emit("actualizaNombre",facil[roomNumero].jugadores[i]);
	}
		}
		if(dificultad==2){
			var roomNumero=buscarRoom(roomUsuario);
			for(var i=0; i< intermedio[roomNumero].jugadores.length; i++){
			socket.emit("actualizaNombre",intermedio[roomNumero].jugadores[i]);
	}
		}
		if(dificultad==3){
			var roomNumero=buscarRoom(roomUsuario);
			for(var i=0; i< dificil[roomNumero].jugadores.length; i++){
			socket.emit("actualizaNombre",dificil[roomNumero].jugadores[i]);
	}
	
		}

		
		console.log("emito actualizaNombre a room"+roomUsuario+" nombre "+nombre);
		io.in(roomUsuario).emit("actualizaNombre", nombre);


	}else{
		//Si no emitir mensaje de que la cuenta esta en uso
		socket.emit("enUso", "cuenta en uso")
	}
	});

	socket.on("damePrueba", function(mensaje){
		socket.emit("prueba", mensaje+" server");
	});

	socket.on("cerrar", function(room, nombre, dificultad){
		socket.leave(room);
		io.in(room).emit("jugadorDesconectado", nombre);
		if(dificultad=="facil"){
			var pos = facil[buscarRoom(room)].jugadores.indexOf(nombre);
			facil[buscarRoom(room)].jugadores.splice(pos, 1);
		}
		if(dificultad=="intermedio"){

		}
		if(dificultad=="dificil"){

		}

	});

});


function buscarRoom(nombre){
	console.log("buscare"+nombre);
	console.log("longitud facil"+facil.length);
			for (var i = 0; i < facil.length; i++) {
				console.log("vere si facil["+i+"] es nulo= "+facil[i]!=null);
				if(facil[i]!=null){
				console.log("Vere si "+i+" tiene nombre "+facil[i].nombre+"= "+nombre);
					if(facil[i].nombre==nombre){
						console.log("Encontre "+facil[i].nombre+" = "+nombre+" en "+i);
						return i;
					}
			}
			
			}

	console.log("longitud intermedio"+intermedio.length);
			for (var i = 0; i < intermedio.length; i++) {
				console.log("Entre"+i);
				console.log("vere si intermedio["+i+"] es nulo= "+intermedio[i]!=null);
				if(intermedio[i]!=null){
				console.log("Vere si "+i+" tiene nombre "+intermedio[i].nombre+"= "+nombre);
					if(intermedio[i].nombre==nombre){
						console.log("Encontre "+intermedio[i].nombre+" = "+nombre+" en "+i);
						return i;
					}
			}
			
			}

	console.log("longitud dificil"+dificil.length);
			for (var i = 0; i < dificil.length; i++) {
				console.log("vere si dificil["+i+"] es nulo= "+dificil[i]!=null);
				if(dificil[i]!=null){
				console.log("Vere si "+i+" tiene nombre "+dificil[i].nombre+"= "+nombre);
					if(dificil[i].nombre==nombre){
						console.log("Encontre "+dificil[i].nombre+" = "+nombre+" en "+i);
						return i;
					}
			}
			
			}
	}

function unirRoom(nombre, dificultad, socket){
	console.log("unire jugador "+nombre+" con dificultad "+dificultad);
	switch(dificultad){
		case 1:
		console.log("Inicio ciclo facil");
		console.log("facil tiene :"+facil.length);
			for (var i = 0; i < facil.length; i++) {
				console.log("Checare si facil["+i+"] es nulo ="+facil[i]==null);
				if(facil[i]!=null){
				console.log("Checare si facil["+i+"] esta en espera ="+facil[i].estado=="espera");
				if(facil[i].estado=="espera"){
				console.log("Checare si facil["+i+"] tiene mas de ocho jugadores ="+facil[i].jugadores.length);
					if(facil[i].jugadores.length<8){
				console.log("Agregando");
						facil[i].jugadores.push(nombre);
						socket.join(facil[i].nombre);
						io.in(facil[i].nombre).emit("comenzarPartida", "nomas");
						setTimeout(function(){inicioPartida(facil[i].nombre)}, 30000);
						if(facil[i].jugadores.length>7){
							facil[i].estado="ocupado";
						}
						return facil[i].nombre;
					}
				}
			}
			}
			console.log("Creare un nuevo server");
			var aux=new Object();
			var auxJugadores=[];
			auxJugadores.push(nombre);
			aux.jugadores=auxJugadores;
			aux.nombre="facil"+facil.length;
			socket.join(aux.nombre);
			aux.estado="espera";
			facil.push(aux);
			return aux.nombre;
		break;

		case 2:
				console.log("Inicio ciclo intermedio");
		console.log("intermedio tiene :"+intermedio.length);
			for (var i = 0; i < intermedio.length; i++) {
				console.log("Checare si intermedio["+i+"] es nulo ="+intermedio[i]==null);
				if(intermedio[i]!=null){
				console.log("Checare si intermedio["+i+"] esta en espera ="+intermedio[i].estado=="espera");
				if(intermedio[i].estado=="espera"){
				console.log("Checare si intermedio["+i+"] tiene mas de ocho jugadores ="+intermedio[i].jugadores.length);
					if(intermedio[i].jugadores.length<8){
						console.log("Agregando");
						intermedio[i].jugadores.push(nombre);
						socket.join(intermedio[i].nombre);
						io.in(intermedio[i].nombre).emit("comenzarPartida", "nomas");
						setTimeout(function(){inicioPartida(intermedio[i].nombre)}, 30000);
						if(intermedio[i].jugadores.length>7){
							intermedio[i].estado="ocupado";
						}
						return intermedio[i].nombre;
					}
				}
			}
			}
			console.log("Creare un nuevo server");
			var aux=new Object();
			var auxJugadores=[];
			auxJugadores.push(nombre);
			aux.jugadores=auxJugadores;
			aux.nombre="intermedio"+intermedio.length;
			socket.join(aux.nombre);
			aux.estado="espera";
			intermedio.push(aux.nombre);
			return aux.nombre;
		break;

		case 3:
				console.log("Inicio ciclo intermedio");
		console.log("dificil tiene :"+dificil.length);
			for (var i = 0; i < dificil.length; i++) {
				console.log("Checare si dificil["+i+"] es nulo ="+dificil[i]==null);
				if(dificil[i]!=null){
				console.log("Checare si dificil["+i+"] esta en espera ="+dificil[i].estado=="espera");
				if(dificil[i].estado=="espera"){
				console.log("Checare si dificil["+i+"] tiene mas de ocho jugadores ="+dificil[i].jugadores.length);
					if(dificil[i].jugadores.length<8){
						console.log("Agregando");
						dificil[i].jugadores.push(nombre);
						socket.join(dificil[i].nombre);
						io.in(dificil[i].nombre).emit("comenzarPartida", "nomas");
						setTimeout(function(){inicioPartida(dificil[i].nombre)}, 30000);
						if(dificil[i].jugadores.length>7){
							dificil[i].estado="ocupado";
						}
						return dificil[i].nombre;
					}
				}
			}
			}
			console.log("Creare un nuevo server");
			var aux=new Object();
			var auxJugadores=[];
			auxJugadores.push(nombre);
			aux.jugadores=auxJugadores;
			aux.nombre="dificil"+dificil.length;
			socket.join(aux.nombre);
			aux.estado="espera";
			dificil.push(aux);
			return aux.nombre;
		default:
		console.log("default");
		for (var i = 0; i < intermedio.length; i++) {
				if(intermedio[i]!=null){
				if(intermedio[i].estado=="espera"){
					if(intermedio[i].jugadores.length<8){
						socket.join(intermedio[i].nombre);
						intermedio[i].jugadores.push(nombre);
						io.in(intermedio[i].nombre).emit("comenzarPartida", "nomas");
						setTimeout(function(){inicioPartida(intermedio[i].nombre)}, 30000);
						if(intermedio[i].jugadores.length>7){
							intermedio[i].estado="ocupado";
						}
						return intermedio[i].nombre;
					}
				}
			}
			}
			var aux=new Object();
			var auxJugadores=[];
			auxJugadores.push(nombre);
			aux.jugadores=auxJugadores;
			aux.nombre="intermedio"+intermedio.length;
			socket.join(aux.nombre);
			aux.estado="espera";
			intermedio.push(aux);
			return aux.nombre;
			break;	
	}
}
/**
 * metodo que te dice si un usuario esta libre o no dado su nombre
 */
function usuarioLibre(nombre){
	for (var i = 0; i < facil.length; i++) {
		if(contains(facil[i].jugadores, nombre)) {
			return false;
		}
	}

	for (var i = 0; i < intermedio.length; i++) {
		if(contains(intermedio[i].jugadores, nombre)) {
			return false;
		}
	}

	for (var i = 0; i < dificil.length; i++) {
		if(contains(dificil[i].jugadores, nombre)) { 
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

function imprimir(){
	for (var i = 0; i < facil.length; i++) {
				if(facil[i]!=null){
	console.log("*********************************************************************************");
					console.log("Nombre: "+facil[i].nombre);
					console.log("Numero de jugadores: "+facil[i].jugadores.length);
					console.log("Estado: "+facil[i].estado);
	console.log("---------------------------------------------------------------------------------");
					for (var j = 0; j < facil[i].jugadores.length; j++) {
						console.log("Jugador ["+j+"]: "+facil[i].jugadores[j]);
						}
	console.log("*********************************************************************************");

			}
}

	console.log("-----------------------------------------------------------------------------------");

	for (var i = 0; i < intermedio.length; i++) {
				if(intermedio[i]!=null){
	console.log("*********************************************************************************");
					console.log("Nombre: "+intermedio[i].nombre);
					console.log("Numero de jugadores: "+intermedio[i].jugadores.length);
					console.log("Estado: "+intermedio[i].estado);
	console.log("---------------------------------------------------------------------------------");
					for (var j = 0; j < intermedio[i].jugadores.length; j++) {
						console.log("Jugador ["+j+"]: "+intermedio[i].jugadores[j]);
						}
	console.log("*********************************************************************************");

			}
}

	console.log("-----------------------------------------------------------------------------------");

	for (var i = 0; i < dificil.length; i++) {
				if(dificil[i]!=null){
	console.log("*********************************************************************************");
					console.log("Nombre: "+dificil[i].nombre);
					console.log("Numero de jugadores: "+dificil[i].jugadores.length);
					console.log("Estado: "+dificil[i].estado);
	console.log("---------------------------------------------------------------------------------");
					for (var j = 0; j < dificil[i].jugadores.length; j++) {
						console.log("Jugador ["+j+"]: "+dificil[i].jugadores[j]);
						}
	console.log("*********************************************************************************");

			}
}

	console.log("-----------------------------------------------------------------------------------");
}
function inicioPartida(room){
	console.log("Empieza partida"+room);
	io.in(room).emit("inicioPartida", "nomas");
}