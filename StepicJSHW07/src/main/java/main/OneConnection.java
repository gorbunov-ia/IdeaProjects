package main;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.net.Socket;

/**
 * Created by VIRUZ on 02.04.2017.
 */
class OneConnection extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public OneConnection(Socket s) throws IOException {
        System.out.println("New connection!");
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Включаем автоматическое выталкивание:
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                .getOutputStream())), true);
        // Если любой из вышеприведенных вызовов приведет к
        // возникновению исключения, то вызывающий отвечает за
        // закрытие сокета. В противном случае, нить
        // закроет его.
        start(); // вызываем run()
    }

    public void run() {
        try {
            System.out.println("Read string.");
            while (true) {
                String str = in.readLine();
                if (str.equals("Bue."))
                    break;
                System.out.println("Echoing: " + str);
                out.println(str);
            }
            System.out.println("closing...");
        }
        catch (IOException e) {
            System.err.println("IO Exception");
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }
}