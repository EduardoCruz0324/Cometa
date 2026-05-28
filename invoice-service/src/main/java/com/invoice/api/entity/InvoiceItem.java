package com.invoice.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer invoice_item_id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false)
	private Invoice invoice;

	private String gtin;

	private Integer quantity;

	private Double unit_price;

	private Double subtotal;

	private Double taxes;

	private Double total;

	private Integer status;

	public InvoiceItem() {
	}

	public Integer getInvoice_item_id() { return invoice_item_id; }
	public void setInvoice_item_id(Integer invoice_item_id) { this.invoice_item_id = invoice_item_id; }

	public Invoice getInvoice() { return invoice; }
	public void setInvoice(Invoice invoice) { this.invoice = invoice; }

	public String getGtin() { return gtin; }
	public void setGtin(String gtin) { this.gtin = gtin; }

	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }

	public Double getUnit_price() { return unit_price; }
	public void setUnit_price(Double unit_price) { this.unit_price = unit_price; }

	public Double getSubtotal() { return subtotal; }
	public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

	public Double getTaxes() { return taxes; }
	public void setTaxes(Double taxes) { this.taxes = taxes; }

	public Double getTotal() { return total; }
	public void setTotal(Double total) { this.total = total; }

	public Integer getStatus() { return status; }
	public void setStatus(Integer status) { this.status = status; }
}
