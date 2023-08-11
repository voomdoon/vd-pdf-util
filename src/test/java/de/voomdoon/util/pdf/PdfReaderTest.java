package de.voomdoon.util.pdf;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.io.IOStreamUtil;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class PdfReaderTest {

	/**
	 * DOCME add JavaDoc for PdfReaderTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class PdfReader_File_Test extends TestBase {

		/**
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test() throws Exception {
			logTestStart();

			File input = new File(getTempDirectory() + "/input.pdf");

			IOStreamUtil.copy(IOStreamUtil.getInputStream("Rechnung1649393.pdf"), new FileOutputStream(input));

			PdfReader actual = new PdfReader(input);
			assertThat(actual).isNotNull();
		}
	}

	/**
	 * Test class for {@link PdfReader#readText(int, Rectangle)}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class ReadTextTest extends TestBase {

		/**
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test() throws Exception {
			logTestStart();

			File input = new File(getTempDirectory() + "/input.pdf");

			IOStreamUtil.copy(IOStreamUtil.getInputStream("Rechnung1649393.pdf"), new FileOutputStream(input));

			PdfReader reader = new PdfReader(input);

			String actual = reader.readText(0, new Rectangle(400, 0, 1000, 20));
			assertThat(actual).isEqualTo("Ecultor GmbH & Co. KG");
		}
	}
}
