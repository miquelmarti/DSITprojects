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
        if(topicMap.containsKey(topic))
            return true;
        else
            return false;
    }
    public Set<String> topics(){
        return topicMap.keySet();
    }
    public Publisher addTopic(String topic){ 
        if(topicMap.get(topic) == null){
            PublisherAdmin p = new PublisherImpl(topic);
            topicMap.put(topic, p);
            return p;
        }
        else{
            PublisherAdmin pa = topicMap.get(topic);
            pa.incPublishers();
            return pa;
        }
    }
    public int removeTopic(String topic){
        PublisherAdmin pa = topicMap.get(topic);
        int num = pa.decPublishers();
        if(num == 0){
            topicMap.remove(topic).detachAllSubscribers();
            return num;
        }
        else{
            return num;
        }
    }
    
    public boolean subscribe(String topic, Subscriber subscriber){
        if(isTopic(topic)){
            topicMap.get(topic).attachSubscriber(subscriber);
            return true;
        }else
            return false;
    }
    
    public boolean unsubscribe(String topic, Subscriber subscriber){
        if(isTopic(topic)){
            topicMap.get(topic).detachSubscriber(subscriber);
            return true;
        }else
            return false;
    }
    
    
    public void clearTopics(){
        topicMap.clear();
    }
}