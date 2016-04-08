/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Map;
import javax.swing.JTextArea;
import subscriber.Subscriber;

/**
 *
 * @author juanluis
 */
class SubscriberImpl implements Subscriber {

        private JTextArea messages_TextArea;
        private JTextArea my_subscriptions_TextArea;
        private Map<String,Subscriber> my_subscriptions;

        public SubscriberImpl(ClientSwing clientSwing) {
                this.messages_TextArea = clientSwing.messages_TextArea;
                this.my_subscriptions_TextArea = clientSwing.my_subscriptions_TextArea;
                this.my_subscriptions = clientSwing.my_subscriptions;
        }

        public void onClose(String topic, String cause) {
                if(cause.equals("PUBLISHER")) {
                        messages_TextArea.append("Publishers on "+topic+" have ended\n");
                        my_subscriptions.remove(topic);
                        
                        my_subscriptions_TextArea.setText("");
                        for (String topic2 : my_subscriptions.keySet())
                            my_subscriptions_TextArea.append(topic2+"\n");
                }
                else if(cause.equals("SUBSCRIBER")) {
                        messages_TextArea.append("Subscription ends on topic " +topic+"\n");
                }
        }

        public void onEvent(String topic, String event) {
                messages_TextArea.append(topic+": "+event+"\n");
        }
    }
