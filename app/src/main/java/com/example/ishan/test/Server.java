package com.example.ishan.test;

import android.widget.CheckBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class Server {
    lan activity;
    ServerSocket server_socket;
    static final int server_port = 8080;
    int count =0;
    int flag=1;
    String message_send ;
    String message_rec;
    String conversation = "";

    public Server(lan activity){
        this.activity=activity;
        Thread server_thread = new Thread(new SocketServerThread());
        server_thread.start();
    }

    public class SocketServerThread extends Thread{

        public void run(){
            Socket socket =null;

            try{
                server_socket=new ServerSocket(server_port);
                while (true) {
                    socket = server_socket.accept();
                    count++;
                    SocketServerReplyThread server_reply_thread = new SocketServerReplyThread(socket);
                    server_reply_thread.start();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class SocketServerReplyThread extends Thread{
        Socket host_socket;

        SocketServerReplyThread(Socket socket){
            host_socket = socket;
        }

        public void run(){
            DataOutputStream output_stream;
            DataInputStream input_stream;

            try{
                input_stream = new DataInputStream(host_socket.getInputStream());
                output_stream = new DataOutputStream(host_socket.getOutputStream());
                while(true)
                {
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
                                    activity.message_et.setText("");
                                    flag = 1;
                                }
                            });
                        }
                    }
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
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public String getIP(){
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();
                    if (inetAddress.isSiteLocalAddress()) {
                        ip += inetAddress.getHostAddress();
                    }
                }
            }

        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public String getPort() {
        String port ="";
        port = String.valueOf(server_port);
        return port;
    }
}
