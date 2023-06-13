package de.voomdoon.util.pdf;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class PdfReader {

	/**
	 * @since 0.1.0
	 */
	private PDDocument document;

	/**
	 * @since 0.1.0
	 */
	private PDFTextStripperByArea stripper;

	/**
	 * DOCME add JavaDoc for constructor PdfReader
	 * 
	 * @param file
	 *            {@link File}
	 * @since 0.1.0
	 */
	public PdfReader(File file) {
		try {
			document = Loader.loadPDF(file);
		} catch (IOException e1) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'PdfReader': " + e1.getMessage(), e1);
		}

		try {
			stripper = new PDFTextStripperByArea();
		} catch (IOException e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'PdfReader': " + e.getMessage(), e);
		}

		stripper.setSortByPosition(true);
	}

	/**
	 * DOCME add JavaDoc for method readText
	 * 
	 * @param page
	 * @param rectangle
	 * @return {@link String}
	 * @since 0.1.0
	 */
	public String readText(int page, Rectangle rectangle) {
		PDPage p = document.getDocumentCatalog().getPages().get(page);

		stripper.addRegion("current", new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height));

		try {
			stripper.extractRegions(p);
		} catch (IOException e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'readText': " + e.getMessage(), e);
		}

		String result = stripper.getTextForRegion("current");

		if (result.endsWith("\r\n")) {
			result = result.substring(0, result.length() - 2);
		}

		// TODO implement readText
		return result.trim();// TESTME trim
	}
}
