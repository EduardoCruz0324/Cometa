package com.invoice.commons.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.invoice.api.dto.DtoInvoiceList;
import com.invoice.api.entity.Invoice;

@Service
public class MapperInvoice {
	
	public List<DtoInvoiceList> toDtoList(List<Invoice> invoices) {
		List<DtoInvoiceList> dtoInvoices = new ArrayList<>();
		
		for (Invoice invoice : invoices) {
			
			DtoInvoiceList dtoInvoice = new DtoInvoiceList(
		            invoice.getInvoice_id(),
		            invoice.getUser_id(),
		            invoice.getCreated_at(),
		            invoice.getSubtotal(),
		            invoice.getTaxes(),
		            invoice.getTotal()
		        );
			dtoInvoices.add(dtoInvoice);
		}
         
         return dtoInvoices;
    }
}
