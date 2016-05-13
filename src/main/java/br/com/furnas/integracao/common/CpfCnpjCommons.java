package br.com.furnas.integracao.common;

import java.util.ArrayList;
import java.util.List;
/**
 * Valida CPF ou CPNJ de acordo com o algoritmo de constru��o.
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 1.0
 */
public class CpfCnpjCommons {
	
	/**
	 * Verifica se o CPF � valido.
	 * @param item - string com o valor de cpf que sera testado
	 * @return Boolean
	 */
	public static Boolean isValidCPF(String item) {
		
		return isValidItem(item, ValidationType.CPF);
		
	}
	
	/**
	 * Verifica se o CNPJ � valido.
	 * @param item - string com o valor de cnpj que sera testado
	 * @return Boolean
	 */	
	public static Boolean isValidCNPJ(String item) {
		
		return isValidItem(item, ValidationType.CNPJ);
		
	}
	
	/**
	 * Obtem os multiplicadores necessarios para 
	 * multiplica��o por posicao dos primeiros 9 digitos do CPF, 
	 * obtendo o digito verificador.
	 * @return List<Integer>
	 */
	private static List<Integer> getMultipliersCPF() {
		
		List<Integer> l = new ArrayList<Integer>();
		
		for ( Integer a = 11; a >= 2; a-- ) l.add(a);

		return l;
	
	}
	
	/**
	 * Obtem os multiplicadores necessarios para 
	 * multiplica��o por posicao dos primeiros 14 digitos do CNPJ, 
	 * obtendo o digito verificador.
	 * @return List<Integer>
	 */
	private static List<Integer> getMultipliersCNPJ() {
		
		List<Integer> l = new ArrayList<Integer>();
		
		for ( Integer a = 6; a >= 2; a-- ) l.add(a);
		
		for ( Integer a = 9; a >= 7; a-- ) l.add(a);

		for ( Integer a = 6; a >= 2; a-- ) l.add(a);
		
		return l;
	
	}	

	/**
	 * Aplica o algoritmo de validaao dependendo do tipo do item passado.
	 * @param item - numero do cpf ou cnpj
	 * @param validationType - tipo de validacao que sera utilizada
	 * @return Boolean
	 */
	private static Boolean isValidItem(String item, String validationType) {
		
		String itemFirstDigits = null;
		
		Object[] multipliers = null;
		
		Integer numOfDigits = null;
		
		/***
		 * Verifico se o tipo sera CPF ou CNPJ isso define o numero total de
		 * digitos, os multiplicadores de cada um eo numero do item sem digito
		 * verificador
		 */
		if (ValidationType.CPF.equals(validationType)) {

			numOfDigits = 11;

			multipliers = getMultipliersCPF().toArray();

			itemFirstDigits = item.substring(0, 9);

		} else if (ValidationType.CNPJ.equals(validationType)) {

			numOfDigits = 14;

			multipliers = getMultipliersCNPJ().toArray();

			itemFirstDigits = item.substring(0, 12);

		} else {

			return false;

		}
		
		/** Caso o numero de digitos seja diferente do esperado ele retorna false */ 
		if ((item == null) || (item.length() != numOfDigits)) return false;

		Integer sumValueFirstDigitVerify = 0;
		
		/** Verificando executando a soma do primeiro digito verificador a partir do resultado dos primeiros digitos sem o verificador */ 
		for (int index = itemFirstDigits.length() - 1, currentDigit; index >= 0; index--) {

			currentDigit = Integer.parseInt(itemFirstDigits.substring(index,index + 1));

			sumValueFirstDigitVerify += currentDigit * (Integer) multipliers[multipliers.length - itemFirstDigits.length() + index];

		}
		
		/** Depois de somado divido o resultado por 11 */
		sumValueFirstDigitVerify = 11 - sumValueFirstDigitVerify % 11;
		
		/** regra para saber se o primeiro digito � zero */
		Integer firstDigitVerify = sumValueFirstDigitVerify > 9 ? 0 : sumValueFirstDigitVerify;
		
		/** Adiciono o primeiro digito verificador junto aos numeros iniciais do item */
		String itemFirstDigitsWithFirstDigitVerify = itemFirstDigits.concat(String.valueOf(firstDigitVerify));

		Integer sumValueSecondDigitVerify = 0;
		
		/** Verificando executando a soma do primeiro digito verificador a partir do resultado dos primeiros digitos com o primeiro digito verificador */
		for (int index = itemFirstDigitsWithFirstDigitVerify.length() - 1, currentDigit; index >= 0; index--) {

			currentDigit = Integer.parseInt(itemFirstDigitsWithFirstDigitVerify.substring(index, index + 1));

			sumValueSecondDigitVerify += currentDigit * (Integer) multipliers[multipliers.length - itemFirstDigitsWithFirstDigitVerify.length() + index];

		}
		
		/** Depois de somado divido o resultado por 11 */
		sumValueSecondDigitVerify = 11 - sumValueSecondDigitVerify % 11;
		
		/** regra para saber se o segundo digito � zero */
		Integer secondDigitVerify = sumValueSecondDigitVerify > 9 ? 0 : sumValueSecondDigitVerify;
		
		/** Verifica se o item com os primeiro numero mais os digitos verificadores forem iguais ao item inicial */ 
		return item.equals(itemFirstDigits.concat(
				String.valueOf(firstDigitVerify)).concat(
				String.valueOf(secondDigitVerify)));

	}
	
	/**
	 * Usada para representar internamente os 
	 * tipos de CPF e CNPJ parametrizados
	 * @author Diego Costa - diego.csilva@montreal.com.br
	 * @version 1.0
	 */
	final class ValidationType {
		
		public static final String CPF = "CPF";
		
		public static final String CNPJ = "CNPJ";
		
		private ValidationType() {}
		
	}
	
}