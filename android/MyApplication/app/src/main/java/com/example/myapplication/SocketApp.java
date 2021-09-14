package com.example.myapplication;


import android.view.View;
import android.widget.Button;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

import io.socket.emitter.Emitter;

public class SocketApp {
    private Socket androidSocket ;

    SocketApp()  {
        super();
        try {
            androidSocket = IO.socket("http://192.168.31.63:3000");
        }
        catch (URISyntaxException e){e.printStackTrace();}
    }

    public Socket getSocket(){
        return  androidSocket;
    }


}
