package interfaces;

import models.Port;

import java.util.ArrayList;
import java.util.Objects;

public interface User {
    public models.User findUserByName(String name);
    public ArrayList<models.User> getAllUsers();

    public String getUsername();

    public String getPassword();

    public boolean isAdmin();

    public Port getManagingPort();


    public models.User authenticate();

    public String toStringSaveFileFormat();
    @Override
    public String toString();
}

    

