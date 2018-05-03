package com.edgardjr.cursosb.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class BoletoService {
	
	public Date gerarDataVencimento(Date createdDate) {
		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(createdDate);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		
		return calendar.getTime();
	}
}
