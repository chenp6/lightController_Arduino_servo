package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MainActivity extends AppCompatActivity {

    private Socket socket;
    boolean connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect = true;
        SocketApp app = new SocketApp();
        socket = app.getSocket();
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on("set light", onSetLight);
        socket.on("light on", onLightOn);
        socket.on("light off", onLightOff);

        socket.connect();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }


    Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            socket.emit("user connect");
        }
    };

    Emitter.Listener onSetLight = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (args[0].equals(false)) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lightOff();
                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lightOn();
                    }
                });
            }
            System.out.println("set light finished");
        }
    };

    Emitter.Listener onLightOff = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lightOff();
                }
            });
        }
    };

    Emitter.Listener onLightOn = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lightOn();
                }
            });
        }
    };


    public View.OnClickListener lightOffListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            lightOff();
            socket.emit("light off");
        }
    };

    public View.OnClickListener lightOnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            lightOn();
            socket.emit("light on");

        }
    };

    public void lightOff() {
        Button btn = (Button) findViewById(R.id.lightControlBtn);
        btn.setText("開燈");
        btn.setOnClickListener(lightOnListener);
    }


    public void lightOn() {
        Button btn = (Button) findViewById(R.id.lightControlBtn);
        btn.setText("關燈");
        btn.setOnClickListener(lightOffListener);
    }

    public Activity getActivity() {
        return this;
    }

}
