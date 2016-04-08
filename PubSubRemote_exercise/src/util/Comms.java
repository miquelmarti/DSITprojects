package util;

public interface Comms {

  public static final String SERVER = "localhost";
  public static final int PORT = 3456;

  public static final int ADD_TOPIC		= 0;
  public static final int REMOVE_TOPIC  = 1;
  public static final int IS_TOPIC		= 2;
  public static final int TOPICS		= 3;
  public static final int SUBSCRIBE		= 4;
  public static final int UNSUBSCRIBE   = 5;
  
  public static final int PUBLISH		= 6;
  
  public static final int CLOSE_SUB		= 7;
  public static final int ON_EVENT		= 8;
  public static final int CLEAR_TOPICS		= 9;


}
