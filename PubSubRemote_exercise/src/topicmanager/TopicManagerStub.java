package topicmanager;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;
import publisher.Publisher;
import publisher.PublisherStub;
import subscriber.Subscriber;
import subscriber.SubscriberSkel;
import util.Comms;

public class TopicManagerStub implements TopicManager {

    Socket sc;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    SubscriberSkel subscriberSkel;

    public TopicManagerStub() {
        subscriberSkel = new SubscriberSkel();
    }
    public void close() {
        subscriberSkel.close();
    }
    
    public Publisher addTopic(String topic) {
        try {
            connectSkel();

            oos.writeInt(Comms.ADD_TOPIC);
            oos.writeUTF(topic);
            oos.flush();

            int portPublisherSkel = ois.readInt();

            disconnectSkel();
            
            return new PublisherStub(topic, portPublisherSkel);

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }
    public int removeTopic(String topic) {
        try {
            connectSkel();

            oos.writeInt(Comms.REMOVE_TOPIC);
            oos.writeUTF(topic);
            oos.flush();

            int numPublishers = ois.readInt();

            disconnectSkel();

            return numPublishers;

        } catch (Exception e) { e.printStackTrace(); }

        return -1;
    }
    public boolean isTopic(String topic) {
        try {
            connectSkel();

            oos.writeInt(Comms.IS_TOPIC);
            oos.writeUTF(topic);
            oos.flush();

            boolean is_topic = ois.readBoolean();

            disconnectSkel();

            return is_topic;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }
    public Set<String> topics() {
        Set<String> topics = new HashSet<String>();

        try {
            connectSkel();

            oos.writeInt(Comms.TOPICS);
            oos.flush();

            while(true) {
                    String elem = ois.readUTF();
                    if(elem.equals("fin")) break;
                    topics.add(elem);
            }

            disconnectSkel();

        } catch (Exception e) { e.printStackTrace(); }

        return topics;
    }
    public boolean subscribe(String topic, Subscriber subscriber) {
        try {
            
            connectSkel();

            oos.writeInt(Comms.SUBSCRIBE);
            oos.writeUTF(topic);
            oos.writeUTF(subscriberSkel.getHost());
            oos.writeInt(subscriberSkel.getPort());
            oos.flush();

            boolean subscribe_topic_ok = ois.readBoolean();
            
            disconnectSkel();

            if(subscribe_topic_ok) {
                subscriberSkel.addSubscriber(topic, subscriber);
                return subscribe_topic_ok;
            }
            else {
                return false;
            }
        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }
    public boolean unsubscribe(String topic, Subscriber subscriber) {
        try {
           
            connectSkel();

            oos.writeInt(Comms.UNSUBSCRIBE);
            oos.writeUTF(topic);
            oos.writeUTF(subscriberSkel.getHost());
            oos.writeInt(subscriberSkel.getPort());
            oos.flush();

            boolean unsubscribe_topic_ok = ois.readBoolean();
            
            disconnectSkel();

            return unsubscribe_topic_ok;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }
    
    public void clearTopics() {
        try {
           
            connectSkel();

            oos.writeInt(Comms.CLEAR_TOPICS);
            oos.flush();
            
            disconnectSkel();


        } catch (Exception e) { e.printStackTrace(); }

    }

    private void connectSkel() {
        try {
            sc = new Socket(Comms.SERVER, Comms.PORT);
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
