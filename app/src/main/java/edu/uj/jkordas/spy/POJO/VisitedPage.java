package edu.uj.jkordas.spy.POJO;

public class VisitedPage {
	private long id;
	private String title;
	private String url;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public VisitedPage(String _title, String _url) {
		this.title = _title;
		this.url = _url;
	}

	@Override
	public String toString() {
		return this.title + ": " + this.url;
	}
}
