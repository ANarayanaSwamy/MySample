package my.chat.app.data;

import java.util.ArrayList;
import java.util.List;

public class MessageCache {
	private static final String NEW_LINE = System.getProperty("line.separator");
	private static List<String> prevMessages = new ArrayList<String>();

	// Cache the messages that are received from Kafka
	public static void cache(String currMessage) {
		prevMessages.add(currMessage);
	}

	// Format and send to the UI for display
	public static String getAllCachedMessage() {
		StringBuilder sb = new StringBuilder();
		for (String msg : prevMessages) {
			sb.append(msg);
			sb.append(NEW_LINE);
		}
		return sb.toString();
	}
}
