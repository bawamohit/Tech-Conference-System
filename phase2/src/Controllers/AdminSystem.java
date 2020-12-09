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
            List<UUID> emptyEvents = tcs.getEM().getEmptyEvents();

            switch (choice){
                case "0":
                    presenter.printLoggedOut();
                    break label;
                case "1":
                    while (true) {

                        while (true) {
                            presenter.printAskUsername1();
                            String username1 = scanner.nextLine();
                            if (tcs.getUM().isRegistered(username1)) {
                                break;
                            } else {
                                presenter.printUserDoesNotExist();
                            }
                        }
                        while (true) {
                            presenter.printAskUsername2();
                            String username2 = scanner.nextLine();
                            if (tcs.getUM().isRegistered(username2)) {
                                break;
                            } else {
                                presenter.printUserDoesNotExist();
                            }
                        }

                        presenter.confirmChatDeletion();
                        String confirmation = scanner.nextLine();

                        if (confirmation == "yes"){

                            break;
                        }
                    }

                    break;
                case "2":
                    presenter.printDeleteEventMenu();

            }
        }
    }
}
