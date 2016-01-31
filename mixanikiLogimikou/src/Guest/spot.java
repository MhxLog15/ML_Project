package Guest;

public class spot {
	private String title = "", descr = "";
	private int id = -1, x = -1, y = -1;

	public void SetId(int id) {
		this.id = id;
	}

	public int GetId() {
		return id;
	}

	public void SetTitle(String title) {
		this.title = title;
	}

	public String GetTitle() {
		return title;
	}

	public void SetDescr(String desc) {
		this.descr = desc;
	}

	public String GetDescr() {
		return descr;
	}

	public void SetX(int x) {
		this.x = x;
	}

	public void SetY(int y) {
		this.y = y;
	}

	public int GetX() {
		return x;
	}

	public int GetY() {
		return y;
	}

	public void Delete() {
		id = -1;
		title = "";
		descr = "";
		x = -1;
		y = -1;
	}
}
