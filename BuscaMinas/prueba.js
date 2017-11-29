var io = require("socket.io")(7001);

io.on("connection", function(socket){
	console.log("Cliente conectado");  
	socket.emit("comenzarPartida", "nomas"); //Prueba para contador
	socket.on("infoJugador", function(id, nombre, dificultad){});
});