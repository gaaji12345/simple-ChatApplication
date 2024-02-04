package service;

import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

public class ClientSide extends Task<String> {
    Socket socket;

    public ClientSide() throws IOException {
        socket=new Socket("localhost",5664);
    }
    @Override
    protected String call() throws Exception {
        while (true){
            InputStream inputStream=socket.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String msg="";
            while ((msg= bufferedReader.readLine())!=null){
                updateValue(msg);
                System.out.println();
            }
        }
    }
    public void sendToClient(String msg) throws IOException {
        PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
        out.println(msg);
    }
}
