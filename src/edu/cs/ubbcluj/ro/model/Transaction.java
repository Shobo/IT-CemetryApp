package edu.cs.ubbcluj.ro.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the transactions database table.
 * 
 */
@Entity
@Table(name="transactions")
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String after;

	private String before;

	@Column(name="document_number")
	private BigInteger documentNumber;

	@Column(name="modification_details")
	private String modificationDetails;

	private String table;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Transaction() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAfter() {
		return this.after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	public String getBefore() {
		return this.before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public BigInteger getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(BigInteger documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getModificationDetails() {
		return this.modificationDetails;
	}

	public void setModificationDetails(String modificationDetails) {
		this.modificationDetails = modificationDetails;
	}

	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}