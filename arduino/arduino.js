'use strict'; //stable JS

const io = require("socket.io-client");
const socket = io.connect("http://localhost:3000/");


socket.on('connect', () => {
    console.log('arduino connected');
    socket.emit('arduino connect');
});




const johnny5 = require('johnny-five');
var EtherPortClient = require("etherport-client").EtherPortClient;

var board = new johnny5.Board({
    port: new EtherPortClient({
        host: "192.168.31.25",
        port: 3030
    }),
    timeout: 1e5,
    repl: false
});



board.on('ready', function () {
    let servo = new johnny5.Servo({
        pin: 4,
        startAt: 90

    });

    socket.on("light on", () => {
        servo.to(150);//turn to angle=150
        console.log("on");
        servo.to(90, 1000);//return to startAt
    });
    
    socket.on("light off", () => {
        servo.to(0);//turn to angle=0
        console.log("off");
        servo.to(90, 1000);//return to startAt
    });

});




