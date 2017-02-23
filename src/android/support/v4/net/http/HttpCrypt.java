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

import android.n.NString;
import android.support.v4.lang.Strings;

public class HttpCrypt {
	private static final String AES = NString.add(Strings.UPPA, Strings.UPPE, Strings.UPPS);

	public static final String decrypt(final String plaintext) {
		return decrypt(plaintext, NString.add(Strings.FIVE, Strings.SEVEN, Strings.TWO, Strings.THREE, Strings.EIGHT,
				Strings.ZERO, Strings.ZERO, Strings.FOUR, Strings.LOWE, Strings.SEVEN, Strings.EIGHT, Strings.FOUR,
				Strings.FOUR, Strings.NINE, Strings.EIGHT, Strings.LOWB, Strings.LOWB, Strings.LOWC, Strings.TWO,
				Strings.LOWF, Strings.EIGHT, Strings.LOWB, Strings.LOWF, Strings.NINE, Strings.EIGHT, Strings.FOUR,
				Strings.FIVE, Strings.SIX, Strings.FIVE, Strings.ZERO, Strings.NINE, Strings.ZERO));
	}

	public static final String encrypt(final String plaintext) {
		return encrypt(plaintext, NString.add(Strings.FIVE, Strings.SEVEN, Strings.TWO, Strings.THREE, Strings.EIGHT,
				Strings.ZERO, Strings.ZERO, Strings.FOUR, Strings.LOWE, Strings.SEVEN, Strings.EIGHT, Strings.FOUR,
				Strings.FOUR, Strings.NINE, Strings.EIGHT, Strings.LOWB, Strings.LOWB, Strings.LOWC, Strings.TWO,
				Strings.LOWF, Strings.EIGHT, Strings.LOWB, Strings.LOWF, Strings.NINE, Strings.EIGHT, Strings.FOUR,
				Strings.FIVE, Strings.SIX, Strings.FIVE, Strings.ZERO, Strings.NINE, Strings.ZERO));
	}

	public static final String decrypt(final String plaintext, final String key) {
		String result = plaintext;
		try {
			final SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByteArray(key), AES);
			final Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, cipher.getParameters());
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

	public static final String encrypt(final String plaintext, final String key) {
		String result = plaintext;
		try {
			final SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByteArray(key), AES);
			final Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, cipher.getParameters());
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

	public static final String byteArrayToHexString(final byte... bytes) {
		String result = new String(bytes);
		try {
			final StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
			for (final byte b : bytes) {
				final int calint = b & 0xff;
				if (calint < 16) {
					stringBuilder.append('0');
				}
				stringBuilder.append(Integer.toHexString(calint));
			}
			result = stringBuilder.toString().toUpperCase(Locale.US);
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}

	public static final byte[] hexStringToByteArray(final String string) {
		byte[] result = string.getBytes();
		try {
			final byte[] bytes = new byte[string.length() / 2];
			for (int i = 0; i < bytes.length; i++) {
				final int index = i * 2;
				final int calint = Integer.parseInt(string.substring(index, index + 2), 16);
				bytes[i] = (byte) calint;
			}
			result = bytes;
		} catch (final NumberFormatException exception) {
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}
}
