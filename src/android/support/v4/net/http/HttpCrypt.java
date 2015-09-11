package android.support.v4.net.http;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.support.v4.lang.Strings;

public class HttpCrypt {
	private static final String AES = Strings.fString(Strings.UPPA,
			Strings.UPPE, Strings.UPPS);

	public static String decrypt(final String plaintext) {
		return decrypt(plaintext, "57238004e784498bbc2f8bf984565090");
	}

	public static String encrypt(final String plaintext) {
		return encrypt(plaintext, "57238004e784498bbc2f8bf984565090");
	}

	public static String decrypt(final String plaintext, final String key) {
		String result = plaintext;
		try {
			final SecretKeySpec secretKeySpec = new SecretKeySpec(
					hexStringToByteArray(key), AES);
			final Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec,
					cipher.getParameters());
			result = new String(cipher.doFinal(hexStringToByteArray(plaintext)));
		} catch (final NoSuchAlgorithmException exception) {
		} catch (final NoSuchPaddingException exception) {
		} catch (final IllegalBlockSizeException exception) {
		} catch (final BadPaddingException exception) {
		} catch (final InvalidKeyException exception) {
		} catch (final InvalidAlgorithmParameterException exception) {
		}
		return result;
	}

	public static String encrypt(final String plaintext, final String key) {
		String result = plaintext;
		try {
			final SecretKeySpec secretKeySpec = new SecretKeySpec(
					hexStringToByteArray(key), AES);
			final Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,
					cipher.getParameters());
			result = byteArrayToHexString(cipher.doFinal(plaintext.getBytes()));
		} catch (final NoSuchAlgorithmException exception) {
		} catch (final NoSuchPaddingException exception) {
		} catch (final IllegalBlockSizeException exception) {
		} catch (final BadPaddingException exception) {
		} catch (final InvalidKeyException exception) {
		} catch (final InvalidAlgorithmParameterException exception) {
		}
		return result;
	}

	public static String byteArrayToHexString(final byte... bytes) {
		String result = new String(bytes);
		try {
			final StringBuilder stringBuilder = new StringBuilder(
					bytes.length * 2);
			for (final byte b : bytes) {
				final int calint = b & 0xff;
				if (calint < 16) {
					stringBuilder.append('0');
				}
				stringBuilder.append(Integer.toHexString(calint));
			}
			result = stringBuilder.toString().toUpperCase(Locale.US);
		} catch (final Exception exception) {
			Strings.exceptionToJSONObject(exception);
		}
		return result;
	}

	public static byte[] hexStringToByteArray(final String string) {
		byte[] result = string.getBytes();
		try {
			final byte[] bytes = new byte[string.length() / 2];
			for (int i = 0; i < bytes.length; i++) {
				final int index = i * 2;
				final int calint = Integer.parseInt(
						string.substring(index, index + 2), 16);
				bytes[i] = (byte) calint;
			}
			result = bytes;
		} catch (final NumberFormatException exception) {
		} catch (final Exception exception) {
			Strings.exceptionToJSONObject(exception);
		}
		return result;
	}
}
