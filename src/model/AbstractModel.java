package model;

import java.util.UUID;

import javax.persistence.Column;

public class AbstractModel {
	private String uuid;

	@Override
	public int hashCode() {
		return getUuid().hashCode();
	}

	@Column(name = "uuid", nullable = false, length = 36, unique = true)
	public String getUuid() {
		if (uuid == null) {
			uuid = UUID.randomUUID().toString();
		}
		return uuid;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractModel)) {
			return false;
		}
		return getUuid().equals(((AbstractModel) obj).getUuid());
	}

}
