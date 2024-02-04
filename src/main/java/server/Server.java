package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(5664);
        ArrayList<Socket> sockets = new ArrayList<>();
        while (true){
            System.out.println("Listing...!");
            Socket accept = serverSocket.accept();//stop thread
            System.out.println("Aceepted");
            sockets.add(accept);
            Thread ob =new Thread(){

                @Override
                public void run() {
                    InputStream inputStream = null;
                    try {
                        inputStream = accept.getInputStream();
                        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                        String line= "";
                        while ((line = bufferedReader.readLine()) !=null){
                            System.out.println(line);

                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            };
            ob.start();



        }
    }
    }

