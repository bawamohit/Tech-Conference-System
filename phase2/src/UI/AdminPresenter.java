package UI;

public class AdminPresenter extends UserPresenter {
    public void printAdminMenu(){
        System.out.println("0. Logout\n" +
                "1. Delete Message Chat Between Two Users\n" + "2. Delete Events");
    }
    public void printAskUsername1(){
        System.out.println("Enter username 1");
    }

    public void printAskUsername2(){
        System.out.println("Enter username 2");
    }

    public void confirmChatDeletion(){
        System.out.println("Are you sure you'd like to delete this chat? ('yes' or 'no')");
    }

    public void printAvailableEvents(String compiled) {
        System.out.println("The events available are: \n" + compiled);
    }

    public void printDeleteEventMenu(){

    }
}
