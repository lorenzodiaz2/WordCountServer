import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleLineProcessingServer {
  protected final int port;
  protected final String quitCommand;
  protected final PrintStream ps;

  public SimpleLineProcessingServer(int port, String quitCommand, OutputStream os) {
    this.port = port;
    this.quitCommand = quitCommand;
    ps = new PrintStream(os);
  }

  public void run() throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      while (true) {
        Socket socket = serverSocket.accept();
        handleClient(socket);
      }
    }
  }

  protected void handleClient(Socket socket) throws IOException {
    ps.printf("[%1$tY-%1$tm-%1$td %1$tT] Connection from %2$s.%n", System.currentTimeMillis(), socket.getInetAddress());
    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    int requestsCounter = 0;
    while (true) {
      String line = br.readLine();
      if (line.equals(quitCommand)) {
        break;
      }
      printWriter.println(process(line));
      requestsCounter = requestsCounter + 1;
    }
    socket.close();
    ps.printf("[%1$tY-%1$tm-%1$td %1$tT] Disconnection of %2$s after %3$d requests.%n", System.currentTimeMillis(), socket.getInetAddress(), requestsCounter);
  }

  protected String process(String input) {
    return input;
  }
}