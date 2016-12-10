import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class KeeperCommandInterface {

	// This could all be refactored with an organic menu
	private static final int OPT_TRACKER = 1;
	private static final int OPT_EVENT = 2;
	private static final int OPT_LIST_TRACKERS = 3;
	private static final int OPT_CREATE_KEEPER = 4;
	private static final int OPT_SET_ACTIVE_KEEPER = 5;
	private static final int OPT_WHO_AM_I = 6;
	private static final int OPT_WRITE_TO_DISK = 7;
	private static final int OPT_LIST_TRACKER_TYPES = 8;
	private static final int OPT_EXIT = 9;

	private static final int OPT_TRACKER_ADD = 1;
	private static final int OPT_EVENT_ADD = 1;

	private static String KPR_PROMPT_MAIN_MENU = "Welcome to Keeper, What are we doing?";

	private static String KPR_PROMPT_TRACKER_NAME = "Tracker Name?";
	private static String KPR_PROMPT_EVENT_DETAILS = "Event Details...";

	private static String KPR_OPT_ADD_TRACKER = "" + OPT_TRACKER
			+ ". Manipulate Tracker";
	private static String KPR_OPT_ADD_EVENT = "" + OPT_EVENT
			+ ". Manipulate Event";

	private static String KPR_OPT_SYS_EXIT = "" + OPT_EXIT + ". Exit";

	private static String KPR_TRACKER_MAIN_MENU = "How are we manipulating your Trackers?";
	private static String KPR_EVENT_MAIN_MENU = "How are we manipulating your Events?";

	private static String KPR_TRACKER_ADD_TRACKER = "" + OPT_TRACKER_ADD
			+ ". Add a Tracker";
	private static String KPR_EVENT_ADD_EVENT = "" + OPT_EVENT_ADD
			+ ". Add an Event";

	private static String KPR_TRACKER_ADD_CONFIRM = "Adding a Tracker...";
	private static String KPR_EVENT_ADD_CONFIRM = "Adding an Event...";

	private static String FEEDBACK_TASK_COMPLETED = "Task completed.";
	private static String FEEDBACK_RETURN_TO_MAIN = "Returning to Main Menu.";

	private Scanner in;
	private boolean running;
	private Keeper keeper;
	private KeeperParser keeperParser; // Only available so kci can create
										// keepers/users

	public KeeperCommandInterface(KeeperParser parentController) {
		this.in = new Scanner(System.in);
		this.running = true;
		this.keeper = null;
		this.keeperParser = parentController;
	}

	public void setActiveKeeper(Keeper keeper) {
		this.keeper = keeper;
	}

	public Keeper getActiveKeeper() {
		return this.keeper;
	}

	// Refactor loose parts
	public void interact() {
		if (this.keeper == null) {
			this.putMessage("Active Keeper cannot be null. Please set active Keeeper and try again.");
		}
		while (running) {
			this.putMessage(KPR_PROMPT_MAIN_MENU);
			this.putMessage(KPR_OPT_ADD_TRACKER);
			this.putMessage(KPR_OPT_ADD_EVENT);
			this.putMessage("3. List Trackers");
			this.putMessage("4. Create Keeper");
			this.putMessage("5. Set Active User");
			this.putMessage("6. Who am I?");
			this.putMessage("7. Write to Disk");
			this.putMessage("8. List Tracker Types");
			this.putMessage(KPR_OPT_SYS_EXIT);
			int userChoice = Integer.parseInt(this.getMessage());
			switch (userChoice) {
			case OPT_TRACKER:
				this.handleMenuTracker();
				break;
			case OPT_EVENT:
				this.handleMenuEvent();
				break;
			case OPT_LIST_TRACKERS:
				this.handleMenuListTrackers();
				break;
			case OPT_CREATE_KEEPER:
				this.handleMenuCreateKeeper();
				break;
			case OPT_SET_ACTIVE_KEEPER:
				this.handleMenuSetActiveUser();
				this.complete();
				break;
			case OPT_WHO_AM_I:
				this.handleMenuGetActiveUser();
				break;
			case OPT_WRITE_TO_DISK:
				this.putMessage("Writing keeper data to disk...");
				this.keeperParser.writeKeepersToDisk();
				break;
			case OPT_LIST_TRACKER_TYPES:
				this.handleMenuListTrackerTypes();
				break;
			case OPT_EXIT:
				this.running = false;
				this.putMessage("GOOD BYE");
				break;
			default:
				System.out.println("Not A Valid Option.");
			}
		}
	}

	private void putMessage(String message) {
		System.out.println(message);
	}

	private String getMessage() {
		return this.in.nextLine();
	}

	private void handleMenuListTrackerTypes() {
		for (Tracker.TrackerType types : Tracker.TrackerType.values()) {
			this.putMessage(types.name());
		}
	}

	private void handleMenuGetActiveUser() {
		this.putMessage("Active User:");
		if (this.getActiveKeeper() != null) {
			this.putMessage(this.getActiveKeeper().toString());
		}
	}

	private void handleMenuSetActiveUser() {
		this.putMessage("Set Active Keeper as:");
		String user = this.getMessage();
		ArrayList<Keeper> keepers = this.keeperParser.getKeeperList();
		for (Keeper keeper : keepers) {
			if (keeper.getUser().equals(user)) {
				this.keeper = keeper;
				this.putMessage(user + " set as Active.");
			}
		}
	}

	private void handleMenuTracker() {
		this.putMessage(KPR_TRACKER_MAIN_MENU);
		this.putMessage(KPR_TRACKER_ADD_TRACKER);
		int choice = Integer.parseInt(this.getMessage());
		switch (choice) {
		case OPT_TRACKER_ADD:
			this.putMessage(KPR_TRACKER_ADD_CONFIRM);
			this.handleMenuTrackerAdd();
			break;
		default:
			this.putMessage("Not A Valid Choice.");
		}
	}

	private void handleMenuListTrackers() {
		this.putMessage("Listing Trackers...");
		for (Tracker tracker : this.keeper.getNonNullTrackers()) {
			this.putMessage(tracker.toString());
		}
		this.complete();
	}

	private void handleMenuCreateKeeper() {
		this.putMessage("What is the user name?");
		String user = this.getMessage();
		this.keeperParser.getKeeperList().add(new Keeper(user));
	}

	private void handleMenuEvent() {
		this.putMessage(KPR_EVENT_MAIN_MENU);
		this.putMessage(KPR_EVENT_ADD_EVENT);
		int choice = Integer.parseInt(this.getMessage());
		switch (choice) {
		case OPT_EVENT_ADD:
			this.putMessage(KPR_EVENT_ADD_CONFIRM);
			this.handleMenuEventAdd();
			break;
		default:
			this.putMessage("Not A Valid Choice.");
		}
	}

	// This menu should complete()
	private void handleMenuTrackerAdd() {
		this.putMessage(KPR_PROMPT_TRACKER_NAME);
		String tracker = this.getMessage();
		Tracker.TrackerType trackerType = Tracker.GetTrackerTypeByName(tracker);
		this.keeper.addTracker(new Tracker(trackerType));
		this.complete();
	}

	// This menu should complete()
	private void handleMenuEventAdd() {
		this.putMessage(KPR_PROMPT_EVENT_DETAILS);
		this.putMessage("What is the tracker type?");
		String tracker = this.getMessage();
		Tracker.TrackerType trackerType = Tracker.GetTrackerTypeByName(tracker);
		this.putMessage("What is the value of the event?");
		String eventValue = this.getMessage();
		EventInstance eventInstance = new EventInstance(trackerType,
				new Date(), Integer.parseInt(eventValue));
		Tracker memberTracker = this.keeper.getTracker(tracker);
		if (memberTracker == null) {
			memberTracker = new Tracker(trackerType);
			memberTracker.addEvent(eventInstance);
			this.keeper.addTracker(memberTracker);
		} else {
			memberTracker.addEvent(eventInstance);
		}
		this.complete();
	}

	private void complete() {
		this.putMessage(FEEDBACK_TASK_COMPLETED);
		this.putMessage(FEEDBACK_RETURN_TO_MAIN);
	}

}
