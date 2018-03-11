package pe.com.boox.cargo.core;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.apache.commons.validator.GenericValidator;

public class StringUtil {
	private static String zeros = "0000000000";
	private static int codMoneda=1;
	

	public static boolean isEmpty(String obj) {
		return StringUtils.isEmpty(obj);
	}

	public static String blankIfNull(String string) {
		return StringUtils.defaultString(string, "");
	}

	public static String nullIfBlankOrNull(String string) {
		return GenericValidator.isBlankOrNull(string) ? null : string;
	}

	public static String toFixedString(int value, int length) {
		String val = Integer.toString(value);
		int diff = length - val.length();
		if (diff > 0)
			return (zeros.substring(10 - diff) + val);
		else
			return val;
	}

	public static String removeHTMLTags(String html) {
		return html.replaceAll("<(.*?)>", " ").replaceAll("\\s+", " ");
	}

	public static int convertToInt(String str) {
		return NumberUtils.createInteger(str);
	}

	public static long convertToLong(String str) {
		return NumberUtils.createLong(str);
	}

	public static Double convertToDouble(String str, double defValue) {
		return NumberUtils.createFloat(str).doubleValue();
	}

	/**
	 * generates an alphanumeric string based on specified length.
	 * 
	 * @param length
	 *            # of characters to generate
	 * @return random string
	 */
	public static String generateRandomString(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	/**
	 * Combines an array of string into one string using the specified
	 * separator.
	 * 
	 * @param separator
	 * @param input
	 * @return
	 */
	public static final String explode(char separator, String[] input) {
		if (input == null)
			return null;
		int count = 0;
		StringBuffer out = new StringBuffer();
		for (String word : input) {
			if (count++ > 0)
				out.append(separator);
			out.append(word);
		}
		return out.toString();
	}

	public static boolean containsAnyListElements(String base,
			ArrayList<String> stringList) {
		for (String string : stringList) {
			if (base.indexOf(string) != -1) {
				return true;
			}
		}
		return false;
	}

	public static String capitalizeString(String string) {
		return StringUtils.capitalize(string);
	}

	/**
	 * Metodo que rellena una cadena con valores por la izquierda
	 */
	public static String leftPad(final String input, final int tamano,
			final String patron) {
		return StringUtils.leftPad(input, tamano, patron);
	}

	/**
	 * Metodo que completa una cadena con zeros a la izquierda
	 */
	public static String zeroLeftFiller(final String cadena, final int longitud) {
		return leftPad(cadena, longitud, "0");
	}

	/**
	 * Metodo que rellena una cadena con valores por la derecha
	 */
	public static String rightPad(final String input, final int tamano,
			final String patron) {
		return StringUtils.rightPad(input, tamano, patron);
	}

	/**
	 * Metodo que completa una cadena con zeros a la derecha
	 */
	public static String zeroRightFiller(final String cadena, final int longitud) {
		return rightPad(cadena, longitud, "0");
	}

	/**
	 * Rellena una String con espacios a la derecha hasta alcanzar el largo
	 * requerido
	 * 
	 * @author jgonzaleza
	 * @param toFill
	 *            String a llenar
	 * @param length
	 *            Largo requerido
	 * @return <code>String</code>
	 * @throws java.lang.NullPointerException
	 */
	public static String spaceRightFiller(String toFill, int length) {
		return StringUtils.rightPad(toFill, length);
	}

	public static String formatNumber(String number) {

		Double num = Double.parseDouble(number);

		DecimalFormatSymbols sym = new DecimalFormatSymbols();
		sym.setDecimalSeparator('.');
		sym.setGroupingSeparator(',');

		DecimalFormat formateador = new DecimalFormat(
				"###,###,###,###,###,###,##0.00;-###,###,###,###,###,###,##0.00");
		formateador.setDecimalFormatSymbols(sym);

		return formateador.format(num);
	}
	
	public static String formatNumber2(String number) {

		Double num = Double.parseDouble(number);

		DecimalFormatSymbols sym = new DecimalFormatSymbols();
		sym.setDecimalSeparator('.');
		sym.setGroupingSeparator(',');

		DecimalFormat formateador = new DecimalFormat(
				"###,###,###,###,###,###,##0;-###,###,###,###,###,###,##0");
		formateador.setDecimalFormatSymbols(sym);

		return formateador.format(num);
	}
	
	public static String formatNumberThreeDigits(String number) {

		Double num = Double.parseDouble(number);

		DecimalFormatSymbols sym = new DecimalFormatSymbols();
		sym.setDecimalSeparator('.');
		sym.setGroupingSeparator(',');

		DecimalFormat formateador = new DecimalFormat(
				"###,###,###,###,###,###,##0.000;-###,###,###,###,###,###,##0.000");
		formateador.setDecimalFormatSymbols(sym);

		return formateador.format(num);
	}

	private static int charCount(String string, String c) {
		return StringUtils.countMatches(string, c);
	}

	public static boolean verifyStringAsNumber(String number) {
		if (GenericValidator.isBlankOrNull(number)) {
			return false;
		}
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(number);

		if (charCount(number, "-") > 1 || charCount(number, ".") > 1
				|| matcher.find()) {
			return false;
		}

		String raw = "";
		boolean hasNegSymbol = false;
		if (charCount(number, "-") > 0) {
			hasNegSymbol = true;
			if (number.charAt(0) != '-') {
				return false;
			}

			raw = StringUtils.replaceOnce(
					(GenericValidator.isBlankOrNull(raw) ? number : raw), "-",
					"");
		}
		if (charCount(number, ".") > 0) {
			if (!hasNegSymbol) {
				if (number.charAt(0) == '.') {
					return false;
				}
			} else {
				if (number.charAt(1) == '.') {
					return false;
				}
			}

			raw = StringUtils.replaceOnce(
					(GenericValidator.isBlankOrNull(raw) ? number : raw),
					"\\.", "");
		}

		if (!GenericValidator.isBlankOrNull(raw)) {
			if (raw.matches("[0-9]+")) {
				return true;
			}
		}

		if (number.matches("[0-9]+")) {
			return true;
		}

		return false;
	}

	@SuppressWarnings("rawtypes")
	public static List splitToList(String source, String delimiter) {
		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(delimiter)) {
			List list = new ArrayList();
			return list;
		}

		StrTokenizer token = new StrTokenizer(source);
		token.setDelimiterString(delimiter);
		token.setIgnoreEmptyTokens(false);
		return token.getTokenList();
	}


