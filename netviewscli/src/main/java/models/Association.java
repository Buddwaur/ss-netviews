package models;

public class Association {
	private String source;

	private String target;

	private String operations[];

	public Association(String source, String target, String operations[]) {
		setSource(source);
		setTarget(target);
		setOperations(operations);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		if (source == null || source.length() == 0) {
			throw new IllegalArgumentException("Source cannot be empty.");
		}
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		if (target == null || target.length() == 0) {
			throw new IllegalArgumentException("Target cannot be empty.");
		}
		this.target = target;
	}

	public String[] getOperations() {
		return operations;
	}

	public void setOperations(String operations[]) {
		this.operations = operations;
	}
	
	public String operationsString() {
		StringBuilder ops = new StringBuilder();
		
		for (int i = 0; i < operations.length; i++) {
			ops.append(operations[i]);
			
			if (i != operations.length - 1) {
				ops.append(" ");
			}
		}
		
		return ops.toString();
	}
}
