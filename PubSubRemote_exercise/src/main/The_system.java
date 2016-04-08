/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import topicmanager.TopicManager;
import topicmanager.TopicManagerImpl;
import topicmanager.TopicManagerSkel;
import topicmanager.TopicManagerStub;

/**
 *
 * @author juanluis
 */
public class The_system {
    public static void main(String[] args){
        
//        new TopicManagerSkel(new TopicManagerImpl()).service();
        
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                        ClientSwing client = new ClientSwing(new TopicManagerStub());
                        client.createAndShowGUI();
                }
        });
        
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                        ClientSwing client = new ClientSwing(new TopicManagerStub());
                        client.createAndShowGUI();
                }
        });
    }
}
