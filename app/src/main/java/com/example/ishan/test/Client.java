package com.example.ishan.test;

import android.os.AsyncTask;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {

    lan activity;
    String destIP;
    int destPORT;
    String message_rec;
    String message_send;
    String conversation= "";
    int flag=1;

    Client(lan activity){
        this.activity = activity;
        destIP = activity.server_ip_et.getText().toString();
        destPORT = Integer.parseInt(activity.server_port_et.getText().toString());
        Thread client_thread = new Thread(new SocketClientThread());
        client_thread.start();
    }

    public class SocketClientThread extends Thread{

        public void run() {
            DataInputStream input_stream;
            DataOutputStream output_stream;
            Socket socket = null;

            try {
                socket = new Socket(destIP, destPORT);
                input_stream = new DataInputStream(socket.getInputStream());
                output_stream = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    //RECEIVE
                    if (input_stream.available() > 0) {
                        message_rec = input_stream.readUTF();
                        conversation += "Friend : " + message_rec;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.conversation_tv.setText(conversation);
                            }
                        });
                    }
                    //SEND
                    if(activity.send_cb.isChecked() && flag==1) {
                        flag = 0;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                message_send = activity.message_et.getText().toString();
                                flag = 1;
                            }
                        });
                        if (flag == 1) {
                            output_stream.writeUTF(message_send + "\n");
                            output_stream.flush();
                            conversation += "You : " + message_send + "\n";
                            flag = 0;

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.conversation_tv.setText(conversation);
                                    activity.send_cb.setChecked(false);
                                    flag = 1;
                                }
                            });
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
