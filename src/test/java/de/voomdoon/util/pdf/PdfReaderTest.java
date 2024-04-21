package de.voomdoon.util.pdf;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
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
	class PdfReader_File_Test extends PdfReaderTestBase {

		/**
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test() throws Exception {
			logTestStart();

			PdfReader actual = getReader();
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
	class ReadTextTest extends PdfReaderTestBase {

		/**
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test() throws Exception {
			logTestStart();

			PdfReader reader = getReader();
			PDRectangle mediaBox = reader.getDocument().getPages().get(0).getMediaBox();

			String actual = reader.readText(0,
					new Rectangle(0, 0, (int) mediaBox.getWidth(), (int) mediaBox.getHeight()));
			assertThat(actual).contains("Lorem ipsum dolor sit amet").contains("commodo at,");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_y_bottomToTop() throws Exception {
			logTestStart();

			PdfReader reader = getReader();
			PDRectangle mediaBox = reader.getDocument().getPages().get(0).getMediaBox();

			String actual = reader.readText(0, new Rectangle(0, 0, (int) mediaBox.getWidth(), 200));
			assertThat(actual).contains("eget dui. Phasellus congue").doesNotContain("Lorem ipsum dolor");
		}
	}

	/**
	 * DOCME add JavaDoc for PdfReaderTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	private abstract class PdfReaderTestBase extends TestBase {

		/**
		 * DOCME add JavaDoc for method getReader
		 * 
		 * @return
		 * @throws IOException
		 * @throws FileNotFoundException
		 * @since 0.1.0
		 */
		protected PdfReader getReader() throws IOException, FileNotFoundException {
			File input = new File(getTempDirectory() + "/input.pdf");

			IOStreamUtil.copy(IOStreamUtil.getInputStream("sample.pdf"), new FileOutputStream(input));

			PdfReader reader = new PdfReader(input);
			return reader;
		}
	}
}
