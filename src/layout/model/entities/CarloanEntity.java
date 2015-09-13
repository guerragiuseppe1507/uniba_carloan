package layout.model.entities;

import javafx.beans.property.StringProperty;

public interface CarloanEntity {
	public StringProperty getProperty(String propertyName);
	public int getId();
}
