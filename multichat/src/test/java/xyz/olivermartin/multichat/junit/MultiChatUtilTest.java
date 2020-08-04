package xyz.olivermartin.multichat.junit;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import xyz.olivermartin.multichat.common.MultiChatUtil;
import xyz.olivermartin.multichat.common.TranslateMode;

public class MultiChatUtilTest {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(MultiChatUtilTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
	}

	@Test
	public void shouldTranslateColourCodesCorrectly() {

		String rawMessage = "&r&aHello &kthere! &6&lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!";

		// ALL
		assertEquals("All codes should be translated appropriately",
				"�r�aHello �kthere! �6�lthis �ois �ma �nmessage! �r�x�a�b�c�d�e�fRGB �r�x�a�b�c�d�e�ftoo�r�x�a�b�c�d�e�f!",
				MultiChatUtil.translateColourCodes(rawMessage));

		// ALL #2
		assertEquals("All codes should be translated appropriately",
				"�r�aHello �kthere! �6�lthis �ois �ma �nmessage! �r�x�a�b�c�d�e�fRGB �r�x�a�b�c�d�e�ftoo�r�x�a�b�c�d�e�f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.ALL));

		// SIMPLE
		assertEquals("Simple codes should be translated appropriately",
				"�r�aHello �kthere! �6�lthis �ois �ma �nmessage! &#ABCDEFRGB &xAbCdEftoo&x�a�b�c�d�e�f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.SIMPLE));

		// SIMPLE COLOUR
		assertEquals("Simple colour codes should be translated appropriately",
				"&r�aHello &kthere! �6&lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x�a�b�c�d�e�f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.COLOUR_SIMPLE));

		// ALL COLOUR
		assertEquals("All colour codes should be translated appropriately",
				"�r�aHello &kthere! �6&lthis &ois &ma &nmessage! �r�x�a�b�c�d�e�fRGB �r�x�a�b�c�d�e�ftoo�r�x�a�b�c�d�e�f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.COLOUR_ALL));

		// FORMAT UNDERLINE
		assertEquals("Underline codes should be translated appropriately",
				"&r&aHello &kthere! &6&lthis &ois &ma �nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.FORMAT_UNDERLINE));

		// FORMAT ITALIC
		assertEquals("Italic codes should be translated appropriately",
				"&r&aHello &kthere! &6&lthis �ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.FORMAT_ITALIC));

		// FORMAT BOLD
		assertEquals("Bold codes should be translated appropriately",
				"&r&aHello &kthere! &6�lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.FORMAT_BOLD));

		// FORMAT STRIKE
		assertEquals("Strike codes should be translated appropriately",
				"&r&aHello &kthere! &6&lthis &ois �ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.FORMAT_STRIKE));

		// FORMAT OBFUSCATED
		assertEquals("Obfuscation codes should be translated appropriately",
				"&r&aHello �kthere! &6&lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.FORMAT_OBFUSCATED));

		// FORMAT RESET
		assertEquals("Reset codes should be translated appropriately",
				"�r&aHello &kthere! &6&lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.FORMAT_RESET));

		// FORMAT ALL
		assertEquals("All format codes should be translated appropriately",
				"�r&aHello �kthere! &6�lthis �ois �ma �nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.FORMAT_ALL));

		// X
		assertEquals("All X codes should be translated appropriately",
				"&r&aHello &kthere! &6&lthis &ois &ma &nmessage! &r�x&a&b&c&d&e&fRGB &r�x&a&b&c&d&e&ftoo&r�x&a&b&c&d&e&f!",
				MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.X));

	}

	@Test
	public void shouldNotChangeWithMultipleTranslations() {

		String rawMessage = "&r&aHello &kthere! &6&lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!";

		String translated1 = MultiChatUtil.translateColourCodes(rawMessage);

		String translated2 = MultiChatUtil.translateColourCodes(translated1);

		assertEquals("Resulting translations should be the same after multiple parses",
				translated1,
				translated2);

	}

	@Test
	public void shouldApproximateRGBColourCodesCorrectly() {

		String rawMessage = "&r&aHello &kthere! &6&lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!";

		String translated = MultiChatUtil.translateColourCodes(rawMessage);

		String approximated = MultiChatUtil.approximateRGBColourCodes(translated);

		assertEquals("Translated RGB colour codes should be approximated to nearest minecraft equivalent",
				"�r�aHello �kthere! �6�lthis �ois �ma �nmessage! �7RGB �7too�7!",
				approximated);

		String simpleTranslated = MultiChatUtil.translateColourCodes(rawMessage, TranslateMode.SIMPLE);

		String simpleApproximated = MultiChatUtil.approximateRGBColourCodes(simpleTranslated);

		assertEquals("Non translated RGB colour codes should NOT be approximated to nearest minecraft equivalent",
				"�r�aHello �kthere! �6�lthis �ois �ma �nmessage! &#ABCDEFRGB &xAbCdEftoo&x�a�b�c�d�e�f!",
				simpleApproximated);

		String jsonMessage = "{\"text\":\"hello world\", \"color\":\"#ABCDEF\"}";

		assertEquals("JSON RGB colour codes should be approximated to nearest simple equivalent",
				"{\"text\":\"hello world\", \"color\":\"gray\"}",
				MultiChatUtil.approximateRGBColourCodes(jsonMessage));

		String jsonMessage2 = "{\"text\":\"hello world\", \"color\":\"#aBcDeF\"}";

		assertEquals("JSON RGB colour codes should be approximated to nearest simple equivalent (test 2)",
				"{\"text\":\"hello world\", \"color\":\"gray\"}",
				MultiChatUtil.approximateRGBColourCodes(jsonMessage2));

		String jsonMessage3 = "{\"text\":\"hello world\", \"color\":\"#abcdef\"}";

		assertEquals("JSON RGB colour codes should be approximated to nearest simple equivalent (test 3)",
				"{\"text\":\"hello world\", \"color\":\"gray\"}",
				MultiChatUtil.approximateRGBColourCodes(jsonMessage3));

		String jsonMessage4 = "{\"text\":\"hello world\", \"color\":\"gray\"}";

		assertEquals("Non RGB JSON messages should not be appoximated by the method",
				jsonMessage4,
				MultiChatUtil.approximateRGBColourCodes(jsonMessage4));

	}

	@Test
	public void shouldNotChangeWithMultipleRGBApproximations() {

		String rawMessage = "&r&aHello &kthere! &6&lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!";

		String translated = MultiChatUtil.translateColourCodes(rawMessage);

		String approximated = MultiChatUtil.approximateRGBColourCodes(translated);

		String approximated2 = MultiChatUtil.approximateRGBColourCodes(approximated);

		assertEquals("Approximated RGB codes should remain the same after multiple parses of the approximator",
				approximated,
				approximated2);

	}

	@Test
	public void shouldGetMessageFromArgsCorrectly() {

		String[] args = new String[] {"this", "is", "a", "message!"};

		assertEquals("Message should be returned exactly from args",
				"this is a message!",
				MultiChatUtil.getMessageFromArgs(args));

		assertEquals("Message should be returned exactly from args (with offset start)",
				"is a message!",
				MultiChatUtil.getMessageFromArgs(args, 1));

		assertEquals("Message should be returned exactly from args (with offset start and end)",
				"is a",
				MultiChatUtil.getMessageFromArgs(args, 1, 2));

		Collection<String> collection = Arrays.asList(args);

		assertEquals("Message should be returned exactly from args (collection version)",
				"this is a message!",
				MultiChatUtil.getStringFromCollection(collection));

	}

	@Test
	public void shouldStripColourCodesCorrectly() {

		String rawMessage = "&r&aHello &kthere! &6&lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!";

		// ALL
		assertEquals("All codes should be stripped appropriately",
				"Hello there! this is a message! RGB too!",
				MultiChatUtil.stripColourCodes(rawMessage, false));

		// ALL #2
		assertEquals("All codes should be stripped appropriately",
				"Hello there! this is a message! RGB too!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.ALL));

		// SIMPLE
		assertEquals("Simple codes should be stripped appropriately",
				"Hello there! this is a message! &#ABCDEFRGB &xAbCdEftoo&x!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.SIMPLE));

		// SIMPLE COLOUR
		assertEquals("Simple colour codes should be stripped appropriately",
				"&rHello &kthere! &lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.COLOUR_SIMPLE));

		// ALL COLOUR
		assertEquals("All colour codes should be stripped appropriately",
				"Hello &kthere! &lthis &ois &ma &nmessage! RGB too!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.COLOUR_ALL));

		// FORMAT UNDERLINE
		assertEquals("Underline codes should be stripped appropriately",
				"&r&aHello &kthere! &6&lthis &ois &ma message! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.FORMAT_UNDERLINE));

		// FORMAT ITALIC
		assertEquals("Italic codes should be stripped appropriately",
				"&r&aHello &kthere! &6&lthis is &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.FORMAT_ITALIC));

		// FORMAT BOLD
		assertEquals("Bold codes should be stripped appropriately",
				"&r&aHello &kthere! &6this &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.FORMAT_BOLD));

		// FORMAT STRIKE
		assertEquals("Strike codes should be stripped appropriately",
				"&r&aHello &kthere! &6&lthis &ois a &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.FORMAT_STRIKE));

		// FORMAT OBFUSCATED
		assertEquals("Obfuscation codes should be stripped appropriately",
				"&r&aHello there! &6&lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.FORMAT_OBFUSCATED));

		// FORMAT RESET
		assertEquals("Reset codes should be stripped appropriately",
				"&aHello &kthere! &6&lthis &ois &ma &nmessage! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.FORMAT_RESET));

		// FORMAT ALL
		assertEquals("All format codes should be stripped appropriately",
				"&aHello there! &6this is a message! &#ABCDEFRGB &xAbCdEftoo&x&a&b&c&d&e&f!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.FORMAT_ALL));

		// X
		assertEquals("All X codes should be stripped appropriately",
				"&r&aHello &kthere! &6&lthis &ois &ma &nmessage! &r&a&b&c&d&e&fRGB &r&a&b&c&d&e&ftoo&r&a&b&c&d&e&f!",
				MultiChatUtil.stripColourCodes(rawMessage, false, TranslateMode.X));

	}

}
