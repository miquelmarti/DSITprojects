package publisher;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import util.Comms;

public class PublisherStub implements Publisher {

    Socket sc;    
    ObjectOutputStream oos;
    ObjectInputStream ois;
    
    int portPublisherSkel;  
    String topic;

    public PublisherStub(String topic, int portPublisherSkel) {
        this.topic = topic;
        this.portPublisherSkel = portPublisherSkel;
    }
    public int getPortPublisherSkel(){
        return portPublisherSkel;
    }
    
    public void publish(String topic, String event) {
        try {
            connectSkel();

            oos.writeInt(Comms.PUBLISH);
            oos.writeUTF(topic);
            oos.writeUTF(event);
            oos.flush();

            disconnectSkel();
          
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    private void connectSkel() {
        try {
            sc = new Socket(Comms.SERVER, portPublisherSkel);
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