	public static String formatString(String input) {
		// Cadena de caracteres original a sustituir.
		String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
		// Cadena de caracteres ASCII que reemplazarán los originales.
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			// Reemplazamos los caracteres especiales.
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}// for i
		return output;

	}

	public static String lastModifieldFile(long tiempo) {

		Date date = new Date(tiempo);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = 0, month = 0, day = 0, hora = 0, min = 0, sec = 0;
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hora = calendar.get(Calendar.HOUR_OF_DAY);
		min = calendar.get(Calendar.MINUTE);
		sec = calendar.get(Calendar.SECOND);
		String formatLastModifield = "%02d%02d%d%02d%02d%02d";
		String lastModifield = String.format(formatLastModifield, day,
				month + 1, year, hora, min, sec);
		return lastModifield;

	}
	
	public static String formatCardNumerExternal(String cardNumber) {
		String result = "";
		if (!GenericValidator.isBlankOrNull(cardNumber) && cardNumber.length() > 4) {
			result = cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
			result = "****" + result;
		}
		return result;
	}
	
//	public static String formatCardSixFour(String cardNumber) {
//		String result = "";
//		String card = cardNumber.trim().replaceAll("\\s+", "");
//		if (!GenericValidator.isBlankOrNull(card) && card.length() >= 10) {
//			String masked = "";
//			for (int i = 0; i < card.length() - 10; i++) {
//				masked += "*";
//			}
//			String first = card.substring(0, 6);
//			String last = card.substring(card.length() - 4, card.length());
//			result = first + masked + last;
//			return result;
//		}
//		return cardNumber;
//	}
//	
	
	public static String formatCardSixFour(String cardNumber) {
		String result = "";
		String card = cardNumber.trim().replaceAll("\\s+", "");
		if (!GenericValidator.isBlankOrNull(card) && card.length() > 10) {
		String masked = "";
		for (int i = 0; i < (card.length() == 14 || card.length() == 15 ||
		card.length() == 16 ? 6 : card.length() - 10); i++) {
		masked += "*";
		}
		String first = card.substring(0, 6);
		int inicio;
		if(card.length() == 14){
		inicio = card.length() - 2;
		}else if(card.length() == 15){
		inicio = card.length() - 3;
		}else {
		inicio = card.length() - 4;
		}
		String last = card.substring(inicio, card.length());
		result = first + masked + last;
		return result;
		}
		return cardNumber;
		}
	
	
	
	public static String getCurrency(String moneda){
		int mon=Integer.parseInt(moneda);
		if (codMoneda==mon){
			return "604";
		} else {
			return "840";
		}
	}
	
	public static String capitalizeString2(String string){
		if(string != null){
			if(string.length() == 1){
				return string.toUpperCase();
			}
			return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
		}
		return null;
	}
	
	
	public static String getDescripcionMes(String mes){
		String descripcionMes= null;
		int m=0;
		try{
			 m= Integer.parseInt(mes);
			 switch (m) {
		        case 1:  descripcionMes = "Ene";
		                 break;
		        case 2:  descripcionMes = "Feb";
		                 break;
		        case 3:  descripcionMes = "Mar";
		                 break;
		        case 4:  descripcionMes = "Abr";
		                 break;
		        case 5:  descripcionMes = "May";
		                 break;
		        case 6:  descripcionMes = "Jun";
		                 break;
		        case 7:  descripcionMes = "Jul";
		                 break;
		        case 8:  descripcionMes = "Ago";
		                 break;
		        case 9:  descripcionMes = "Sep";
		                 break;
		        case 10: descripcionMes = "Oct";
		                 break;
		        case 11: descripcionMes = "Nov";
		                 break;
		        case 12: descripcionMes = "Dic";
		                 break;
		        default: descripcionMes =null;
		                 break;
		    }
		}catch(Exception e){
			m=0;
			descripcionMes=null;
		}
	
		
		return descripcionMes;
	}
}
