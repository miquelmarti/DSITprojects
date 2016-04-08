package demo.impl;

import demo.spec.Message;

public class Message_Impl implements Message, java.io.Serializable{
    
	private String user, message;

    Message_Impl(String msg, String user) {
        this.user=user;
        this.message=msg;
    }

    @Override
    public String getContent() {
        return this.message;
    }

    @Override
    public String getOwner() {
        return this.user;
    }

	
}

