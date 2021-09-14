'use strict'; //stable JS

//document.write('<script src="/socket.io/socket.io.js"></script>');;

let socket = io();



socket.on('connect', () => {
    console.log('user connected');
    socket.emit('user connect');
});




socket.on('set light', (light) => {

    if (light == false) {
        lightOff();
    } else {
        lightOn();
    }

});





socket.on('light on', () => {
    lightOn();
});

socket.on('light off', () => {
    lightOff();
});


function lightOn() {

    //變更狀態為開  按鈕字為關
    let lightControl = document.getElementById('lightControl');
    lightControl.innerHTML = "關燈";
    lightControl.setAttribute('value', "true");
    lightControl.setAttribute('onclick', "socket.emit('light off')");
}

function lightOff() {
    //變更狀態為關  按鈕字為開
    let lightControl = document.getElementById('lightControl');
    lightControl.innerHTML = "開燈";
    lightControl.setAttribute('value', "false");
    lightControl.setAttribute('onclick', "socket.emit('light on')");
}