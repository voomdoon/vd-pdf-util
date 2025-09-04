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
import org.junit.jupiter.api.extension.ExtendWith;

import de.voomdoon.testing.file.TempFileExtension;
import de.voomdoon.testing.file.TempInputFile;
import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.io.IOStreamUtil;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
@ExtendWith(TempFileExtension.class)
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
		void test(@TempInputFile File inputFile) throws Exception {
			logTestStart();

			PdfReader actual = getReader(inputFile);

			assertThat(actual).isNotNull();
		}
	}

	/**
	 * Tests for {@link PdfReader#readText(int, Rectangle)}.
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
		void test(@TempInputFile File inputFile) throws Exception {
			logTestStart();

			PdfReader reader = getReader(inputFile);
			PDRectangle mediaBox = reader.getDocument().getPages().get(0).getMediaBox();

			String actual = reader.readText(0,
					new Rectangle(0, 0, (int) mediaBox.getWidth(), (int) mediaBox.getHeight()));

			assertThat(actual).contains("Lorem ipsum dolor sit amet").contains("commodo at,");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_y_bottomToTop(@TempInputFile File inputFile) throws Exception {
			logTestStart();

			PdfReader reader = getReader(inputFile);
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
		protected PdfReader getReader(File inputFile) throws IOException, FileNotFoundException {
			IOStreamUtil.copy(IOStreamUtil.getInputStream("sample.pdf"), new FileOutputStream(inputFile));

			PdfReader reader = new PdfReader(inputFile);

			return reader;
		}
	}
}