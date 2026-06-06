package com.invoice.api.dto;

public class DtoInvoiceList {

	private Integer id;
	private Integer user_id;
	private String created_at;
	private Double subtotal;
	private Double taxes;
	private Double total;

	public DtoInvoiceList() {
	}

	public DtoInvoiceList(Integer id, Integer user_id, String created_at, Double subtotal, Double taxes, Double total) {
		this.id = id;
		this.user_id = user_id;
		this.created_at = created_at;
		this.subtotal = subtotal;
		this.taxes = taxes;
		this.total = total;
	}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

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
}
