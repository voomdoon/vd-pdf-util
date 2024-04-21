package de.voomdoon.util.pdf;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripperByArea;

//OPTIMIZE speed: remove regions from stripper

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
	 * @return document
	 * @since 0.1.0
	 */
	public PDDocument getDocument() {
		return document;
	}

	/**
	 * DOCME add JavaDoc for method readText
	 * 
	 * @param pageIndex
	 *            (first: 0)
	 * @param rectangle
	 *            (y=0: bottom)
	 * @return {@link String}
	 * @since 0.1.0
	 */
	public String readText(int pageIndex, Rectangle rectangle) {
		// logger.trace("readText " + pageIndex + " " + rectangle);

		// TODO concurrency
		PDPage page = document.getDocumentCatalog().getPages().get(pageIndex);

		String dummyName = "current";
		stripper.addRegion(dummyName, convert(rectangle, page));

		try {
			stripper.extractRegions(page);
		} catch (IOException e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'extractRegions': " + e.getMessage(), e);
		}

		String result = getTextForRegion(dummyName);

		return result.trim();// TESTME trim
	}

	/**
	 * DOCME add JavaDoc for method convert
	 * 
	 * @param rectangle
	 * @param p
	 * @return
	 * @since 0.1.0
	 */
	private Rectangle convert(Rectangle rectangle, PDPage p) {
		PDRectangle mediaBox = p.getMediaBox();

		return new Rectangle(rectangle.x, (int) mediaBox.getHeight() - rectangle.y - rectangle.height, rectangle.width,
				rectangle.height);
	}

	/**
	 * DOCME add JavaDoc for method getTextForRegion
	 * 
	 * @param dummyName
	 * @return
	 * @since 0.1.0
	 */
	private String getTextForRegion(String dummyName) {
		String result = stripper.getTextForRegion(dummyName);

		if (result.endsWith("\r\n")) {
			result = result.substring(0, result.length() - 2);
		}
		return result;
	}
}
