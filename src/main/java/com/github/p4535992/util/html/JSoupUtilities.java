package com.github.p4535992.util.html;

import com.github.p4535992.util.http.HttpUtilities;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Class utility for work with JsoupKit
 *
 * @author 4535992.
 * @version 2015-09-24.
 */
@SuppressWarnings("unused")
public class JSoupUtilities {

    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(JSoupUtilities.class);

    //Variable to filter the attributes
    private static boolean filterAttr = false;

    public static boolean isFilterAttr() {
        return filterAttr;
    }

    public static void setFilterAttr(boolean filterAttr) {
        JSoupUtilities.filterAttr = filterAttr;
    }


    /**
     * Method for clean the string o html of the HTML document.
     *
     * @param Testo the String to clean.
     * @return the cleaned String.
     */
    private static String clean(String Testo) {
        if (Testo != null && !Testo.isEmpty() && !Testo.trim().isEmpty()) {
            Testo = Testo.replace("&nbsp;", " ").replace("\t", " ").replace("\r", " ").replace("\n", " ").replace("�", "à").trim();
            Testo = Testo.replaceAll("\\s+", " ");
        }
        //else if(Testo!=null && !Testo.isEmpty()){Testo = "";}
        else {
            Testo = null;
        }
        return Testo;
    }

    /**
     * Method for extract any type of element from a html document.
     *
     * @param HTMLDocument   the String of the HTML Document.
     * @param HTML           if true get the HTML tag not only the value.
     * @param tagName        the String tag name to extract.
     * @param attribute      the String attribute to research and extract.
     * @param valueAttribute the Object value of the attribute to research and extract.
     * @return the List of List of List of the values of a Table or List HTML.
     * @throws Exception if any error is occurred.
     */
    public static List<List<List<String>>> UniversalExtractor(
            String HTMLDocument, boolean HTML, String tagName, String attribute, String valueAttribute) throws Exception {
        tagName = tagName + "[" + attribute + "=" + valueAttribute + "]";
        return UniversalExtractor(HTMLDocument, HTML, tagName);
    }

    /**
     * Method for extract any type of element from a html document.
     *
     * @param htmlContentOrUrl the String cotnent of a HTML Document or the String of a URL page.
     * @param HTML             if true get the HTML tag not only the value.
     * @param tagName          the String tag name to extract.
     * @return the List of List of List of the values of a Table or List HTML.
     * @throws Exception if any error is occurred.
     */
    public static List<List<List<String>>> UniversalExtractor(String htmlContentOrUrl, boolean HTML, String tagName) throws Exception {
        org.jsoup.nodes.Document htmldoc = toJsoupDocument(htmlContentOrUrl);
        return UniversalExtractor(htmldoc, HTML, tagName);
    }

    /**
     * Method for extract any type of element from a html document.
     *
     * @param url              the URL address to the Web Page.
     * @param HTML             if true get the HTML tag not only the value.
     * @param tagName          the String tag name to extract.
     * @return the List of List of List of the values of a Table or List HTML.
     * @throws Exception if any error is occurred.
     */
    public static List<List<List<String>>> UniversalExtractor(URL url, boolean HTML, String tagName) throws Exception {
        org.jsoup.nodes.Document htmldoc = org.jsoup.Jsoup.connect(url.toString()).get();
        return UniversalExtractor(htmldoc, HTML, tagName);
    }

    /**
     * Method for extract any type of element from a html document
     *
     * @param HTMLDocument   the String of the HTML Document.
     * @param HTML           if true get the HTML tag not only the value.
     * @param tagName        the String tag name to extract.
     * @return the List of List of List of the values of a Table or List HTML.
     * @throws Exception if any error is occurred.
     */
    public static List<List<List<String>>> UniversalExtractor(org.jsoup.nodes.Document HTMLDocument, boolean HTML, String tagName) throws Exception {
        String rootTag;
        if (tagName.toLowerCase().equals("ul") || tagName.toLowerCase().contains("//ul")) {
            rootTag = "ul";
        } else if (tagName.toLowerCase().equals("ol") || tagName.toLowerCase().contains("//ol")) {
            rootTag = "ol";
        } else if (tagName.toLowerCase().equals("li") || tagName.toLowerCase().contains("//li")) {
            rootTag = "li";
        } else if (tagName.toLowerCase().equals("table") || tagName.toLowerCase().contains("//table")) {
            rootTag = "table";
        } else {
            throw new Exception("ERROR: the selected tagName in not supported, plese use : li,ul,ol or table");
        }
        List<List<List<String>>> ResultCollection;
        //HtmlNodeCollection RootTag2 = HTMLWork.SelectNodes(".//tbody");
        org.jsoup.select.Elements RootTag = HTMLDocument.select(rootTag);//e.home ul,ol,table
        ResultCollection = subExtractor6(RootTag, HTML);
        return ResultCollection;

    }

