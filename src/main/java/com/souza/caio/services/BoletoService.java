package com.souza.caio.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.souza.caio.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagBoleto, Date instante) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(instante);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		pagBoleto.setDataVencimento(calendar.getTime());
	}
}
