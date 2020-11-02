import Event;

public class EventManager {
    public class EventManager {
        private List<Event> events;

        /**
         * The constructor takes events and assigns the variable an appropriate value.
         *
         * @param events list of scheduled events
         */

        public EventManager(){
            this.events = new List<Event>;
        }

        /**
         * Implements Getter, getEvents, for events.
         *
         * @return list of all scheduled events
         */
        public List<Event> getEvents(){
            return events;
        }

        /**
         * Implements modifier, addEvent, for events.
         *
         * @return a boolean indicating if event was successfully added
         */
        public boolean addEvent(Event event){
            start = event.getStartTime();
            for(Event e : events){
                if (e.getStartTime() == start){
                    return false;
                }
            }
            if (start.getHour() >= 9 && start.getHour() <= 17){
                events.add(Event e);
                return true;
            }else{
                return false;
            }
        }

        /**
         * Implements modifier, removeEvent, for events.
         *
         * @return a boolean indicating if event was successfully removed
         */
        public boolean removeEvent(Event event){

        }

        /**
         * Implements modifier, addAttendee, for event in events.
         *
         * @return a boolean indicating if user was successfully added
         */
        public boolean addAttendee(User user, Event event){
            s_event = event.getStartTime();
            if (event.getGuests().contains(user)){
                return false;
            }
            for(Event e : user.getEventsAttending()){
                s_user = e.getStartTime();
                if(start.getHour() == s_event.getHour()){
                    return false;
                }
            }
            event.add(user); // add user to event's guest list
            user.addAttend(event); //add event to user's list of attending events
            return true;
        }

        /**
         * Implements modifier, removeAttendee, for event in events.
         *
         * @return a boolean indicating if user was successfully removed
         */
        public boolean removeAttendee(User user, Event event){
            if (!(event.getGuests().contains(user)) || !(user.getEventsAttending().contains(event))){
                return false;
            }
            event.remove(user); // remove user from event's guest list
            user.removeAttend(event); //remove event from user's list of attending events
            return true;
        }


}
