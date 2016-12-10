public class TrackerAnalyzer {

	private Keeper keeper;

	public TrackerAnalyzer(Keeper keeper) {
		this.keeper = keeper;
	}

	/*
	 * Example Output:
	 * 
	 * C=3 CPP=0 JAVA=10
	 */
	public String getEventCountPerTracker() {
		// TODO - probably get rid of this in the future.We already have the nonnullarray
		StringBuilder stringBuilder = new StringBuilder();
		Tracker[] trackerArray = this.keeper.getNonNullTrackers();
		for (Tracker tracker : trackerArray) {
			stringBuilder.append(tracker.getTrackerType().name());
			stringBuilder.append("=");
			stringBuilder.append(tracker.getEventList().size());
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	public Tracker.TrackerType getMostPopularTracker() {
		Tracker[] trackerArray = this.keeper.getNonNullTrackers();
		Tracker.TrackerType popular = null;
		int trackerEventInstances = 0;
		for (Tracker tracker : trackerArray) {
			if (popular == null) {
				popular = tracker.getTrackerType();
				trackerEventInstances = tracker.getEventList().size();
			}

			if (trackerEventInstances < tracker.getEventList().size()) {
				popular = tracker.getTrackerType();
				trackerEventInstances = tracker.getEventList().size();
			}

		}
		return popular;
	}

}

/*
 * Notes: display based on: days, months, year, total hours across activity
 * types
 */
