import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class MainServer implements Runnable {
    Socket socket;
    public MainServer(Socket socket) {
        this.socket = socket;
    }
    public static final Object obj = new Object();

    public void run() {
        System.out.printf("connection received from %s\n", socket);
        try {
            // socket open: make PrinterWriter and Scanner from it...
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());
            // read from input, and echo output...
            // gets the info to send to server
            ArrayList<String> workpls = new ArrayList<>();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                workpls.add(line);
                // looks to see if we are reading
                if (line.equals("Read")) {
                    break;
                }
            }
            // if we are reading then it will read the file and send it to manager
            if (workpls.get(0).equalsIgnoreCase("Read")) {
                synchronized (obj) {
                    BufferedReader br = new BufferedReader(new FileReader("data.txt"));
                    String line;
                        while ((line = br.readLine()) != null) {
                        pw.println(line);
                        pw.flush();
                    }
                }
            } else {
                synchronized (obj) {
                    writeToFile(workpls);
                }
            }
            // input done, close connections...
            pw.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // this will write the tostring of manager to the file on the server side
    public void writeToFile(ArrayList<String> workpls) {

        try {
            FileWriter fw = new FileWriter("data.txt", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            // print the data in manager to the file
            for (int i = 0; i < workpls.size(); i++) {
                pw.println(workpls.get(i));
            }

            pw.close();
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
    }
    public void ReadToFile() {

    }
    public static void main(String[] args) throws IOException {
        // allocate server socket at given port...
        ServerSocket serverSocket = new ServerSocket(4343);
        System.out.printf("socket open, waiting for connections on %s\n", serverSocket);
        // infinite server loop: accept connection,
        // spawn thread to handle...
        while (true) {
            Socket socket = serverSocket.accept();
            MainServer server = new MainServer(socket);
            new Thread(server).start();
        }
    }
}
