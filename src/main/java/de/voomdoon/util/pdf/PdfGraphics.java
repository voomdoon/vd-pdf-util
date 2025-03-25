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
	 * DOCME add JavaDoc for method create
	 * 
	 * @param document
	 * @param page
	 * @return
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
	 * DOCME add JavaDoc for constructor PdfDrawer
	 * 
	 * @param stream
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
	 * DOCME add JavaDoc for method drawLine
	 * 
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
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
	 * DOCME add JavaDoc for method drawRectangle
	 * 
	 * @param rectangle
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
	 * DOCME add JavaDoc for method setAlpha
	 * 
	 * @param alpha
	 * @since 0.1.0
	 */
	public void setAlpha(float alpha) {
		PDExtendedGraphicsState graphicsState = new PDExtendedGraphicsState();
		graphicsState.setStrokingAlphaConstant(alpha);
		setGraphicsStateParameters(graphicsState);
	}

	/**
	 * DOCME add JavaDoc for method setGraphicsStateParameters
	 * 
	 * @param graphicsState
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
	 * @param color
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
