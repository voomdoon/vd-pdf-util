package de.voomdoon.util.pdf;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.Closeable;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;

//TESTME whole class

/**
 * DOCME add JavaDoc for GridDrawer <br>
 * orientation: 0,0: left bottom
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class PdfGraphics implements Closeable {

	/**
	 * @since 0.1.0
	 */
	private static final boolean COMPRESS_NO = false;

	/**
	 * @since 0.1.0
	 */
	private static final boolean RESET_CONTEXT_NO = false;

	/**
	 * Creates graphics for appending content to a PDF page.
	 * 
	 * @param document
	 *            {@link PDDocument}
	 * @param page
	 *            {@link PDPage}
	 * @return {@link PdfGraphics}
	 * @since 0.1.0
	 */
	public static PdfGraphics create(PDDocument document, PDPage page) {
		PDPageContentStream stream;

		try {
			stream = new PDPageContentStream(document, page, AppendMode.APPEND, COMPRESS_NO, RESET_CONTEXT_NO);
		} catch (IOException e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'create': " + e.getMessage(), e);
		}

		return new PdfGraphics(stream);
	}

	/**
	 * @since 0.1.0
	 */
	private PDPageContentStream stream;

	/**
	 * Creates PDF graphics backed by the supplied content stream.
	 * 
	 * @param stream
	 *            {@link PDPageContentStream}
	 * @since 0.1.0
	 */
	public PdfGraphics(PDPageContentStream stream) {
		this.stream = stream;
	}

	/**
	 * DOCME add JavaDoc for method close
	 * 
	 * @since 0.1.0
	 */
	@Override
	public void close() {
		try {
			stream.close();
		} catch (IOException e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'close': " + e.getMessage(), e);
		}
	}

	/**
	 * Draws a line between two points.
	 * 
	 * @param x0
	 *            starting x-coordinate
	 * @param y0
	 *            starting y-coordinate
	 * @param x1
	 *            ending x-coordinate
	 * @param y1
	 *            ending y-coordinate
	 * @since 0.1.0
	 */
	public void drawLine(float x0, float y0, float x1, float y1) {
		try {
			stream.moveTo(x0, y0);
			stream.lineTo(x1, y1);
			stream.stroke();
		} catch (IOException e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'drawLine': " + e.getMessage(), e);
		}
	}

	/**
	 * Draws a rectangle.
	 * 
	 * @param rectangle
	 *            {@link Rectangle}
	 * @since 0.1.0
	 */
	public void drawRectangle(Rectangle rectangle) {
		drawLine(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y);
		drawLine(rectangle.x + rectangle.width, rectangle.y, rectangle.x + rectangle.width,
				rectangle.y + rectangle.height);
		drawLine(rectangle.x + rectangle.width, rectangle.y + rectangle.height, rectangle.x,
				rectangle.y + rectangle.height);
		drawLine(rectangle.x, rectangle.y + rectangle.height, rectangle.x, rectangle.y);
	}

	/**
	 * Sets the stroking alpha value.
	 * 
	 * @param alpha
	 *            alpha value
	 * @since 0.1.0
	 */
	public void setAlpha(float alpha) {
		PDExtendedGraphicsState graphicsState = new PDExtendedGraphicsState();
		graphicsState.setStrokingAlphaConstant(alpha);
		setGraphicsStateParameters(graphicsState);
	}

	/**
	 * Applies extended graphics state parameters.
	 * 
	 * @param graphicsState
	 *            {@link PDExtendedGraphicsState}
	 * @since 0.1.0
	 */
	public void setGraphicsStateParameters(PDExtendedGraphicsState graphicsState) {
		try {
			stream.setGraphicsStateParameters(graphicsState);
		} catch (IOException e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'setGraphicsStateParameters': " + e.getMessage(), e);
		}
	}

	/**
	 * Sets the stroking color.
	 *
	 * @param color
	 *            {@link Color}
	 * @since 0.1.0
	 */
	public void setStrokingColor(Color color) {
		try {
			stream.setStrokingColor(color);
		} catch (IOException e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'setStrokingColor': " + e.getMessage(), e);
		}
	}
}
