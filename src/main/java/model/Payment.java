package model;
// Generated Feb 15, 2024, 12:11:37 PM by Hibernate Tools 5.2.13.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Payment generated by hbm2java
 */
@Entity
@Table(name = "payment", schema = "public")
public class Payment implements java.io.Serializable {

	private int paymentId;
	private Customer customer;
	private Rental rental;
	private Staff staff;
	private BigDecimal amount;
	private Date paymentDate;

	public Payment() {
	}

	public Payment(int paymentId, Customer customer, Rental rental, Staff staff, BigDecimal amount, Date paymentDate) {
		this.paymentId = paymentId;
		this.customer = customer;
		this.rental = rental;
		this.staff = staff;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}

	@Id

	@Column(name = "payment_id", unique = true, nullable = false)
	public int getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rental_id", nullable = false)
	public Rental getRental() {
		return this.rental;
	}

	public void setRental(Rental rental) {
		this.rental = rental;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staff_id", nullable = false)
	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	@Column(name = "amount", nullable = false, precision = 5)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "payment_date", nullable = false, length = 29)
	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}