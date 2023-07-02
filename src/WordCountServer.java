import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class WordCountServer extends SimpleLineProcessingServer {
  public WordCountServer(int port, String quitCommand, OutputStream os) {
    super(port, quitCommand, new PrintStream(os));
  }

  @Override
  protected String process(String input) {
    String[] strings = input.split(" ");
    int n = 0;
    for (String s : strings) {
      if (s.length() == 0) {
        n++;
      }
    }
    return String.valueOf(strings.length - n);
  }

  public static void main(String[] args) throws IOException {
    WordCountServer wordCountServer = new WordCountServer(10000, "bye", System.out);
    wordCountServer.run();
  }
}