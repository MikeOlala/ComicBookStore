package util;

import model.AnalysisResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class OoxmlExporter {

    private OoxmlExporter() {}

    public static File exportTrendReport(AnalysisResult result) throws IOException {
        Map<String, Object> data = result.getResult();

        String filename = "trend_report_" + result.getResultID() + ".xlsx";
        File file = new File(filename);

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file))) {
            writeEntry(zos, "[Content_Types].xml", contentTypesXml());
            writeEntry(zos, "_rels/.rels", relsXml());
            writeEntry(zos, "xl/workbook.xml", workbookXml());
            writeEntry(zos, "xl/_rels/workbook.xml.rels", workbookRelsXml());
            writeEntry(zos, "xl/worksheets/sheet1.xml", sheetXml(data));
            writeEntry(zos, "xl/styles.xml", stylesXml());
            writeEntry(zos, "xl/sharedStrings.xml", sharedStringsXml(data));
            writeEntry(zos, "docProps/core.xml", coreXml(result));
            writeEntry(zos, "docProps/app.xml", appXml());
        }

        return file;
    }

    private static void writeEntry(ZipOutputStream zos, String name, String content) throws IOException {
        zos.putNextEntry(new ZipEntry(name));
        OutputStreamWriter writer = new OutputStreamWriter(zos, StandardCharsets.UTF_8);
        writer.write(content);
        writer.flush();
        zos.closeEntry();
    }

    private static String escapeXml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&apos;");
    }

    /* ---- XML parts ---- */

    private static String contentTypesXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">"
                + "<Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/>"
                + "<Default Extension=\"xml\" ContentType=\"application/xml\"/>"
                + "<Override PartName=\"/xl/workbook.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml\"/>"
                + "<Override PartName=\"/xl/worksheets/sheet1.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\"/>"
                + "<Override PartName=\"/xl/styles.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml\"/>"
                + "<Override PartName=\"/xl/sharedStrings.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml\"/>"
                + "</Types>";
    }

    private static String relsXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
                + "<Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"xl/workbook.xml\"/>"
                + "</Relationships>";
    }

    private static String workbookXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<workbook xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\""
                + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">"
                + "<sheets><sheet name=\"TrendReport\" sheetId=\"1\" r:id=\"rId1\"/></sheets>"
                + "</workbook>";
    }

    private static String workbookRelsXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
                + "<Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\" Target=\"worksheets/sheet1.xml\"/>"
                + "<Relationship Id=\"rId2\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles\" Target=\"styles.xml\"/>"
                + "<Relationship Id=\"rId3\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings\" Target=\"sharedStrings.xml\"/>"
                + "</Relationships>";
    }

    private static String stylesXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<styleSheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">"
                + "<fonts count=\"2\">"
                + "<font><sz val=\"11\"/><name val=\"Calibri\"/></font>"
                + "<font><b/><sz val=\"11\"/><name val=\"Calibri\"/></font>"
                + "</fonts>"
                + "<fills count=\"2\">"
                + "<fill><patternFill patternType=\"none\"/></fill>"
                + "<fill><patternFill patternType=\"gray125\"/></fill>"
                + "</fills>"
                + "<borders count=\"1\"><border><left/><right/><top/><bottom/><diagonal/></border></borders>"
                + "<cellStyleXfs count=\"1\"><xf numFmtId=\"0\" fontId=\"0\" fillId=\"0\" borderId=\"0\"/></cellStyleXfs>"
                + "<cellXfs count=\"2\">"
                + "<xf numFmtId=\"0\" fontId=\"0\" fillId=\"0\" borderId=\"0\" xfId=\"0\"/>"
                + "<xf numFmtId=\"0\" fontId=\"1\" fillId=\"0\" borderId=\"0\" xfId=\"0\" applyFont=\"1\"/>"
                + "</cellXfs>"
                + "</styleSheet>";
    }

    private static String sharedStringsXml(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        sb.append("<sst xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\" count=\"20\" uniqueCount=\"20\">");

        String[] labels = {"Result ID", "Category", "Trend Direction", "Trend Score",
                "Confidence", "Interpretation", "Analysis Date", "Total Revenue",
                "Total Sales", "Total Views"};
        String[] vals = {String.valueOf(data.get("resultID")), String.valueOf(data.get("category")),
                String.valueOf(data.get("trendDirection")), String.format("%.2f", data.get("trendScore")),
                String.format("%.2f%%", data.get("confidence")), String.valueOf(data.get("interpretation")),
                String.valueOf(data.get("analysisDate")), String.valueOf(data.get("totalRevenue")),
                String.valueOf(data.get("totalSales")), String.valueOf(data.get("totalViews"))};

        for (String s : labels) {
            sb.append("<si><t>").append(escapeXml(s)).append("</t></si>");
        }
        for (String s : vals) {
            sb.append("<si><t>").append(escapeXml(s)).append("</t></si>");
        }
        sb.append("</sst>");
        return sb.toString();
    }

    private static String sheetXml(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        sb.append("<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
        sb.append("<cols><col min=\"1\" max=\"1\" width=\"20\" customWidth=\"1\"/><col min=\"2\" max=\"2\" width=\"30\" customWidth=\"1\"/></cols>");
        sb.append("<sheetData>");

        String[] labels = {"Result ID", "Category", "Trend Direction", "Trend Score",
                "Confidence", "Interpretation", "Analysis Date", "Total Revenue",
                "Total Sales", "Total Views"};
        String[] vals = {String.valueOf(data.get("resultID")), String.valueOf(data.get("category")),
                String.valueOf(data.get("trendDirection")), String.format("%.2f", data.get("trendScore")),
                String.format("%.2f%%", data.get("confidence")), String.valueOf(data.get("interpretation")),
                String.valueOf(data.get("analysisDate")), String.valueOf(data.get("totalRevenue")),
                String.valueOf(data.get("totalSales")), String.valueOf(data.get("totalViews"))};

        for (int i = 0; i < labels.length; i++) {
            int r = i + 1;
            String style = (i < 1) ? " s=\"1\"" : "";
            sb.append("<row r=\"").append(r).append("\">");
            sb.append("<c r=\"A").append(r).append("\" t=\"s\"").append(style).append("><v>").append(i).append("</v></c>");
            sb.append("<c r=\"B").append(r).append("\" t=\"s\"").append("><v>").append(i + labels.length).append("</v></c>");
            sb.append("</row>");
        }

        sb.append("</sheetData></worksheet>");
        return sb.toString();
    }

    private static String coreXml(AnalysisResult result) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<cp:coreProperties xmlns:cp=\"http://schemas.openxmlformats.org/package/2006/metadata/core-properties\""
                + " xmlns:dc=\"http://purl.org/dc/elements/1.1/\""
                + " xmlns:dcterms=\"http://purl.org/dc/terms/\">"
                + "<dc:title>Trend Analysis Report #" + result.getResultID() + "</dc:title>"
                + "<dc:subject>Trend Report</dc:subject>"
                + "<dc:creator>ComicBookStore System</dc:creator>"
                + "<dcterms:created>" + result.getAnalysisDate() + "</dcterms:created>"
                + "</cp:coreProperties>";
    }

    private static String appXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Properties xmlns=\"http://schemas.openxmlformats.org/officeDocument/2006/extended-properties\">"
                + "<Application>ComicBookStore</Application>"
                + "<DocSecurity>0</DocSecurity>"
                + "<ScaleCrop>false</ScaleCrop>"
                + "<LinksUpToDate>false</LinksUpToDate>"
                + "<SharedDoc>false</SharedDoc>"
                + "</Properties>";
    }
}
