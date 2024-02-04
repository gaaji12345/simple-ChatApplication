package server;

import com.google.gson.Gson;
import dto.TransferData;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
   static ArrayList<Socket> sockets = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(5684);

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
                            TransferData transferData = new Gson().fromJson(line, TransferData.class);
                            manageTransferData(transferData,accept);

                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            };
            ob.start();


        }
      }
      public synchronized  static  void manageTransferData(TransferData data,Socket recvide){
        if (data.getCommand().equals("TXT_MSG")){
            if (data.getTo().equals("ALL")){
                fowrdMessageToAll(recvide,data.getMsg());
            }
        }

      }

      //synchronized --> threads gdk ekama method eka use krnkota prshna adu krna
      public synchronized static void fowrdMessageToAll(Socket recve,String msg){
          for (Socket socket : sockets) {
              if (socket.getPort()==recve.getPort()){
                  continue;
              }
              try {
                  PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
                  out.println(msg);
              }catch (IOException e){
                  e.printStackTrace();
              }

          }
      }



    }