    /**
     * Method for the extraction of simple item list "li".
     *
     * @param HTMLDocument   the String of the HTML Document.
     * @param HTML           if true get the HTML tag not only the value.
     * @return the List of List of List of the values of a Table or List HTML.
     * @throws Exception if any error is occurred.
     */
    public static List<List<List<String>>> TablesExtractor(String HTMLDocument, boolean HTML) throws Exception {
        return UniversalExtractor(HTMLDocument, HTML, "table");
    }

    /**
     * Method for the extraction of simple item list "li".
     *
     * @param url            the URL address to the Web Page.
     * @param HTML           if true get the HTML tag not only the value.
     * @return the List of List of List of the values of a Table or List HTML.
     * @throws Exception if any error is occurred.
     */
    public static List<List<List<String>>> TablesExtractor(URL url, boolean HTML) throws Exception {
        return UniversalExtractor(url, HTML, "table");
    }

    /**
     * Method for the extraction of simple item list "li".
     *
     * @param HTMLDocument   the String of the HTML Document.
     * @param HTML           if true get the HTML tag not only the value.
     * @return the List of List of List of the values of a Table or List HTML.
     * @throws Exception if any error is occurred.
     */
    public static List<List<List<String>>> SimpleListItemExtractor(String HTMLDocument, boolean HTML) throws Exception {
        return UniversalExtractor(HTMLDocument, HTML, "li");
    }

    /**
     * Method for the extraction of a order list "ol".
     *
     * @param HTMLDocument   the String of the HTML Document.
     * @param HTML           if true get the HTML tag not only the value.
     * @return the List of List of List of the values of a Table or List HTML.
     * @throws Exception if any error is occurred.
     */
    public static List<List<List<String>>> OrderListItemExtractor(String HTMLDocument, boolean HTML) throws Exception {
        return UniversalExtractor(HTMLDocument, HTML, "ol");
    }

    /**
     * Method for the extraction of a order list "ul".
     *
     * @param HTMLDocument   the String of the HTML Document.
     * @param HTML           if true get the HTML tag not only the value.
     * @return the List of List of List of the values of a Table or List HTML.
     * @throws Exception if any error is occurred.
     */
    public static List<List<List<String>>> UnorderListItemExtractor(String HTMLDocument, boolean HTML) throws Exception {
        return UniversalExtractor(HTMLDocument, HTML, "ul");
    }


