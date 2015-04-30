/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sister2project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Paulus
 */
public final class ClientMailer {
    // Variabel Global
    private static String hostName; // nama host
    private static int hostPort; // nama port
    private static Socket socket; // socket
    private static PrintWriter sender; // sender
    private static BufferedReader receiver; // receivernya pake buffer, kalo mau object input stream monggo wae
    
    //----- Getter & Setter -----//
    public static String getHostName() {
        return hostName;
    }

    public static int getHostPort() {
        return hostPort;
    }

    public static void setHostName(String _hostName) {
        hostName = _hostName;
    }

    public static void setHostPort(int _hostPort) {
        hostPort = _hostPort;
    }
    
        
    // ----- Method Utama -----//
    private ClientMailer(String _hostName, int _hostPort) {
        hostName = _hostName;
        hostPort = _hostPort;
    }
    
    public static void openConnection(){
        try{
            // initiation
            socket = new Socket(hostName,hostPort);
            
            // sender & receiver
            sender = new PrintWriter(socket.getOutputStream(), true);
            receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static String communicate(String message){
        char[] buf = new char[4096];
        int nRead = 0; // default length
        try{
            sender.println(message);
            nRead = receiver.read(buf);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new String(buf,0,nRead);
    }
    
    public static void closeConnection(){
        try{
            socket.close();
            receiver.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
