var io = require("socket.io")(7001);
var nombres=[];
io.on("connection", function(socket){
	console.log("Cliente conectado: ");  
		
	socket.on("infoJugador", function(id, nombre, dificultad){
		if(!nombres.toLocaleString(nombre)){
		console.log(id+" Jugador: " +nombre+" con dificultad "+dificultad+ " a room: ");
		socket.join('primer');
		socket.emit("setRoom", "primer");
		socket.emit("actualizaNombre",nombres);
	
		socket.broadcast.to('primer').emit("actualizaNombre", nombre);
		nombres.push(nombre);
	}
	});

})