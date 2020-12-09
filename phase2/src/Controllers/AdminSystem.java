package Controllers;

import UI.AdminPresenter;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AdminSystem extends UserSystem{
    private AdminPresenter presenter;

    public AdminSystem(){
        this.presenter = new AdminPresenter();
    }

    /**
     * Implements the run method for all admin users.
     * With this method, an admins can logout, message (view, send, receive), view available events,
     * signup and remove themselves from events.
     *
     */
    @Override
    void run(String username, TechConferenceSystem tcs) {
        Scanner scanner = new Scanner(System.in);
        label:
        while (true) {
            presenter.printAdminMenu();
            String choice = scanner.nextLine();

            switch (choice){
                case "0":
                    presenter.printLoggedOut();
                    break label;
                case "1": // Delete Chat
                    deleteMessageChat(scanner, tcs);
                    break;
                case "2":
                    presenter.printDeleteEventMenu();
                    List<UUID> emptyEventsIds = tcs.getEM().getEmptyEvents();
                    List<String> emptyEventStrings = tcs.getEM().getEventsStrings(emptyEventsIds);
                    presenter.printEmptyEvents(formatInfo(emptyEventStrings));

                    String eventNum;

                    presenter.printDeleteEventMenu();
                    presenter.printBackToMainMenu();

                    eventNum = validInput("^[0-" + (emptyEventsIds.size() - 1) + "]$|^.{0}$", scanner ,tcs);
                    if (eventNum.equals("")){
                        break;
                    }
                    presenter.confirmEventDeletion(eventNum);
                    String confirmation = scanner.nextLine();
                    if (confirmation.equals("yes")){
                        UUID id = emptyEventsIds.get(Integer.parseInt(choice));
                        if (!tcs.getEM().removeEvent(id)) {
                            presenter.printDNE(("the event " + tcs.getEM().getEventName(id)));
                            presenter.printEventActionFail("removed");
                            break;
                        }
                        tcs.getRM().removeEventFromSchedule(id);
                        presenter.printEventActionSuccess("removed");
                    }
                    break;
            }
        }
    }

    private void deleteMessageChat(Scanner scanner, TechConferenceSystem tcs){
        String username1;
        String username2;
        presenter.printDeleteChatMenu();
        while (true) {
            presenter.printAskUsername1();
            presenter.printBackToMainMenu();
            username1 = scanner.nextLine();
            if (username1.equals("")){
                return;
            }
            if (!tcs.getUM().isRegistered(username1)) {
                presenter.printUserDoesNotExist();
            } else {
                break;
            }
        }
        while (true) {
            presenter.printAskUsername2();
            presenter.printBackToMainMenu();
            username2 = scanner.nextLine();
            if (username2.equals("")){
                return;
            }
            if (!tcs.getUM().isRegistered(username1)) {
                presenter.printUserDoesNotExist();
            } else {
                break;
            }
        }
        presenter.confirmChatDeletion(username1, username2);
        String confirmation = scanner.nextLine();

        if (confirmation.equals("yes")){
            tcs.getMM().deleteMutualThread(username1, username2);
        }
    }
}
