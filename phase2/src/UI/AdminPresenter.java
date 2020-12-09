package UI;

public class AdminPresenter extends UserPresenter {
    public void printAdminMenu(){
        System.out.println("0. Logout\n" +
                "1. Delete Message Chat Between Two Users\n" + "2. Delete Events");
    }
    public void printDeleteChatMenu(){
        System.out.println("Please enter the two usernames associated with the chat you'd like to delete:\n");
    }

    public void printAskUsername1(){
        System.out.println("Enter the first username:");
    }

    public void printAskUsername2(){
        System.out.println("Enter the second username:");
    }

    public void confirmChatDeletion(String u1, String u2){
        System.out.println("Are you sure you'd like to delete the chat between " + u1 + " and " + u2 +
                "? ('yes' or 'no')");
    }

    public void confirmEventDeletion(String eventNum){
        System.out.println("Are you sure you'd like to delete event " + eventNum + "? ('yes' or 'no')");
    }

    public void printEmptyEvents(String compiled) {
        System.out.println("The events with zero attendance are: \n" + compiled);
    }

    public void printDeleteChatError(){
        System.out.println("Error cannot delete chat between the same user");
    }

    public void printDeleteEventMenu(){
        System.out.println("Enter the number associated with an event to delete it:");
    }
}
