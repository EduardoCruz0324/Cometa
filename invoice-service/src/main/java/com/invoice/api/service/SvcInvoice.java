package com.invoice.api.service;

import java.util.List;

import com.invoice.api.dto.ApiResponse;
import com.invoice.api.dto.DtoInvoiceList;
import com.invoice.api.entity.Invoice;

public interface SvcInvoice {

	public List<DtoInvoiceList> findAll();
	public Invoice findById(Integer id);
	public ApiResponse create();
}
