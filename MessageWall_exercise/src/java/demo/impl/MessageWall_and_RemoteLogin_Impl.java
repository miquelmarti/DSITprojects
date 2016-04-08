package demo.impl;

import demo.spec.Message;
import demo.spec.MessageWall;
import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageWall_and_RemoteLogin_Impl implements RemoteLogin, MessageWall {

    private List<Message> messages;
    private HashMap<String,String> users;

    public MessageWall_and_RemoteLogin_Impl() {
        this.messages = new ArrayList<Message>();
        this.users = new HashMap<String,String>();
        this.users.put("miquel","123456");
        this.users.put("oriol","654321");
        this.users.put("agus","Agus69");
    }
    
    

    @Override
    public UserAccess connect(String usr, String passwd) {
       try{
       if(users.get(usr).equals(passwd)) return new UserAccess_Impl(this,usr);
       else return null;
       }catch(NullPointerException e){
           return null;
       }
    }

    @Override
    public void put(String user, String msg) {
        this.messages.add(new Message_Impl(msg,user));
    }

    @Override
    public boolean delete(String user, int index) {
        if(this.messages.get(index).getOwner().equals(user)){
            this.messages.remove(index);
            return true;
        }else return false;
    }

    @Override
    public Message getLast() {
        try{
            return this.messages.get(this.messages.size()-1);
        }catch(Exception e){
            return new Message_Impl("No messages","Admin");
        }
    }

    @Override
    public int getNumber() {
        return this.messages.size();
    }

    @Override
    public List<Message> getAllMessages() {
        return this.messages;
    }

    
}