    /**
     * Method for extract all elements from a html document , version : 6 (2015-04-15).
     *
     * @param TableColl the List of JSoup Element to inspect like a Table Object.
     * @param HTML  if true get the HTML tag not only the value.
     * @return the List of List of List of the values of a Table or List HTML.
     */
    private static List<List<List<String>>> subExtractor6(org.jsoup.select.Elements TableColl, boolean HTML) {
        List<List<List<String>>> ResultCollection = new ArrayList<>();
        //New Table
        for (org.jsoup.nodes.Element TableElem : TableColl) {
            switch (TableElem.tagName().toUpperCase()) {
                case "TABLE":
                case "UL":
                case "OL": {
                    int iMaxCol = 0;
                    List<List<String>> dtTableList = new ArrayList<>();
                    for (org.jsoup.nodes.Element _CurrentElement : TableElem.children()) {
                        switch (_CurrentElement.tagName().toUpperCase()) {
                            case "TR": {
                                List<String> Cols = new ArrayList<>();
                                for (int iElem = 0; iElem < _CurrentElement.children().size(); iElem++) {
                                    if (Objects.equals(_CurrentElement.child(iElem).tagName().toUpperCase(), "TD")
                                            || Objects.equals(_CurrentElement.child(iElem).tagName().toUpperCase(), "TH")) {
                                        if (_CurrentElement.child(iElem).children().size() == 0)
                                            Cols = Extractor6(_CurrentElement.child(iElem), HTML, Cols);
                                        else
                                            for (org.jsoup.nodes.Element node : _CurrentElement.child(iElem).children()) {
                                                Cols = Extractor6(node, HTML, Cols);
                                            }
                                    }
                                    //if td o th
                                    else {
                                        Cols = Extractor6(_CurrentElement.child(iElem), HTML, Cols);
                                    }
                                }//for each node in tr
                                if (Cols.size() > iMaxCol) iMaxCol = Cols.size();
                                if (Cols.size() > 0) dtTableList.add(Cols);
                            }
                            break;
                            case "TBODY":
                            case "THEAD":
                            case "TFOOT": {
                                //Cols = ExtractorRomis(_CurrentElement, HTML, Cols);
                                for (org.jsoup.nodes.Element _rows : _CurrentElement.children()) {
                                    if (Objects.equals(_rows.tagName().toUpperCase(), "TR")) {
                                        List<String> Cols = new ArrayList<>();
                                        for (int iElem = 0; iElem < _rows.children().size(); iElem++) {
                                            if (Objects.equals(_rows.child(iElem).tagName().toUpperCase(), "TD")
                                                    || Objects.equals(_rows.child(iElem).tagName().toUpperCase(), "TH")) {
                                                if (_rows.child(iElem).children().size() == 0)
                                                    Cols = Extractor6(_rows.child(iElem), HTML, Cols);
                                                else
                                                    for (org.jsoup.nodes.Element node : _rows.child(iElem).children()) {
                                                        Cols = Extractor6(node, HTML, Cols);
                                                    }
                                            }
                                        }//for ielem _rows.ChildNodes
                                        if (Cols.size() > iMaxCol) iMaxCol = Cols.size();
                                        if (Cols.size() > 0) dtTableList.add(Cols);
                                    }//if _rows NamespaceHandling is TR
                                }//foreach HtmlNode _rows in _CurrentElement.ChildNodes
                            }
                            break;
                            case "CAPTION":
                            case "H3":
                            case "H2":
                            case "LI": {
                                List<String> Cols = new ArrayList<>();
                                Cols = Extractor6(_CurrentElement, HTML, Cols);
                                if (Cols.size() > iMaxCol) iMaxCol = Cols.size();
                                if (Cols.size() > 0) dtTableList.add(Cols);
                                break;
                            }
                        }//end of the switch TR,TBODY,CAPTION
                    }
                    ResultCollection.add(dtTableList);
                }
                break;
                case "LI": {
                    //int iMaxCol = 0;
                    List<List<String>> dtTableList = new ArrayList<>();
                    List<String> Cols = new ArrayList<>();
                    Cols = Extractor6(TableElem, HTML, Cols);
                    //if (Cols.size() > iMaxCol) iMaxCol = Cols.size();
                    if (Cols.size() > 0) dtTableList.add(Cols);
                    ResultCollection.add(dtTableList);
                }
                break;
                case "DL": {
                    //org.jsoup.nodes.Element _CurrentElement = TableElem; //NOT DELETE
                    //iCurCol = 0; iCurRow = 0;
                    int iMaxCol = 0;
                    //int iCurCol = 0;
                    List<List<String>> dtTable = new ArrayList<>();
                    List<String> Cols = new ArrayList<>();
                    for (int iRow = 0; iRow < TableElem.children().size(); iRow++) {
                        if (Objects.equals(TableElem.child(iRow).tagName().toUpperCase(), "DT")) {
                            Cols = Extractor6(TableElem.child(iRow), HTML, Cols);
                            if (Cols.size() > iMaxCol) iMaxCol = Cols.size();
                            if (Cols.size() > 0) dtTable.add(Cols);
                            Cols = new ArrayList<>();
                            //iCurCol = 0;
                            //CurTabella[iCurRow - 1, iCurCol] = (Testo == null) ? "" : Testo;
                            //iCurCol = 1;
                        }

                        if (Objects.equals(TableElem.child(iRow).tagName().toUpperCase(), "DD")
                            //&& iCurCol == 1
                                ) {
                            Cols = Extractor6(TableElem.child(iRow), HTML, Cols);
                        }
                        //iCurCol++;
                    }
                    ResultCollection.add(dtTable);
                }
                break;
            }
        }
        return ResultCollection;
    }//subTableWithHtmlNode6

