package pe.com.boox.cargo.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Random;

public class CodeGeneratorUtil {
	/** Generador de números aleatorios. */
	protected static Random generador = new Random(System.currentTimeMillis());
	/** Valor de host por defecto. */
	private static final String DEFAULT_HOST = "999";
	/** Inicio de la secuencia. */
	private static final int SEQUENCE_START = 1000;
	/** Fin de la secuencia. */
	private static final int SEQUENCE_END = 9999;
	/** Largo máximo del identificador generado. */
	private static final int MAX_SIZE = 20;
	/** Número de secuencia. */
	private static int sequence = SEQUENCE_START;
	/** Número de secuencia. */
	private static int reference = SEQUENCE_START;
	/**
	 * Número máximo a generar aleatoriamente para la referencia de cheque
	 * positivo
	 */
	private static final int REFERENCE_RANDOM_LIMIT = 1000;
	/**
	 * Largo de número a generar aleatoreamente para la referencia de cheque
	 * positivo
	 */
	private static final int REFERENCE_RANDOM_LENGTH = 3;

	/**
	 * Genera un número único de 20 carateres para el sistema.
	 * 
	 * @return <code>String</code> con el código.
	 */
	public static String generateCode() {
		String returnValue = null;
		String host = null;

		if (sequence >= SEQUENCE_END) {
			sequence = SEQUENCE_START;
		}

		try {
			host = DEFAULT_HOST
					.concat(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// Se asigan un valor por default
			host = DEFAULT_HOST;
		} catch (Exception e) {
			host = DEFAULT_HOST;
		}

		StringBuffer valor = new StringBuffer(Integer.toHexString(sequence));
		sequence++;

		valor.append(Long.toHexString(System.currentTimeMillis()));

		int size = host.length();
		valor.append(host.substring(size - 3, size));

		valor = valor.append(Long.toHexString(generador.nextLong()).substring(
				0, 3));

		if (valor.length() > MAX_SIZE) {
			returnValue = valor.substring(0, MAX_SIZE - 1);
		} else {
			returnValue = valor.toString();
		}

		return returnValue;
	}

	/**
	 * Genera un número único de tamaño específico de carateres para el sistema.
	 * 
	 * @param size
	 *            <code>int</code> tamaño del identificador a generar
	 * @return <code>String</code> con el código.
	 */
	public static String generateCode(int size) {
		String returnValue = null;
		int beginIndex = 0;
		int endIndex = 0;
		String host = null;

		if (sequence >= SEQUENCE_END) {
			sequence = SEQUENCE_START;
		}

		try {
			host = DEFAULT_HOST
					.concat(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// Se asigan un valor por default
			host = DEFAULT_HOST;
		} catch (Exception e) {
			host = DEFAULT_HOST;
		}

		StringBuffer valor = new StringBuffer(Integer.toHexString(sequence));
		sequence++;

		valor.append(Long.toHexString(System.currentTimeMillis()));

		int sizeHost = host.length();
		valor.append(host.substring(sizeHost - 3, sizeHost));

		valor = valor.append(Long.toHexString(generador.nextLong()).substring(
				0, 3));

		returnValue = valor.toString();
		if (size < returnValue.length()) {
			beginIndex = generador.nextInt(returnValue.length() - size);
			endIndex = beginIndex + size;
			returnValue = returnValue.substring(beginIndex, endIndex);
		}

		return returnValue;

	}

	/**
	 * Genera un número único de 10 carateres para ser usado como referencia.
	 * esta referencia tendrá un rango desde 0000000001 hasta 9999999999, no
	 * consecutivo. XXXJJJYYYY xxx = número random JJJ = número de días
	 * transcurridos en el año YYYY = secuencia
	 * 
	 * @return <code>String</code> con el código.
	 */
	public static String generateReference() {
		int random = generador.nextInt(REFERENCE_RANDOM_LIMIT);

		if (reference >= SEQUENCE_END) {
			reference = SEQUENCE_START;
		}

		StringBuffer code = new StringBuffer(StringUtil.zeroLeftFiller(
				String.valueOf(random), REFERENCE_RANDOM_LENGTH));
		code.append(StringUtil.zeroLeftFiller(String.valueOf(Calendar
				.getInstance().get(Calendar.DAY_OF_YEAR)),
				REFERENCE_RANDOM_LENGTH));
		code.append(String.valueOf(reference));
		reference++;

		return code.toString();
	}

	/**
	 * Constructor por defecto.
	 */
	private CodeGeneratorUtil() {
		super();
	}

	/**
	 * Genera un número único no consecutivo de N dígitos.
	 * 
	 * @return <code>String</code> con el código.
	 */
	public static synchronized String generateRandomNumber(int length) {

		// se inicializa el StringBuffer con el último dígito del año actual
		String code = null;
		int diff = 0;
		code = String.valueOf(System.currentTimeMillis());

		// obtener los N ultims digitos
		if (code.length() > length) {
			diff = code.length() - length;
			code = code.substring(diff - 1, code.length() - 1);
		}

		if (code.length() < length) {
			code = StringUtil.zeroRightFiller(code, length);
		}

		return code;
	}
}