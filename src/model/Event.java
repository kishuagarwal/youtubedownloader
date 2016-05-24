package model;
public class Event {

	String url_encoded_fmt_stream_map;
	String title;
	long length_seconds;

	Event(String url_encoded_fmt_stream_map, String title, long length_seconds) {
		this.url_encoded_fmt_stream_map = url_encoded_fmt_stream_map;
		this.title = title;
		this.length_seconds = length_seconds;
	}
}
