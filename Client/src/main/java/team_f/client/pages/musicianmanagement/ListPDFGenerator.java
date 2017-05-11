package team_f.client.pages.musicianmanagement;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import team_f.client.pages.monthpublish.FooterHandler;
import team_f.jsonconnector.entities.EventDuty;
import team_f.jsonconnector.entities.Person;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by w7pro on 11.05.2017.
 */
public class ListPDFGenerator {
    private static final URL IMAGE = ClassLoader.getSystemResource("Logo2.jpg");
    private List<Person> _list;
    private final PdfFont _regular;
    private final PdfFont _bold;

    public ListPDFGenerator(List<Person> list,String directory) throws Exception {
        final String DEST = parseToPath(directory);
        _regular = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        _bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);

        _list = list;
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        manipulatePdf(DEST);
    }

    protected String parseToPath(String directory){
        StringBuilder finalPath=new StringBuilder();
        for(int i=0; i<directory.length();i++){
            if (directory.charAt(i)=='\\') {
                finalPath.append('/');
            }else{
                finalPath.append(directory.charAt(i));
            }
        }
        return finalPath.toString();
    }

    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterHandler());
        Image img = new Image(ImageDataFactory.create(IMAGE));
        img.scaleToFit(220, 120);
        img.setFixedPosition(600 , 500 );
        doc.add(img);

        Paragraph p=new Paragraph("Musician List");
        p.setFont(_bold);
        p.setFontSize(20);
        p.setMarginTop(35);
        doc.add(p);


        Table table = new Table(new float[]{1,1,1,2,2,2,2,2,2,2,2});

        table.addHeaderCell(new Cell().setFont(_bold).add("ID"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Initials"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Gender"));
        table.addHeaderCell(new Cell().setFont(_bold).add("First Name"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Last Name"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Address"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Phone Number"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Email"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Person Role"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Instruments"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Section"));

        table.setFontSize(10);

       if(_list != null && !_list.isEmpty()) {
            for (Person person : _list) {
                if (person != null) {
                    if (person.getPersonID() > 0) {
                        table.addCell(new Cell().add(String.valueOf(person.getPersonID())));
                    } else {
                        table.addCell(" ");
                    }

                    if (person.getInitials() != null) {
                        table.addCell(new Cell().add(person.getInitials()));
                    } else {
                        table.addCell(" ");
                    }

                    if (person.getGender()!= null) {
                        table.addCell(new Cell().add(person.getGender().toString()));
                    } else {
                        table.addCell(" ");
                    }

                    if (person.getFirstname() != null) {
                        table.addCell(new Cell().add(person.getFirstname()));
                    } else {
                        table.addCell(" ");
                    }

                    if (person.getLastname() != null) {
                        table.addCell(new Cell().add(person.getLastname()));
                    } else {
                        table.addCell(" ");
                    }

                    if (person.getAddress() != null) {
                        table.addCell(new Cell().add(person.getAddress()));
                    } else {
                        table.addCell(" ");
                    }

                    if (person.getPhoneNumber() != null) {
                        table.addCell(new Cell().add(person.getPhoneNumber()));
                    } else {
                        table.addCell(" ");
                    }

                    if (person.getEmail() != null) {
                        table.addCell(new Cell().add(person.getEmail()));
                    } else {
                        table.addCell(" ");
                    }

                    if (person.getPersonRole().toString() != null) {
                        table.addCell(new Cell().add(person.getPersonRole().toString()));
                    } else {
                        table.addCell(" ");
                    }

                    if (person.getInstrumentType() != null && person.getInstrumentType().name() != null) {
                        table.addCell(new Cell().add(person.getInstrumentType().name()));
                    } else {
                        table.addCell(" ");
                    }

                    if (person.getInstrumentType() != null && person.getInstrumentType().name() != null) {
                        table.addCell(new Cell().add(person.getInstrumentType().name()));
                    } else {
                        table.addCell(" ");
                    }
                }
            }


           doc.add(table);
           doc.close();
           pdfDoc.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setGraphic(new ImageView("check.png"));
        alert.setHeaderText("Successfully converted selected Month to PDF");
        alert.showAndWait();

    }


}

}
