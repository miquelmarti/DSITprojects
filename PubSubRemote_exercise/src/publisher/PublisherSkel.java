package publisher;

import subscriber.*;
import java.net.ServerSocket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import util.Comms;

public class PublisherSkel implements Runnable {

    ServerSocket ss;
    boolean service_on;
    Map<String,Publisher> publisherMap;

    public PublisherSkel() {

        try{
            ss = new ServerSocket();
            ss.bind(null);
            service_on = true;
            publisherMap = new HashMap<String,Publisher>();
            new Thread(this).start();
        }
        catch(IOException e) {e.printStackTrace();}
    }    
    public int getPort() {
        return ss.getLocalPort();
    }
    public String getHost() {
        try {return InetAddress.getLocalHost().getHostAddress();}
        catch(IOException e) {e.printStackTrace();return null;}
    }
    public void close() {
        service_on = false;
        try{ss.close();}
        catch(IOException e){e.printStackTrace();}
    }
    public void addPublisher(String topic, Publisher publisher){
        publisherMap.put(topic, publisher); //afegit
    }
    public void removePublisher(String topic){
        publisherMap.remove(topic); //afegit
    }
    public void run() {
        while (service_on) {
            try {
                Socket sock = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());

                int metodo = ois.readInt();

                switch (metodo) {
                    case Comms.PUBLISH:
                        String topic = ois.readUTF();
                        String event = ois.readUTF();
                        publisherMap.get(topic).publish(topic, event);
                        break;    
                }            
            }
            catch(IOException e) {
                if(!service_on) { return; }
                e.printStackTrace();
            }
        }
    }
}