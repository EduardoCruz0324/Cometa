package com.invoice.commons.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.invoice.exception.ApiException;

@Component
public class JwtDecoder {

	public boolean isAdmin() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated()) {
				return authentication.getAuthorities()
						.stream()
						.anyMatch(authority -> "ADMIN".equals(authority.getAuthority()));
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Integer getUserId() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Object details = authentication.getDetails();

			if (details instanceof Number number) {
				return number.intValue();
			}

			throw new ApiException(HttpStatus.PRECONDITION_FAILED, "El usuario es inválido");
		} catch (ApiException e) {
			throw e;
		} catch (Exception e) {
			throw new ApiException(HttpStatus.PRECONDITION_FAILED, "El usuario es inválido");
		}
	}
}
