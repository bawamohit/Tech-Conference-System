import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class Room {
    private String name;
    private int capacity;
    private HashMap<LocalDateTime, UUID> schedule;

    public Room(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        schedule = new HashMap<>();
    }

    public String getName(){
        return name;
    }

    public int getCapacity(){
        return capacity;
    }

    public HashMap<LocalDateTime, UUID> getSchedule(){
        return schedule;
    }

}
