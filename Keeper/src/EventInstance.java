import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventInstance {

	@JsonProperty("trackerType")
	private Tracker.TrackerType trackerType;
	@JsonProperty("date")
	private Date date;
	@JsonProperty("value")
	private int value;

	public EventInstance() {
	}

	public EventInstance(Tracker.TrackerType trackerType, Date date, int value) {
		this.trackerType = trackerType;
		this.date = date;
		this.value = value;
	}

	public Tracker.TrackerType getTrackerType() {
		return this.trackerType;
	}

	public Date getDate() {
		return this.date;
	}

	public int getValue() {
		return this.value;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[EventInstance]");
		stringBuilder.append("[");
		stringBuilder.append(this.trackerType.name());
		stringBuilder.append("][");
		stringBuilder.append(this.date);
		stringBuilder.append("][");
		stringBuilder.append(this.value);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
