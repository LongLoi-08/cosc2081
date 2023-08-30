package interfaces;

import models.Port;

public interface User {
    public boolean login(String username, String password);
    public boolean logout();    //id what to do about this yet
    public Port getPort();
    
}