    /**
     * Method of support for UniversalExtractor(); , version : 6 (2015-04-15).
     *
     * @param node the JSoup Node to Inspect.
     * @param HTML if true get the HTML tag not only the value.
     * @param Cols the List of String of the Columns of the Table.
     * @return the List of String of the value for the specified Columns.
     */
    private static List<String> Extractor6(org.jsoup.nodes.Element node, boolean HTML, List<String> Cols) {
        String[] goodTag = new String[]{"A", "IMG", "P", "SPAN", "CAPTION", "DIV", "DD",
                "DT", "DL", "LI", "DIV", "BR", "STRONG", "B"};//...possible new entry -> STRONG,#TEXT
        String[] goodAttr = new String[]{"SRC", "HREF", "ID", "NAME", "VALUE", "TITLE",
                "ALT", "ONCLICK"}; //..add more attributes , by use case
        String Testo;
        org.jsoup.nodes.Element child;
        //if is a tag with some information to extract
        if (node.children().size() == 1 &&
                !Arrays.asList(goodTag).contains(node.children().first().tagName().toUpperCase())){
            return Cols; //..do nothing
        }
        //...o the son is unique or is a text String...
        else if (node.children().size() == 1 &&
                !Objects.equals(node.children().first().tagName().toUpperCase(), "#TEXT")) {
            child = node.child(0);
        }
        //...has not sons
        else {
            child = node;
        }
        if (!HTML)//html == false
        {
            Testo = clean(child.ownText());
            if (Testo != null) Cols.add(Testo);
            if (!isFilterAttr() && Arrays.asList(goodTag).contains(child.tagName().toUpperCase())) {
                Testo = "";
                boolean check = false;
                for (String attr : goodAttr) {
                    //org.jsoup.nodes.Attribute aaa = new  org.jsoup.nodes.Attribute(goodAttr,);
                    if ((child.attributes().hasKey(attr) || (child.attributes().hasKey(attr.toLowerCase())))
                            && clean(child.attr(attr)) != null) {
                        Testo = Testo + "[" + attr + "=" + clean(child.attr(attr)) + "] ";
                    } else if ((child.attributes().hasKey(attr) || (child.attributes().hasKey(attr.toLowerCase())))
                            && Objects.equals(Testo, "") && Arrays.asList(goodAttr).contains(attr)) {
                        check = true;
                    }
                }
                Testo = clean(Testo);
                if (check && clean(Testo) == null) Cols.add((Testo == null) ? "" : Testo); //Testo = "";
                else if (Testo != null) Cols.add(Testo);
            }
            //.Equals("IMG")...Se il filtro degli attributi è true ma sappiamo
            // che vi è un qualcosa di utile l'unico caso trovato sono le immagini
            else if ("IMG".contains(child.tagName().toUpperCase())) {
                Testo = "";
                Cols.add(Testo);
            }
        } else {
            Testo = clean(child.outerHtml());
            if (Testo != null) Cols.add(Testo);
        }
        return Cols;
    }

