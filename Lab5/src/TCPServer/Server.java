package TCPServer;

import TCPInterfaces.Executable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

     static ServerSocket servSock = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter server port:");
        int serverPort = input.nextInt();
        servSock = new ServerSocket(serverPort);
        Socket clientSocket = servSock.accept();

        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        String classFile = (String) in.readObject();
        classFile = classFile.replaceFirst("client", "server");
        byte[] b = (byte[]) in.readObject();
        FileOutputStream fos = new FileOutputStream(classFile);
        fos.write(b);

        Executable ex = (Executable) in.readObject();

        double startTime = System.nanoTime();
        Object output = ex.execute();
        double endTime = System.nanoTime();
        double completionTime = endTime - startTime;

        ResultImpl r = new ResultImpl(output, completionTime);
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        classFile = "./out\\production\\Lab5\\TCPServer\\ResultImpl.class";
        out.writeObject(classFile);
        FileInputStream fis = new FileInputStream(classFile);
        byte[] bo = new byte[fis.available()];
        fis.read(bo);
        out.writeObject(bo);
        out.writeObject(r);
    }
}
