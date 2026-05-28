package com.invoice.api.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer invoice_id;

	private Integer user_id;

	private String created_at;

	private Double subtotal;

	private Double taxes;

	private Double total;

	private Integer status;

	@OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<InvoiceItem> items;

	public Invoice() {
	}

	public Integer getInvoice_id() { return invoice_id; }
	public void setInvoice_id(Integer invoice_id) { this.invoice_id = invoice_id; }

	public Integer getUser_id() { return user_id; }
	public void setUser_id(Integer user_id) { this.user_id = user_id; }

	public String getCreated_at() { return created_at; }
	public void setCreated_at(String created_at) { this.created_at = created_at; }

	public Double getSubtotal() { return subtotal; }
	public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

	public Double getTaxes() { return taxes; }
	public void setTaxes(Double taxes) { this.taxes = taxes; }

	public Double getTotal() { return total; }
	public void setTotal(Double total) { this.total = total; }

	public Integer getStatus() { return status; }
	public void setStatus(Integer status) { this.status = status; }

	public List<InvoiceItem> getItems() { return items; }
	public void setItems(List<InvoiceItem> items) { this.items = items; }
}
