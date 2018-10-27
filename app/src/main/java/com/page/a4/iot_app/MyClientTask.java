package com.page.a4.iot_app;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClientTask extends AsyncTask<Void, Void, Void> {
    public static String dstAddress;
    public static int dstPort;
    public static String response = "";
    public static String myMessage = "";

    //constructor
    MyClientTask(String addr, int port, String message){
        dstAddress = addr;
        dstPort = port;
        myMessage = message;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;
        myMessage = myMessage.toString();
        try {
            socket = new Socket(dstAddress, dstPort);
            //송신
            OutputStream out = socket.getOutputStream();
            out.write(myMessage.getBytes());

            //수신
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            /*
             * notice:
             * inputStream.read() will block if no data return
             */
            while ((bytesRead = inputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8") ;

            }

            //Toast.makeText(getApplicationContext(), array[1], Toast.LENGTH_SHORT).show();
            //response = response;
            //Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        }/*finally{
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }*/
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        MainActivity.recieveText.setText(response);
        super.onPostExecute(result);
    }
}