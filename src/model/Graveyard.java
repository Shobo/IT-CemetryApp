package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Radu
 *
 */
@Entity
@Table(name = "Graveyards")
public class Graveyard extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -103498975627165214L;

	private String name;
	private boolean deleted;

	public Graveyard() {
		super();
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "deleted")
	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
