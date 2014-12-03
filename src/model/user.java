package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Model class for the user entity. 
 * @author Radu
 *
 */

@Entity
@Table(name = "Users")
public class User extends BaseEntity{
	private static final long serialVersionUID = 1L;


	private String username;
	private String password;

	
	public User(){
		super();
	}
	@Column(name = "username")
	public String getName() {
		return username;
	}

	public void setName(String name) {
		this.username = name;
	}
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	@Override
	public String toString() {
		return this.username + " " + this.password;
	}
}
