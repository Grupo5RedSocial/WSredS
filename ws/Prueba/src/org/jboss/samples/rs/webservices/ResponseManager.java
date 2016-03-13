package org.jboss.samples.rs.webservices;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.ws.util.UtilCryptography;

public class ResponseManager {
	public static final String cookieName = "autenticar";
	private static final Integer tiempoSession = 500000000;
	private static final Integer cantCaracteres=15;

	public static Response ResponseHeader(String objeto) {
		return ResponseHeader.addHeader(objeto, generarToken());
	}

	public static Response ResponseHeader(String objeto, String token) {
		return ResponseHeader.addHeader(objeto, token);
	}
	
	public Response ResponseHeader(String objeto, String origin,
			String methods, String headers) {
		return ResponseHeader.addHeader(objeto, origin, methods, headers);
	}

	public static String generarToken() {
		String token = null;
		String palabra;
		try {
			palabra = generaPalabra() + new Date().getTime();
			token = UtilCryptography.encriptar(palabra);
		} catch (Exception e) {
			System.out.println(e);
		}
		return token;
	}

	private static String generaPalabra() {
		return RandomStringUtils.randomAlphanumeric(cantCaracteres);
	}

	public static String validaToken(String token) {
		try {
			String valor = UtilCryptography.desencriptar(token);
			Long tiempo = Long.parseLong(valor.substring(cantCaracteres, valor.length()));
			Date fechaLogin = new Date();
			fechaLogin.setTime(tiempo);
			/*if (!validaHoraSesion(fechaLogin)) {
				return "Sesion Caducada";
			}*/
		} catch (Exception e) {
			return "No tiene permisos de acceso";
		}
		return "OK";
	}

	private static boolean validaHoraSesion(Date fecha) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(fecha);
		long milis1 = cal1.getTimeInMillis();
		long milis2 = cal2.getTimeInMillis();
		long diff = milis2 - milis1;
		long diffMinutes = diff / (60 * 1000);
		if (tiempoSession < diffMinutes) {
			return false;
		}
		return true;
	}

}
