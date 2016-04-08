package subscriber;

import java.net.ServerSocket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import util.Comms;

public class SubscriberSkel implements Runnable {

    ServerSocket ss;
    boolean service_on;
    Map<String,Subscriber> subscriberMap;

    public SubscriberSkel() {
        try{
            ss = new ServerSocket();
            ss.bind(null);
            service_on = true;
            subscriberMap = new HashMap<String,Subscriber>();
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
    public synchronized void addSubscriber(String topic, Subscriber subscriber) {
        subscriberMap.put(topic, subscriber); //afegit
    }
    public synchronized void removeSubscriber(String topic) {
        subscriberMap.remove(topic);
    }
    public void run() {
        while (service_on) {
            try {
                Socket sock = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());

                int metodo = ois.readInt();
                String topic = ois.readUTF();
                
                switch(metodo){
                    case Comms.ON_EVENT:
                        String event = ois.readUTF();
                         
                        subscriberMap.get(topic).onEvent(topic, event);
                        break;
                         
                    case Comms.CLOSE_SUB:
                        String cause = ois.readUTF();
                        
                        subscriberMap.get(topic).onClose(topic, cause);
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