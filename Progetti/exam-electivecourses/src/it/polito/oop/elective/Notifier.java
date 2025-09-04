package it.polito.oop.elective;


public interface Notifier {
    
    void assignedToCourse(String id, String course);
    
    
    void requestReceived(String id);
}
