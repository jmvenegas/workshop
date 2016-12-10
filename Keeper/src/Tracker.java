import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tracker {

	public enum TrackerType {
		C, CPP, JAVA, PYTHON, GO, BLENDER, UNITY, TWITCH
	}

	public static TrackerType GetTrackerTypeByName(String name) {
		return TrackerType.valueOf(name);
	}

	@JsonProperty("trackerType")
	private TrackerType trackerType;
	@JsonProperty("eventArray")
	private ArrayList<EventInstance> eventArray;

	public Tracker() {
	}

	public Tracker(TrackerType trackerType) {
		this.trackerType = trackerType;
		this.eventArray = new ArrayList<EventInstance>();
	}

	public TrackerType getTrackerType() {
		return this.trackerType;
	}

	public ArrayList<EventInstance> getEventList() {
		return this.eventArray;
	}

	public void addEvent(EventInstance eventInstance) {
		this.eventArray.add(eventInstance);
	}

	public int getTotalEventInstances() {
		return this.eventArray.size();
	}

	public int getEventValueTotal() {
		int total = 0;
		for (EventInstance event : this.eventArray) {
			total += event.getValue();
		}
		return total;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.trackerType);
		stringBuilder.append(this.eventArray);
		return stringBuilder.toString();
	}

	public boolean equals(Object other) {

		if (other == null) {
			return false;
		}
		if (!(other instanceof Tracker)) {
			return false;
		}
		Tracker otherTracker = (Tracker) other;
		return (otherTracker.trackerType == this.trackerType);

	}

}
