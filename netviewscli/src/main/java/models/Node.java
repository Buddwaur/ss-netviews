package models;

public class Node {
	private String name;
	
	private String type;
	
	private Properties properties;
	
	public Node(String name, String type, Properties prop) {
		setName(name);
		setType(type);
		setProperties(prop);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("Name cannot be empty.");
		}
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type == null || type .length() == 0) {
			throw new IllegalArgumentException("Type cannot be empty.");
		}
		this.type = type;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	
}
