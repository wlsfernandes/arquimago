package br.com.furnas.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatarData {
	/**
	 * Converte uma String para um objeto Date. Caso a String seja vazia ou nula, 
	 * retorna null - para facilitar em casos onde formulários podem ter campos
	 * de datas vazios.
	 * @param data String no formato dd/MM/yyyy a ser formatada
	 * @return Date Objeto Date ou null caso receba uma String vazia ou nula
	 * @throws Exception Caso a String esteja no formato errado
	 */
	public static Date formataData(String data) throws Exception { 
		if (data == null || data.equals(""))
			return null;
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            date = (java.util.Date)formatter.parse(data);
        } catch (ParseException e) {            
            throw e;
        }
        return date;
	}
	
	public static String formataData(Date data) { 
		if (data == null || data.equals(""))
			return null;
		String date = null;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		date = formatter.format(data);
	
		return date;
	}
}
