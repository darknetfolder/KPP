package TCPClient;

import TCPInterfaces.Result;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static int port = -1;
    private static String server = null;
    private Socket socket = null;
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    public Client(String server, int port) {
        this.port = port;
        this.server = server;
                try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(server, port), 1000);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (InterruptedIOException e) {
            System.out.println("Error: " + e);
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter server name:");
        server = input.nextLine();

        System.out.println("Enter port:");
        port = Integer.parseInt(input.nextLine());
        Socket client = new Socket(server, port);

        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());

        String classFile = "./out\\production\\Lab5\\TCPClient\\JobOne.class";
        out.writeObject(classFile);
        FileInputStream fis = new FileInputStream(classFile);
        byte[] b = new byte[fis.available()];
        fis.read(b);
        out.writeObject(b);

        System.out.println("Enter number:");
        int num = Integer.parseInt(input.nextLine());
        JobOne aJob = new JobOne(num);

        out.writeObject(aJob);

        ObjectInputStream in = new ObjectInputStream(client.getInputStream());

        classFile = (String) in.readObject();
        b = (byte[]) in.readObject();
        FileOutputStream fos = new FileOutputStream(classFile);
        fos.write(b);

        Result r = (Result) in.readObject();

        System.out.println("result = " + r.output() + ", time taken = " + r.scoreTime() + "ns");
    }

}