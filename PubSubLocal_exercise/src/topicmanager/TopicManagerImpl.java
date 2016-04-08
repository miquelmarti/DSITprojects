package topicmanager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import publisher.Publisher;
import publisher.PublisherAdmin;
import publisher.PublisherImpl;
import subscriber.Subscriber;

public class TopicManagerImpl implements TopicManager {

    private Map<String,PublisherAdmin> topicMap;

    public TopicManagerImpl() {
        topicMap = new HashMap<String,PublisherAdmin>();
    }
    
    public boolean isTopic(String topic){
        return topicMap.containsKey(topic);
    }
    
    public Set<String> topics(){
        return topicMap.keySet();
    }
    
    public Publisher addTopic(String topic){        
        PublisherAdmin p;
        if(topicMap.containsKey(topic)){
            p = topicMap.get(topic);
            p.incPublishers();
        }else{
            p = new PublisherImpl(topic);
            topicMap.put(topic,(PublisherAdmin) p);            
        }
        return p;
    }
    
    public int removeTopic(String topic){
        int nump = topicMap.get(topic).decPublishers();
        if(nump==0){
            topicMap.remove(topic).detachAllSubscribers();
        }
        return nump;
        
    }
    
    public boolean subscribe(String topic, Subscriber subscriber){
        topicMap.get(topic).attachSubscriber(subscriber);
        return true;
    }
    
    public boolean unsubscribe(String topic, Subscriber subscriber){
        topicMap.get(topic).detachSubscriber(subscriber);
        return true;
    }
}