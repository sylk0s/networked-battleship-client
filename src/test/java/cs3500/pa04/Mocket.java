package cs3500.pa04;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Mocket extends Socket {
  @Override
  public InputStream getInputStream() throws IOException {
    throw new IOException();
  }

  @Override
  public OutputStream getOutputStream() throws IOException {
    throw new IOException();
  }
}
