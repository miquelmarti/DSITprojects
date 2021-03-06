package publisher;

import java.util.HashSet;
import java.util.Set;
import subscriber.Subscriber;

public class PublisherImpl implements PublisherAdmin, Publisher {

    private Set<Subscriber> subscriberSet;
    private int numPublishers;
    private String topic;
    
    public PublisherImpl(String topic){
        subscriberSet = new HashSet<Subscriber>();
        numPublishers = 1;
        this.topic = topic;
    }
    
    public int incPublishers(){
        return ++numPublishers;
    }
    
    public int decPublishers(){
        return --numPublishers;
    }
    
    public void attachSubscriber(Subscriber subscriber) {
       subscriberSet.add(subscriber);
    }
    
    public void detachSubscriber(Subscriber subscriber) {
       subscriberSet.remove(subscriber);
       subscriber.onClose(topic,"SUBSCRIBER");
    }
    
    public void detachAllSubscribers() {
       for(Subscriber s : subscriberSet){
           s.onClose(topic, "PUBLISHER");
       }
    }
    
    public void publish(String topic, String event) {
       for(Subscriber s : subscriberSet){
           s.onEvent(topic, event);
       }
    }
}