    /**
     * Method to convert a HTML File to a JSoup Document.
     * @param file the HTML File to convert.
     * @param baseUri the String url for the JSoup Document.
     * @return the JSoup Document.
     */
    public static org.jsoup.nodes.Document toJsoupDocument(File file, String baseUri){
        //File input = new File("/tmp/input.html");
        try {
            return org.jsoup.Jsoup.parse(file, "UTF-8", baseUri);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * Method to convert a HTML String content or a url address to a Web  Page to a JSoup Document.
     * @param htmlOrUri the html content or the url of the web page.
     * @return the JSoup Document.
     */
    public static org.jsoup.nodes.Document toJsoupDocument(String htmlOrUri) {
        org.jsoup.nodes.Document htmldoc = new Document("");
        if (UrlValidator.getInstance().isValid(htmlOrUri)) {
            try {//try 1
                //htmldoc = org.jsoup.Jsoup.connect(htmlOrUri).get();
                htmldoc = Jsoup.connect(htmlOrUri)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
            }catch(Exception e11) {
                try {//try 2 http://stackoverflow.com/questions/6581655/jsoup-useragent-how-to-set-it-right
                    Connection.Response response = Jsoup.connect(htmlOrUri)
                            .ignoreContentType(true)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                            .referrer("http://www.google.com")
                            .timeout(12000)
                            .followRedirects(true)
                            .execute();
                    htmldoc = response.parse();

                } catch (Exception e) {
                    try {
                        String html = HttpUtilities.executeHTTPGetRequest(htmlOrUri);
                        htmldoc = org.jsoup.Jsoup.parse(html);
                    } catch (Exception e1) {
                        try {
                            htmldoc = org.jsoup.Jsoup.connect(htmlOrUri)
                                    .data("query", "Java")
                                    .userAgent("Mozilla")
                                    .cookie("auth", "token")
                                    .timeout(3000)
                                    .post();
                        } catch (IOException e2) {
                            try {
                                htmldoc = Document.createShell(htmlOrUri);
                            } catch (Exception e3) {
                                logger.error(e3.getMessage(), e3);
                            }
                        }
                    }
                }
            }
        } else {
            try {
                htmldoc = org.jsoup.Jsoup.parse(htmlOrUri);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return htmldoc;
    }

    /**
     * Method to convert a Hurl address to a Web Page to a JSoup Document.
     * @param url the url of the web page.
     * @return the JSoup Document.
     */
    public static org.jsoup.nodes.Document toJsoupDocument(URL url) {
        return toJsoupDocument(url.toString());
    }

    /**
     * Method to convert a InputStream to a JSoup Document.
     * @param ins the Stream of the HTML File to convert.
     * @param baseUri the String url for the JSoup Document.
     * @return the JSoup Document.
     * @throws java.io.IOException throw if any error is occurred.
     */
    public static org.jsoup.nodes.Document toJsoupDocument(InputStream ins,String baseUri) 
            throws IOException {
        return org.jsoup.Jsoup.parse(ins, "UTF-8", baseUri);
    }


    /*private List<Map<String, String>> parseDataSet(String xml) {
        List<Map<String, String>> maps = new ArrayList<>();
        Map<String, String> map;
        org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(xml);
        org.jsoup.select.Elements rows = doc.getElementsByTag("Table");
        for (org.jsoup.nodes.Element row : rows) {
            org.jsoup.select.Elements cells = row.children();
            map = new HashMap<>();
            for (org.jsoup.nodes.Element cell : cells) {
                map.put(cell.tagName(), cell.html());
            }
            maps.add(map);
        }
        return maps;
    }*/

        /*public DataSet convert(String xml){
            DataSet dataSet=new DataSet();
            Document doc=Jsoup.parse(xml);
            Column column;
            Elements headerEls=doc.select(getColumnSelector());
            String type;
            int index;
            for (  Element rowEl : headerEls) {
              type=rowEl.attr("type");
              index=type.indexOf(":");
              column=new Column(rowEl.attr("name").toLowerCase(),index != -1 ? type.substring(index + 1) : type);
              dataSet.addColumn(column);
              Formater<?> formater=this.getFormaters().get(column.getType());
              if (formater != null) {
                column.setFormater(formater);
              }
            }
        }*/

    public static String getContentOfFooter(org.jsoup.nodes.Document doc) {
        String[] specialFooter = new String[]{"footer",
                "div[id$=footer]"/*div id ending with footer*/, "div[id^=footer]"/*div id starting with footer*/};
        return getContentOfTag(doc, specialFooter, false);
    }

    public static String getContentOfHeader(org.jsoup.nodes.Document doc) {
        String[] specialHeader = new String[]{"head", "title",
                "div[id$=header]"/*div id ending with footer*/, "div[id^=header]"/*div id starting with footer*/};
        return getContentOfTag(doc, specialHeader, false);
    }

    public static String getContentOfTitle(org.jsoup.nodes.Document doc) {
        String[] specialHeader = new String[]{"title"};
        return getContentOfTag(doc, specialHeader, true);
    }

    public static String getContentOfTag(org.jsoup.nodes.Document doc, String[] special, boolean first) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        org.jsoup.select.Elements sFooter;
        int i = 0;
        int j = 0;
        while (i < special.length) {
            sFooter = doc.select(special[i]);//div id ending with footer
            while (j < sFooter.size()) {
                sb.append(sFooter.get(j).outerHtml()).append(System.getProperty("line.separator"));
                if (first) break;
                else j++;
            }
            i++;
            if (!sb.toString().isEmpty()) break;
        }
        return sb.toString();
    }

    public static String getContent(String url) {
        String htmlContent;
        try {
            org.jsoup.nodes.Document doc = toJsoupDocument(url);
            htmlContent = doc.outerHtml();
        }catch(Exception e){
            logger.error("JSOUP can't convert the url:" + url + " to a string maybe the " +
                    "web page not exists anymore or can't be reach",e);
            return null;
        }
        return htmlContent;
    }

    public static String getContent(URL url) {
        return getContent(url.toString());
    }

}
    

 

