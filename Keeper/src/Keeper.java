import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Keeper {

	@JsonProperty("user")
	private String user;
	@JsonProperty("trackerArray")
	public Tracker[] trackerArray;

	public Keeper() {
	}

	public Keeper(String user) {
		this.user = user;
		this.trackerArray = new Tracker[Tracker.TrackerType.values().length];
	}
	
	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[Keeper[");
		stringBuilder.append("User:");
		stringBuilder.append(this.user);
		stringBuilder.append("]]");
		return stringBuilder.toString();
	}
	
	public String getUser(){
		return this.user;
	}

	public void addTracker(Tracker tracker) {
		int index = tracker.getTrackerType().ordinal();
		if (this.trackerArray[index] == null) {
			this.trackerArray[index] = tracker;
		}
	}

	/* WARNING - Only use if you know what you are overriding */
	public void overrideAddTracker(Tracker tracker) {
		int index = tracker.getTrackerType().ordinal();
		this.trackerArray[index] = tracker;
	}

	public Tracker getTracker(String trackerTypeName) {
		try {
			return this.trackerArray[Tracker.TrackerType.valueOf(
					trackerTypeName).ordinal()];
		} catch (IllegalArgumentException iae) {
			System.out
					.println("No tracker with that type exists. Use name of a valid TrackerType.");
			return null;
		}
	}

	public Map<Tracker.TrackerType, Integer> getTrackerValueMap() {
		Map<Tracker.TrackerType, Integer> trackerValueMap = new HashMap<Tracker.TrackerType, Integer>();
		Tracker[] trackerArray = this.getNonNullTrackers();
		for (Tracker tracker : trackerArray) {
			if (tracker != null) {
				trackerValueMap.put(tracker.getTrackerType(), new Integer(
						tracker.getEventList().size()));
			}
		}
		return trackerValueMap;

	}

	public Tracker[] getNonNullTrackers() {
		ArrayList<Tracker> trackerList = new ArrayList<Tracker>();
		for (Tracker tracker : this.trackerArray) {
			if (tracker != null) {
				trackerList.add(tracker);
			}
		}
		Tracker[] trackerArray = new Tracker[trackerList.size()];
		return trackerList.toArray(trackerArray);
	}

}
