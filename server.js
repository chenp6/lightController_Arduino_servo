let PORT = process.env.PORT || 3000;


import express from 'express'; //載入express框架模組
import http from 'http'; //載入http框架模組
import path from 'path';
const __dirname = path.resolve();

let app = express(); //Creates an Express application.
let server = http.Server(app);

app.use('/', express.static(__dirname + '/website/'));

let light = false; //false表示off true表示on
app.get('/', (req, res) => {
    res.sendFile("/index.html");
});


server.listen(PORT, () => {
    console.log("正在聽" + PORT);
});

import { Server } from 'socket.io';
let io = new Server(server);

io.on('connection', (socket) => {



    socket.on('arduino connect', () => {
        console.log('new arduino connect');
    });


    socket.on('user connect', () => {
        socket.emit('set light', light);
        console.log('new user connect');
    });




    socket.on('light on', () => {
        io.emit('light on');
        console.log("light on");
        light = true;
    });

    socket.on('light off', () => {
        io.emit('light off');
        console.log("light off");
        light = false;
    });





});