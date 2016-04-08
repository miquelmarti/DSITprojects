package topicmanager;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import publisher.Publisher;
import publisher.PublisherSkel;
import subscriber.Subscriber;
import subscriber.SubscriberStub;
import util.Comms;

public class TopicManagerSkel {

  private TopicManager manager;
  private boolean service_on;
  private ServerSocket ss;
  private Map<String,Subscriber> subscriberMap;
  private PublisherSkel publisherSkel;
  
  public TopicManagerSkel(TopicManager manager) {
    this.manager = manager;
    service_on = true;
    try{ ss = new ServerSocket(Comms.PORT); }
    catch(IOException e) {e.printStackTrace();}
    subscriberMap = new HashMap<String,Subscriber>();  
    publisherSkel = new PublisherSkel();
    new Thread(new ToStopFromKeyboard()).start();
  }
  public static void main(String[] args){
      new TopicManagerSkel(new TopicManagerImpl()).service();
  }
  private class ToStopFromKeyboard implements Runnable {
      public void run() {
          while(true){
              System.out.println("If you want to end service write CR");
              BufferedReader keybd = new BufferedReader(new InputStreamReader(System.in));
              String command = null;
              try{
                  command = keybd.readLine();
                  if(command==null || command.equals("")) {
                      System.out.println("Program ends... ");
                      service_on = false;
                      if(publisherSkel!=null)
                          publisherSkel.close();
                      ss.close();
                      return;
                  }
              }
              catch(IOException e) {e.printStackTrace();}
              System.out.println("Unrecognized command, try again...");
          }
      }
  }
  public void service() {
    try {
      while (service_on) {
        Socket sc = ss.accept();
        ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(sc.getOutputStream());

        int metodo = ois.readInt();
        String topic, host;
        int port;

        switch (metodo) {

            case Comms.ADD_TOPIC:
                topic = ois.readUTF();
                Publisher p = manager.addTopic(topic);
                publisherSkel.addPublisher(topic, p);
                oos.writeInt(publisherSkel.getPort());
                break;

            case Comms.REMOVE_TOPIC:
                topic = ois.readUTF();
                int num = manager.removeTopic(topic);
                
                if (num == 0){
                    publisherSkel.removePublisher(topic);
                }
                
                oos.writeInt(num);
                break;

            case Comms.IS_TOPIC:
                topic = ois.readUTF();
                oos.writeBoolean(manager.isTopic(topic));
                
                break;

            case Comms.TOPICS:
                Set<String> topics = manager.topics();
                for(String s : topics){
                    oos.writeUTF(s);
                }
                oos.writeUTF("fin");
                break;

            case Comms.SUBSCRIBE:
                topic = ois.readUTF();
                host= ois.readUTF();
                port = ois.readInt();
                
                SubscriberStub ss = new SubscriberStub(host, port);
                subscriberMap.put(host, ss);
                
                oos.writeBoolean(manager.subscribe(topic, ss));
                
                break;

            case Comms.UNSUBSCRIBE:
                topic = ois.readUTF();
                host = ois.readUTF();
                port = ois.readInt();
                
                oos.writeBoolean(manager.unsubscribe(topic, subscriberMap.get(host)));
                break;
                
            case Comms.CLEAR_TOPICS:
                manager.clearTopics();
                
                break;
        }

        oos.close();
        ois.close();
        sc.close();
        
      }

    }
    catch (Exception e) {
        if(!service_on) { return; }
        e.printStackTrace();
    }
  }
}
