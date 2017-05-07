package team_f.client.pages.monthpublish;



import com.itextpdf.kernel.events.IEventHandler;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

public class FooterHandler implements IEventHandler {

    protected String info;
    public void setInfo(String info) {
        this.info = info;
    }
    public String getInfo() {
        return info;
    }

    @Override
    public void handleEvent(com.itextpdf.kernel.events.Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfDocument pdfDoc = ((PdfDocumentEvent) event).getDocument();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            new Canvas(pdfCanvas, pdfDoc, pageSize)
                    .showTextAligned("Page "+Integer.toString(pdfDoc.getPageNumber(page)), pageSize.getWidth() / 2, 30, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0);
        }

    }
