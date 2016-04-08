package subscriber;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.io.ObjectOutputStream;
import util.Comms;

public class SubscriberStub implements Subscriber {
    
    Socket sc;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    
    private String host;
    private int port;

    public SubscriberStub(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public void onEvent(String topic, String event) {
        try {
          connectSkel();

          oos.writeInt(Comms.ON_EVENT);
          oos.writeUTF(topic);
          oos.writeUTF(event);

          disconnectSkel();
        }
        catch(Exception e) {e.printStackTrace();}
    }
    public void onClose(String topic, String cause) {
        try {
          connectSkel();

          oos.writeInt(Comms.CLOSE_SUB);
          oos.writeUTF(topic);
          oos.writeUTF(cause);

          disconnectSkel();
        }
        catch(Exception e) {}
    }
    
    private void connectSkel() {
        try {
            sc = new Socket(host, port);
            oos = new ObjectOutputStream(sc.getOutputStream());
            ois = new ObjectInputStream(sc.getInputStream());
        }
        catch(Exception e) { e.printStackTrace(); }
    }
    private void disconnectSkel() {
        try {
            oos.close();
            ois.close();
            sc.close();
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}