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
import team_f.jsonconnector.entities.Person;
import java.io.File;
import java.net.URL;


public class MusicianPDFGenerator {

    private static final URL IMAGE = ClassLoader.getSystemResource("Logo2.jpg");
    private Person _person;
    private final PdfFont _regular;
    private final PdfFont _bold;


    public MusicianPDFGenerator(Person person, String directory) throws Exception {
        final String DEST = parseToPath(directory);
        _regular = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        _bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);

        if(person!=null){
            _person=person;
            File file = new File(DEST);
            file.getParentFile().mkdirs();
            manipulatePdf(DEST);}
        else{
            return;
        }
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
        img.setFixedPosition(600, 500);
        doc.add(img);

        Table table = new Table(new float[]{3,6});
        table.setWidthPercent(50).setMarginBottom(10);


        if (_person != null) {
            Paragraph p = new Paragraph("Information for: " + _person.getFirstname() + " " + _person.getLastname());
            p.setFont(_bold);
            p.setFontSize(20);
            p.setMarginTop(35);
            doc.add(p);

            table.addCell(new Cell().setFont(_bold).add("ID"));
            if(_person.getPersonID() > 0){
                table.addCell(new Cell().add(String.valueOf(_person.getPersonID())));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Initials"));
            if(_person.getInitials()!=null){
                table.addCell(new Cell().add(_person.getInitials()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Gender"));
            if(_person.getGender().toString()!=null){
                table.addCell(new Cell().add(_person.getGender().toString()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("First Name"));
            if(_person.getFirstname()!=null){
                table.addCell(new Cell().add(_person.getFirstname()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Last Name"));
            if(_person.getLastname()!=null){
                table.addCell(new Cell().add(_person.getLastname()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Username"));
            if(_person.getAccount()!=null&&_person.getAccount().getUsername()!=null){
                table.addCell(new Cell().add(_person.getAccount().getUsername()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Address"));
            if(_person.getAddress()!=null){
                table.addCell(new Cell().add(_person.getAddress()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Phone Number"));
            if(_person.getPhoneNumber()!=null){
                table.addCell(new Cell().add(_person.getPhoneNumber()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Email"));
            if(_person.getEmail()!=null){
                table.addCell(new Cell().add(_person.getEmail()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Account Role"));
            if(_person.getAccount()!=null&&_person.getAccount().getRole().toString()!=null){
                table.addCell(new Cell().add(_person.getAccount().getRole().toString()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Person Role"));
            if(_person.getPersonRole().toString()!=null){
                table.addCell(new Cell().add(_person.getPersonRole().toString()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Section"));
            if(_person.getInstrumentType()!=null&&_person.getInstrumentType().name()!=null){
                table.addCell(new Cell().add(_person.getInstrumentType().name()));
            } else {
                table.addCell(" ");
            }

            table.addCell(new Cell().setFont(_bold).add("Instruments"));
            if(_person.getInstrumentType()!=null&&_person.getInstrumentType().name()!=null){
                table.addCell(new Cell().add(_person.getInstrumentType().name()));
            } else {
                table.addCell(" ");
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