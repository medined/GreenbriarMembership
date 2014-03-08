package com.affy.greenbriar;

import au.com.bytecode.opencsv.CSVReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ReadMembershipDatabaseDriver {

    String pdfFileName = "2014_gca_membership_drive.pdf";

    public static final Membership YEAR_2012 = new Membership("2012");
    public static final Membership YEAR_2013 = new Membership("2013");

    // (0,0)      lower left
    // (612, 792) upper right
    float lowerLeftX = PDPage.PAGE_SIZE_LETTER.getLowerLeftX();
    float lowerLeftY = PDPage.PAGE_SIZE_LETTER.getLowerLeftY();
    float upperRightX = PDPage.PAGE_SIZE_LETTER.getUpperRightX();
    float upperRightY = PDPage.PAGE_SIZE_LETTER.getUpperRightY();

    private final Set<String> streets = new TreeSet<String>();
    private final Map<String, String> captains = new TreeMap<String, String>();
    
    Greenbriar community = new Greenbriar();

    int lineCount = 0;

    public static void main(String[] args) throws FileNotFoundException, IOException, COSVisitorException {
        String membershipFile = "2013_Greenbriar_Membership.csv";
        String streetFile = "greenbriar_streets.csv";
        String captainFile = "2013_greenbriar_block_captains.csv";

        ReadMembershipDatabaseDriver driver = new ReadMembershipDatabaseDriver();
        driver.process(membershipFile, streetFile, captainFile);
    }

    private void process(final String membershipFile, final String streetFile, final String captainFile) throws FileNotFoundException, IOException, COSVisitorException {

        String[] components = null;

        CSVReader streetReader = new CSVReader(new FileReader(streetFile));
        while ((components = streetReader.readNext()) != null) {
            String id = components[0];
            String streetName = components[1];
            streets.add(streetName);
        }
        
        CSVReader captainReader = new CSVReader(new FileReader(captainFile));
        while ((components = captainReader.readNext()) != null) {
            String blockName = components[0];
            String lastName = components[1];
            String firstName = components[2];
            captains.put(blockName, firstName + " " + lastName);
        }

        new File(pdfFileName).delete();

        int incorrectStreets = 0;
        
        CSVReader reader = new CSVReader(new FileReader(membershipFile));
        while ((components = reader.readNext()) != null) {
            if (lineCount != 0) {
                String districtName = components[0];
                String blockName = components[1];
                String houseNumber = components[2];
                String streetName = components[3];
                String last = components[4];
                String first = components[5];
                String phone = components[6];
                String bc = components[7];
                String y2012 = components[8];
                String y2013 = components[9];
                String email = components[10];
                String comment = components[11];
                
                if (! streets.contains(streetName)) {
                    System.out.println("Incorrect Street: " + streetName);
                    incorrectStreets++;
                }

                District district = community.addDistrict(districtName);
                Block block = district.addBlock(blockName);
                House house = block.addHouse(houseNumber, streetName);
                Person person = house.addPerson(last, first, phone, email, comment);

                if (!y2012.isEmpty()) {
                    house.addYear(YEAR_2012);
                }
                if (!y2013.isEmpty()) {
                    house.addYear(YEAR_2013);
                }

                if (!bc.isEmpty()) {
                    block.addCaptain(person);
                }

            }
            lineCount++;
        }
        
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(community);
//        System.out.println(json);
        
        PrintWriter writer = new PrintWriter("greenbriar_membership.json");
        writer.println(json);
        writer.close();

        /*
        System.out.println("Incorrect Streets: " + incorrectStreets);
        
        final String title = "GCA Membership; Block Captain Work Sheet";

        Date now = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("YYYY-MMM-DD");

        PDDocument document = new PDDocument();

        for (District district : community.districts) {
            for (Block block : district.blocks) {
                PDPage page = addReportPage(document);

                drawHeader(document, page, title);
                drawPrintedAt(document, page, dataFormat.format(now));
                drawDistrictName(document, page, district.getName());
                drawBlockName(document, page, block.getName());
                drawBlockCaptain(document, page, block);
                drawHouseCount(document, page, block.getHouses().size());
                drawSeparatorLine(document, page);

                float x = 25;
                float y = PDPage.PAGE_SIZE_LETTER.getUpperRightY() - 100;
                y = drawHouse(document, page, block.getHouses(), x, y);
            }
        }

        document.save(pdfFileName);

        document.close();
        */
    }

    private float drawHouse(final PDDocument document, final PDPage page, final Set<House> houses, float x, float y) throws IOException {
        // how many people are in a block?
        int personCount = 0;
        for (House house : houses) {
            personCount += house.getPeople().size();
        }
        personCount++; // account for heading
        personCount++; // account for totals

        String[][] content = new String[personCount][7];
        content[0][0] = "House";
        content[0][1] = "Person";
        content[0][2] = "Phone";
        content[0][3] = "2012";
        content[0][4] = "2013";
        content[0][5] = "2014";
        content[0][6] = "Comments";

        int personIndex = 1;

        int count2012 = 0;
        int count2013 = 0;

        for (House house : houses) {
            String housePlace = house.getHouseNumber() + " " + house.getStreetName();
            float textWidth = 0;

            boolean firstPerson = true;

            for (Person person : house.getPeople()) {

                textWidth = tableFont.getStringWidth(person.getFirst()) / 1000 * tableFontSize;
                maxTextWidth = Math.max(maxTextWidth, textWidth);

                if (firstPerson) {
                    content[personIndex][0] = housePlace;
                } else {
                    content[personIndex][0] = "";
                }
                content[personIndex][1] = person.getLast() + ", " + person.getFirst();
                if (person.getEmail() != null && !person.getEmail().isEmpty()) {
                    content[personIndex][1] += "_" + person.getEmail();
                }
                content[personIndex][2] = person.getPhone();
                content[personIndex][3] = "";
                content[personIndex][4] = "";
                if (firstPerson) {
                    if (house.wasMemberIn(YEAR_2012)) {
                        content[personIndex][3] = "X";
                        count2012++;
                    }
                    if (house.wasMemberIn(YEAR_2013)) {
                        content[personIndex][4] = "X";
                        count2013++;
                    }
                }
                content[personIndex][5] = "";
                content[personIndex][6] = person.getComment();
                firstPerson = false;
                personIndex++;
            }
        }
        // Add totals to the table.
        content[personIndex][0] = "";
        content[personIndex][1] = "";
        content[personIndex][2] = "";
        content[personIndex][3] = String.format("%d", count2012);
        content[personIndex][4] = String.format("%d", count2013);
        content[personIndex][5] = "";
        content[personIndex][6] = "";

        int houseWidth = 115;
        int nameWidth = 190;
        int phoneWidth = 75;
        int paidWidth = 30;
        int commentWidth = 45;
        float[] widths = {houseWidth, nameWidth, phoneWidth, paidWidth, paidWidth, paidWidth, commentWidth};
        PDPageContentStream stream = new PDPageContentStream(document, page, true, true);
        y = drawTable(page, stream, y, 10, widths, content);
        stream.close();

        return y;
    }

    private void drawDistrictName(final PDDocument document, final PDPage page, final String districtName) throws IOException {
        PDFont font = PDType1Font.HELVETICA_BOLD;
        float fontSize = 12;
        float x = 5;
        float y = PDPage.PAGE_SIZE_LETTER.getUpperRightY() - 45;
        drawGrayString(document, page, font, fontSize, x, y, "District: ");
        drawString(document, page, font, fontSize, x + 50, y, districtName);
    }

    private void drawBlockName(final PDDocument document, final PDPage page, final String blockName) throws IOException {
        PDFont font = PDType1Font.HELVETICA_BOLD;
        float fontSize = 12;
        float x = 90;
        float y = PDPage.PAGE_SIZE_LETTER.getUpperRightY() - 45;
        drawGrayString(document, page, font, fontSize, x, y, "Block: ");
        drawString(document, page, font, fontSize, x + 45, y, blockName);
    }

    private void drawHouseCount(final PDDocument document, final PDPage page, final int numHouses) throws IOException {
        PDFont font = PDType1Font.HELVETICA_BOLD;
        float fontSize = 12;
        float x = 185;
        float y = PDPage.PAGE_SIZE_LETTER.getUpperRightY() - 45;
        drawGrayString(document, page, font, fontSize, x, y, "House Count: ");
        drawString(document, page, font, fontSize, x + 85, y, String.format("%d", numHouses));
    }

    private void drawBlockCaptain(final PDDocument document, final PDPage page, final Block block) throws IOException {
        PDFont font = PDType1Font.HELVETICA_BOLD;
        float fontSize = 12;
        float x = 300f;
        float y = PDPage.PAGE_SIZE_LETTER.getUpperRightY() - 45;
        drawGrayString(document, page, font, fontSize, x, y, "Captain: ");
        String captainNameA = null;
        String captainNameB = null;
        if (block.getCaptain() == null) {
            drawRedString(document, page, font, fontSize, x + 50, y, "None");
        } else {
            captainNameA = captains.get(block.getName());
            captainNameB = block.getCaptain() == null ? null : block.getCaptain().getName();
            drawString(document, page, font, fontSize, x + 50, y, block.getCaptain().getName());
        }

        if (captainNameA == null && captainNameB == null) {
            // both empty
        } else if (captainNameA == null && captainNameB != null) {
            System.out.println("1 Spreadsheet [" + captainNameA + "]  BC Sheet [" + captainNameB + "]");
        } else if (captainNameA != null && captainNameB == null) {
            System.out.println("2 Spreadsheet [" + captainNameA + "]  BC Sheet [" + captainNameB + "]");
        } else if (!captainNameA.equals(captainNameB)) {
            System.out.println("3 Spreadsheet [" + captainNameA + "]  BC Sheet [" + captainNameB + "]");
        }
        
    }

    private void drawRedString(final PDDocument document, final PDPage page, final PDFont font, final float fontSize, final float x, final float y, final String s) throws IOException {
        PDPageContentStream stream = new PDPageContentStream(document, page, true, true);
        stream.beginText();
        stream.setFont(font, fontSize);
        stream.moveTextPositionByAmount(x, y);
        setRedColor(stream);
        stream.drawString(s);
        resetColor(stream);
        stream.endText();
        stream.close();
    }

    private void drawGrayString(final PDDocument document, final PDPage page, final PDFont font, final float fontSize, final float x, final float y, final String s) throws IOException {
        PDPageContentStream stream = new PDPageContentStream(document, page, true, true);
        stream.beginText();
        stream.setFont(font, fontSize);
        stream.moveTextPositionByAmount(x, y);
        setGreyColor(stream);
        stream.drawString(s);
        resetColor(stream);
        stream.endText();
        stream.close();
    }

    private void drawString(final PDDocument document, final PDPage page, final PDFont font, final float fontSize, final float x, final float y, final String s) throws IOException {
        PDPageContentStream stream = new PDPageContentStream(document, page, true, true);
        stream.beginText();
        stream.setFont(font, fontSize);
        stream.moveTextPositionByAmount(x, y);
        stream.drawString(s);
        stream.endText();
        stream.close();
    }

    private void drawSeparatorLine(final PDDocument document, final PDPage page) throws IOException {
        float y = PDPage.PAGE_SIZE_LETTER.getUpperRightY() - 70;
        float pageWidth = PDPage.PAGE_SIZE_LETTER.getUpperRightX();
        float lineLength = (float) (pageWidth * .80);
        float leftOffset = (float) (pageWidth * .10);

        PDPageContentStream stream = new PDPageContentStream(document, page, true, true);
        stream.fillRect(leftOffset, y - 15, lineLength, 5);
        stream.close();
    }

    private void drawGrayString(final PDPageContentStream stream, final String s) throws IOException {
        setGreyColor(stream);
        stream.drawString(s);
        resetColor(stream);
    }

    private PDPage addReportPage(final PDDocument document) {
        PDPage page = new PDPage();
        page.setMediaBox(PDPage.PAGE_SIZE_LETTER);
        document.addPage(page);
        return page;
    }

    private void drawHeader(final PDDocument document, final PDPage page, final String title) throws IOException {
        PDFont font = PDType1Font.HELVETICA_BOLD;
        float marginTop = 10;
        float fontSize = 10;
        float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
        PDPageContentStream contentStream = null;
        contentStream = new PDPageContentStream(document, page, true, true);
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - marginTop - titleHeight);
        contentStream.drawString(String.format("%s", title));
        contentStream.endText();
        contentStream.close();
    }

    // 0, 0, 0 = black
    // 49, 7, 241 = blue
    private void drawPrintedAt(final PDDocument document, final PDPage page, final String dateString) throws IOException {
        PDFont font = PDType1Font.HELVETICA_BOLD;
        float marginRight = 20;
        float marginTop = 10;
        float fontSize = 8;
        float titleWidth = font.getStringWidth("Printed: " + dateString) / 1000 * fontSize;
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
        float x = page.getMediaBox().getWidth() - titleWidth - marginRight;
        float y = page.getMediaBox().getHeight() - marginTop - titleHeight;
        PDPageContentStream contentStream = null;
        contentStream = new PDPageContentStream(document, page, true, true);
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.moveTextPositionByAmount(x, y);
        drawGrayString(contentStream, "Printed: ");
        contentStream.drawString(dateString);
        contentStream.endText();
        contentStream.close();
    }

    private void setRedColor(final PDPageContentStream stream) throws IOException {
        stream.setNonStrokingColor(218, 53, 53);
    }

    private void setGreyColor(final PDPageContentStream stream) throws IOException {
        stream.setNonStrokingColor(125, 125, 125);
    }

    private void resetColor(final PDPageContentStream stream) throws IOException {
        stream.setNonStrokingColor(0, 0, 0);
    }

    private void reviewAllPages(final PDDocument document) {
        List pages = document.getDocumentCatalog().getAllPages();
        for (int i = 0; i < pages.size(); i++) {
            PDPage page = ((PDPage) pages.get(i));
        }
    }

    private static final PDType1Font tableFont = PDType1Font.TIMES_ROMAN;
    private static final int tableFontSize = 8;
    private float maxTextWidth = 0;

    public float drawTable(PDPage page, PDPageContentStream contentStream, float y, float margin, float[] widths, String[][] content) throws IOException {
        if (content.length == 0) {
            return y;
        }
        final int rowCount = content.length;
        int cols = widths.length;

        final float headerHeight = 20f;
        final float rowHeight = 30f;

        final float tableWidth = page.findMediaBox().getWidth() - (2 * margin);
        final float tableHeight = (headerHeight * 2) + (rowHeight * (rowCount - 2));
        final float cellMargin = 5f;

        contentStream.setNonStrokingColor(125, 125, 125);
        //draw the rows
        float nexty = y;
        for (int i = 0; i <= rowCount; i++) {
            contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
            nexty -= (i == 0 || i == content.length - 1) ? headerHeight : rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i < cols; i++) {
            contentStream.drawLine(nextx, y, nextx, y - tableHeight);
            nextx += widths[i];
        }
        contentStream.drawLine(margin + tableWidth, y, margin + tableWidth, y - tableHeight);
        contentStream.setNonStrokingColor(0, 0, 0);

        float textx = margin + cellMargin;
        float texty = y - 15;
        int rowIndex = 0;
        for (String[] row : content) {
            int i = 0;
            for (String cell : row) {
                if (cell != null) {
                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(textx, texty + 3);
                    if (rowIndex == 0 || rowIndex == content.length - 1) {
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, tableFontSize);
                    } else {
                        contentStream.setFont(PDType1Font.HELVETICA, tableFontSize);
                    }
                    if (i != 1) {
                        contentStream.drawString(cell);
                    } else {
                        if (cell.indexOf("_") != -1) {
                            // handle email with person's name.
                            String[] components = cell.split("_");
                            String name = components[0];
                            String email = components[1];
                            contentStream.drawString(name);
                            contentStream.moveTextPositionByAmount(10, -10);
                            contentStream.drawString(email);
                        } else {
                            contentStream.drawString(cell);
                        }
                        
                    }
                    contentStream.endText();
                }
                textx += widths[i];
                i++;
            }
            texty -= (rowIndex == 0 || rowIndex == content.length - 1) ? headerHeight : rowHeight;
            textx = margin + cellMargin;
            rowIndex++;
        }

        return texty;
    }
}
