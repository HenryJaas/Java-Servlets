package henry.java.server;

public class User {
	private String Id;
	private String firstName;
	private String lastName;
	

	public User() {
	}

	public User(String id, String firstName, String lastName) {
		super();
		this.Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		this.Id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}