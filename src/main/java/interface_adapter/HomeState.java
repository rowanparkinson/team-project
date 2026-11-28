package interface_adapter;

import entity.User;

public class HomeState {
    User user;

    public User getUser() {return this.user;}

    public void setUser(User user) {this.user = user;}
}
