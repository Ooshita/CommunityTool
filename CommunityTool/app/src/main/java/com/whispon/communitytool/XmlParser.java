 package com.whispon.communitytool;

 import java.io.IOException;
 import java.io.InputStream;
 import java.net.HttpURLConnection;
 import java.net.ProtocolException;
 import java.net.URL;
 import java.nio.charset.MalformedInputException;
 import java.util.ArrayList;

 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.parsers.ParserConfigurationException;

 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.NodeList;
 import org.xml.sax.SAXException;


public class XmlParser {

    /**
     * @param args
     */
    static ArrayList<String> titleList = new ArrayList<String>();

    public static void main(String[] args) throws MalformedInputException,
            ProtocolException, IOException {
    }

    private static Document getDocumet(InputStream is) throws SAXException,
            IOException, ParserConfigurationException {

        DocumentBuilder docbuilder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        return docbuilder.parse(is);

    }

    public static void getXml() throws MalformedInputException,
            ProtocolException, IOException {
        URL url = new URL("http://news.yahoo.co.jp/pickup/domestic/rss.xml");
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestMethod("GET");
        urlConn.setInstanceFollowRedirects(false);
        urlConn.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");

        urlConn.connect();

        Document doc = null;
        try {
            doc = getDocumet(urlConn.getInputStream());
        } catch (SAXException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        // ルートの要素名になっている子ノードを取得する
        Element root = doc.getDocumentElement();
        //System.out.println("ルート要素名：" + root.getTagName());

        // 各ノードリストを取得
        NodeList nodeList = root.getElementsByTagName("item");
        //System.out.println("ノードリストの数は：" + nodeList.getLength());
        //titleListを初期化する。
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element)nodeList.item(i);
            //System.out.println(getChildren(element,"title"));
            //titleタグのなかをtitleListにaddする
            titleList.add(getChildren(element,"title"));
        }
        //リストのすべてを表示する
        //System.out.println(titleList);
        urlConn.disconnect();
    }

    public static void getSelectXml(String URL) throws MalformedInputException,
            ProtocolException, IOException {
        URL url = new URL(URL);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestMethod("GET");
        urlConn.setInstanceFollowRedirects(false);
        urlConn.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");

        urlConn.connect();

        Document doc = null;
        try {
            doc = getDocumet(urlConn.getInputStream());
        } catch (SAXException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        // ルートの要素名になっている子ノードを取得する
        Element root = doc.getDocumentElement();
        //System.out.println("ルート要素名：" + root.getTagName());

        // 各ノードリストを取得
        NodeList nodeList = root.getElementsByTagName("item");
        //System.out.println("ノードリストの数は：" + nodeList.getLength());
        //タイトルリストを初期化する
        titleList.clear();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element)nodeList.item(i);
            //System.out.println(getChildren(element,"title"));
            //titleタグのなかをtitleListにaddする
            titleList.add(getChildren(element,"title"));
        }
        //リストのすべてを表示する
        //System.out.println(titleList);
        urlConn.disconnect();
    }




    public static ArrayList<String> getList() throws MalformedInputException,
    ProtocolException, IOException {
        return titleList;
    }

    /**
     * 指定されたエレメントから子要素の内容を取得。
     *
     * @param   element 指定エレメント
     * @param   tagName 指定タグ名
     * @return  取得した内容
     */
    public static String getChildren(Element element, String tagName) {
        NodeList list = element.getElementsByTagName(tagName);
        Element cElement = (Element)list.item(0);
        return cElement.getFirstChild().getNodeValue();
    }

}