/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmljoiner.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.axiom.om.*;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.impl.llom.OMElementImpl;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.apache.commons.collections.map.HashedMap;
import org.jaxen.JaxenException;

import xmljoiner.utils.Commons;

/**
 *
 * @author jvanek
 */
public class Joiner {

    public static final String VERSION = "2.1";
    private int logging = 0;
    private String headerXpath = null;
    private String contentXpath = "/*";
    private String footerXpath = null;
    private final List<File> input = new ArrayList<File>();
    private File output = null;
    private String delimiter1 = "_part_";
    private String delimiter2 = "";
    private String ignoreUri = null;
    private int sort = 0;
    private boolean checkHeaders = false;
    private final int SIZER = 1;

    public Joiner() {
    }

    public void openEleemtnsList(List<OMElement> currentParents, StringBuilder sb) {
        for (int j = currentParents.size() - 1; j >= 0; j--) {
            OMElement oMElement = currentParents.get(j);
            sb.append(writeOpeningElement(oMElement));
        }
    }

    public String closeEleemtnsList(List<OMElement> currentParents) {
        StringBuilder sb = new StringBuilder();
        closeEleemtnsList(currentParents, sb);
        return sb.toString();
    }

    public void closeEleemtnsList(List<OMElement> currentParents, StringBuilder sb) {
        for (OMElement oMElement : currentParents) {
            sb.append(writeClosingElement(oMElement));
        }
    }

    public void proceed() throws Exception {
        loclalLog("=======PROCESSING=======");
        if (output == null) {
            proceed(System.out);
        } else {
            loclalLog("writing to " + output.getAbsolutePath());
            proceed(new FileOutputStream(output));
        }

    }

    public void proceed(OutputStream is) throws XMLStreamException, JaxenException, UnsupportedEncodingException, IOException {

        Writer os = new OutputStreamWriter(is, "utf-8");
        long totalContentSize = 0;
        long totalChunksCount = 0;

        FirstDocKeeper fdc = null;
        //ElementListKeeper elk = new ElementListKeeper();

        //there will be headers, all contntSSS and footer. Saved at the end.
        List<OMElement> all = new LinkedList<OMElement>();

        //iterate through input files
        for (int k = 0; k < input.size(); k++) {
            File file = input.get(k);

            XMLStreamReader parser = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(file));

//create the builder
            StAXOMBuilder builder = new StAXOMBuilder(parser);

//get the root element (in this case the envelope)
            OMDocument doc = builder.getDocument();
            OMElement root = builder.getDocumentElement();

            long headerSize = 0;
            long footerSize = 0;
            long contentSize = 0;
            List<OMElement> headerList = new ArrayList<OMElement>(0);
            List<OMElement> footerList = new ArrayList<OMElement>(0);
            List<OMElement> contentList = new ArrayList<OMElement>(0);

            if (headerXpath != null) {
                AXIOMXPath xpathExpression = new AXIOMXPath(headerXpath);
                if (ignoreUri != null) {
                    if (ignoreUri.contains("ALL")) {
                        applyAllnamespaces(xpathExpression, root);
                    }
                    if (ignoreUri.replaceAll("\\s*ALL\\s*", " ").trim().length() > 2) {
                        applySelectedNamespaces(xpathExpression, ignoreUri.replaceAll("\\s*ALL\\s*", " ").trim(), root, doc.getOMFactory());
                    }
                }
                List nodeList = xpathExpression.selectNodes(root);

                if (nodeList.isEmpty()) {
                    loclalLog("warning: header list have 0 nodes!");
                } else {
                    loclalLog("header list have " + nodeList.size() + " nodes!");
                    headerList = new ArrayList<OMElement>(nodeList.size());
                    for (Iterator it = nodeList.iterator(); it.hasNext();) {
                        Object object = it.next();
                        if (object instanceof OMElement) {
                            headerSize += SIZER * ((OMElement) object).toString().length();
                            headerList.add((OMElement) object);
                        }

                    }
                    loclalLog("header size is " + headerSize + " bytes!");
                }
            }
            if (footerXpath != null) {
                AXIOMXPath xpathExpression = new AXIOMXPath(footerXpath);
                if (ignoreUri != null) {
                    if (ignoreUri.contains("ALL")) {
                        applyAllnamespaces(xpathExpression, root);
                    }
                    if (ignoreUri.replaceAll("\\s*ALL\\s*", " ").trim().length() > 2) {
                        applySelectedNamespaces(xpathExpression, ignoreUri.replaceAll("\\s*ALL\\s*", " ").trim(), root, doc.getOMFactory());
                    }
                }
                List nodeList = xpathExpression.selectNodes(root);
                if (nodeList.isEmpty()) {
                    loclalLog("warning: footer list have 0 nodes!");
                } else {
                    loclalLog("footer list have " + nodeList.size() + " nodes!");
                    footerList = new ArrayList<OMElement>(nodeList.size());
                    for (Iterator it = nodeList.iterator(); it.hasNext();) {
                        Object object = it.next();
                        if (object instanceof OMElement) {
                            footerSize += SIZER * ((OMElement) object).toString().length();
                            footerList.add((OMElement) object);
                        }

                    }
                    loclalLog("footer size is " + footerSize + " bytes!");
                }
            }

            if (contentXpath != null) {
                AXIOMXPath xpathExpression = new AXIOMXPath(contentXpath);
                if (ignoreUri != null) {
                    if (ignoreUri.contains("ALL")) {
                        applyAllnamespaces(xpathExpression, root);
                    }
                    if (ignoreUri.replaceAll("\\s*ALL\\s*", " ").trim().length() > 2) {
                        applySelectedNamespaces(xpathExpression, ignoreUri.replaceAll("\\s*ALL\\s*", " ").trim(), root, doc.getOMFactory());
                    }
                }
                List nodeList = xpathExpression.selectNodes(root);
                if (nodeList.isEmpty()) {
                    loclalLog("warning: content list have 0 nodes!");
                } else {
                    loclalLog("content list have " + nodeList.size() + " nodes!");
                    contentList = new ArrayList<OMElement>(nodeList.size());
                    for (Iterator it = nodeList.iterator(); it.hasNext();) {
                        Object object = it.next();
                        if (object instanceof OMElement) {
                            contentSize += SIZER * ((OMElement) object).toString().length();
                            contentList.add((OMElement) object);
                        }

                    }
                    loclalLog("content size is " + contentSize + " bytes!)");
                }
            }

            if (fdc == null) {
                //save first document for checking
                fdc = new FirstDocKeeper();
                fdc.setDocument(doc);
                fdc.setRoot(root);
                fdc.setHeaderSize(headerSize);
                fdc.setFooterSize(footerSize);
                fdc.setContentSize(contentSize);
                fdc.setHeader(headerList);
                fdc.setFooter(footerList);
                fdc.setContent(contentList);
                //write head
                //flush processing instructions of document
                // os.write(writeEncodig(doc.getCharsetEncoding()));
                os.write(writeVersion(doc.getXMLVersion()));
                //os.write(writeEncodigAndVersion(doc.getCharsetEncoding(),doc.getXMLVersion()));
                Iterator children = doc.getChildren();
                while (children.hasNext()) {
                    OMNode node = (OMNode) children.next();

                    if (node instanceof OMProcessingInstruction) {
                        os.write(writeProcessingInstruction((OMProcessingInstruction) node));
                    }
                }/*
                 //write opening root;
                 //root
                 os.write(writeOpeningElement(root));
                 //write headers
                 //header with parents
                 os.write(writeBufferWithParents(headerList, root));
                 now all is saved at the end.
                 */

                loclalLog("adding header");
                all.addAll(headerList);
                os.flush();

            }

            if (checkHeaders) {
                //check headers;
                loclalLog("checking heads: ");
                loclalLog("loaded encoding: " + doc.getCharsetEncoding());
                loclalLog("expected encoding: " + fdc.getDocument().getCharsetEncoding());
                if (doc.getCharsetEncoding().equals(fdc.getDocument().getCharsetEncoding())) {
                    loclalLog("ok");
                } else {
                    loclalLog("WARNING encodings differs in chunk: " + k);
                }
                loclalLog("loaded xml-version: " + doc.getXMLVersion());
                loclalLog("expected xml-version: " + fdc.getDocument().getXMLVersion());
                if (doc.getXMLVersion().equals(fdc.getDocument().getXMLVersion())) {
                    loclalLog("ok");
                } else {
                    loclalLog("WARNING xml-versions differs in chunk: " + k);
                }
                loclalLog("processing instructions: ");

                {
                    Iterator children2 = fdc.getDocument().getChildren();
                    OMNode node2 = (OMNode) children2.next();
                    loclalLog("expeced: " + ((OMProcessingInstruction) node2).getTarget() + " " + ((OMProcessingInstruction) node2).getValue());
                }
                Iterator children = doc.getChildren();
                while (children.hasNext()) {
                    OMNode node = (OMNode) children.next();

                    if (node instanceof OMProcessingInstruction) {
                        loclalLog("loaded " + ((OMProcessingInstruction) node).getTarget() + " " + ((OMProcessingInstruction) node).getValue());

                        Iterator children2 = fdc.getDocument().getChildren();
                        boolean matched = false;
                        while (children2.hasNext()) {

                            OMNode node2 = (OMNode) children2.next();
                            if (((OMProcessingInstruction) node2).getTarget().equals(((OMProcessingInstruction) node).getTarget())
                                    && ((OMProcessingInstruction) node2).getValue().equals(((OMProcessingInstruction) node).getValue())) {
                                matched = true;
                                break;
                            }

                        }
                        loclalLog("have match: " + matched);
                    }

                }
                if (headerXpath != null) {
                    loclalLog("checking headers: ");

                    loclalLog("loaded headers size: " + headerSize);
                    loclalLog("expected headers size: " + fdc.getHeaderSize());
                    if (headerSize == fdc.getHeaderSize()) {
                        loclalLog("are equals - ok");
                    } else {
                        loclalLog("WARNING differs");
                    }
                    loclalLog("loaded headers count: " + headerList.size());
                    loclalLog("expected headers count: " + fdc.getHeader().size());
                    if (headerList.size() == fdc.getHeader().size()) {
                        loclalLog("are equals - ok");
                        for (int i = 0; i < headerList.size(); i++) {
                            OMElement e1 = headerList.get(i);

                            OMElement e2 = fdc.getHeader().get(i);

                            if (e1.toString().equals(e2.toString())) {
                                loclalLog("  match");
                            } else {
                                loclalLog("  WARNING - unmatching header");

                            }

                        }
                    } else {
                        loclalLog("WARNING differs");
                    }

                }
                if (footerXpath != null) {
                    loclalLog("checking footers: ");

                    loclalLog("loaded footer size: " + footerSize);
                    loclalLog("expected footer size: " + fdc.getFooterSize());
                    if (footerSize == fdc.getFooterSize()) {
                        loclalLog("are equals - ok");
                    } else {
                        loclalLog("WARNING differs");
                    }
                    loclalLog("loaded footers count: " + footerList.size());
                    loclalLog("expected footers count: " + fdc.getFooter().size());
                    if (footerList.size() == fdc.getFooter().size()) {
                        loclalLog("are equals - ok");
                        for (int i = 0; i < footerList.size(); i++) {
                            OMElement e1 = footerList.get(i);

                            OMElement e2 = fdc.getFooter().get(i);

                            if (e1.toString().equals(e2.toString())) {
                                loclalLog("  match");
                            } else {
                                loclalLog("  WARNING - unmatching header");

                            }

                        }
                    } else {
                        loclalLog("WARNING differs");
                    }
                }

            }
            if (checkHeaders) {
                loclalLog("loaded content count: " + contentList.size());
                loclalLog("unexpected content count: " + fdc.getContent().size());
                if (contentList.size() == fdc.getContent().size()) {
                    loclalLog("are equals - quite suspicious");

                } else {
                    loclalLog("ok-  differs");
                }
                loclalLog("loaded content size: " + contentSize);
                loclalLog("unexpected content size: " + fdc.getContentSize());
                if (contentSize == fdc.getContentSize()) {
                    loclalLog("are equals - VERY suspicious");

                } else {
                    loclalLog("ok-  differs");
                }
            }

            loclalLog("adding chunk " + k + " size " + contentSize + "bytes in " + contentList.size() + " chunks from total " + input.size() + " files");

            //write part, surrounding elements?
            //parents of contents
            //opendˇˇˇˇ
            //contents
            //os.write(writeBufferWithParents(contentList, root, elk));
            all.addAll(contentList);

            totalContentSize += contentSize;
            totalChunksCount += contentList.size();
            loclalLog("addded chunk " + k + " total output size " + totalContentSize + "bytes in " + totalChunksCount + " chunks from total " + input.size() + " files");

        }
        loclalLog("header+contents+footer: " + (totalContentSize + fdc.getFooterSize() + fdc.getHeaderSize()) + "b");
        /*   //close parents
         if (elk.list != null) {
         os.write(closeEleemtnsList(elk.list));
         }
         //write footer
         //footer with parents
         os.write(writeBufferWithParents(fdc.getFooter(), fdc.getRoot()));
         //close root
         //root
         os.write(writeClosingElement(fdc.getRoot()));
         */
        all.addAll(fdc.getFooter());
        loclalLog("writeing complete tree");
        os.write(writeBufferWithParents(all));
        //close stream
        os.flush();
        if (output != null) {
            os.close();
            is.close();
        }
        loclalLog("saved");
    }

    public void loadConfig(URL uRL) {
        InputStream is = null;
        try {
            is = uRL.openStream();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (is == null) {
            loclalLog("config file do not exists: " + uRL.toString());
            return;
        }
        try {
            loclalLog("reading: " + uRL.toString());
            loadConfig(is);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void loadConfig(File file) {
        if (!file.exists()) {
            loclalLog("config file do not exists: " + file.getAbsolutePath());
        }
        try {
            loclalLog("reading: " + file.getAbsolutePath());
            loadConfig(new FileInputStream(file));
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void loadConfig(InputStream is) throws IOException {

        loadConfig(new InputStreamReader(is, "utf-8"));
    }

    public void loadConfig(Reader r) throws IOException {
        BufferedReader br = new BufferedReader(r);
        try {
            while (true) {
                String s = br.readLine();
                if (s == null) {
                    break;
                }
                try {
                    Commons.proceedArg(this, Commons.paarseCommandLineArgWithOrig(s));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            br.close();
        }

    }

    public void addInput(File file) {
        if (file.exists()) {
            this.input.add(file.getAbsoluteFile());
            loclalLog("input file added: " + file.getAbsolutePath());
        } else {

            loclalLog("input file not exist. ignored: " + file.getAbsolutePath());
        }

    }

    public void setOutputFile(File file) {
        this.output = file.getAbsoluteFile();
        loclalLog("output dir setted: " + file.getAbsolutePath());
    }

    public void setHeaderXpath(String string) {
        this.headerXpath = string;
        loclalLog("header xpath setted: " + headerXpath);
    }

    public void setContentXpath(String string) {
        this.contentXpath = string;
        loclalLog("content xpath setted: " + contentXpath);
    }

    public void setFooterXpath(String string) {
        this.footerXpath = string;
        loclalLog("footer xpath setted: " + footerXpath);
    }

    public void checkIntegrity() {

        switch (logging) {
            case 0:
                loclalLog("logging: errorstream");
                break;
            case 1:
                loclalLog("logging: std-out");
                break;
            default:
                loclalLog("logging: silent");
                break;
        }
        loclalLog("delimiter1 set " + delimiter1);
        loclalLog("delimiter2 set " + delimiter2);
        if (headerXpath == null) {
            loclalLog("header xpath not set");
        } else {
            loclalLog("header xpath: " + headerXpath);
        }
        if (contentXpath == null) {
            loclalLog("content xpath not set");
        } else {
            loclalLog("content xpath: " + contentXpath);
        }
        if (footerXpath == null) {
            loclalLog("footer xpath not set");
        } else {
            loclalLog("footer xpath: " + footerXpath);
        }

        if (input.isEmpty()) {
            throw new IllegalArgumentException("no input fileas");
        } else {
            loclalLog(input.size() + " input files ");
        }

        if (output == null) {
            loclalLog("output not set, used std-out");
        } else {
            if (output.getName().trim().length() == 0) {
                File rabbit = input.get(0);
                File dir = rabbit.getParentFile();
                String name = rabbit.getName();
                name = name.replaceAll(delimiter1 + "[0-9]*" + delimiter2, "");
                output = new File(dir, name);
            }
            if (output.isDirectory()) {
                if (!output.exists()) {
                    throw new IllegalArgumentException("output dir: " + output.getAbsolutePath() + "does NOT exists!");
                }
                String name = input.get(0).getName();
                name = name.replaceAll(delimiter1 + "[0-9]*" + delimiter2, "");
                output = new File(output, name);
            }
            loclalLog("output file: " + output);
            if (output.exists()) {
                loclalLog("output file: " + output.getAbsolutePath() + " exists!");
            }
        }

        switch (sort) {
            case 1:
                loclalLog("input filesnow sorting alphabeticaly");
                Collections.sort(input, new Comparator<File>() {

                    public int compare(File t, File t1) {
                        return t.getName().compareTo(t1.getName());
                    }
                });
                break;
            case 2:
                loclalLog("input filesnow sorting numericly");
                Collections.sort(input, new Comparator<File>() {

                    public int compare(File t, File t1) {
                        try {
                            Long l1 = new Long(t.getName().replaceAll("\\D*", ""));
                            Long l2 = new Long(t1.getName().replaceAll("\\D*", ""));
                            return (int) (l1 - l2);
                        } catch (Exception ex) {
                            loclalLog("uncomparables: " + t.getName() + ", " + t1.getName());
                            return 0;
                        }

                    }
                });
                break;
            default:
                loclalLog("input files not sorted");

        }
        for (File file : input) {
            loclalLog(file.getAbsolutePath());
        }

        if (checkHeaders) {
            loclalLog("headers will be checked");
        } else {
            loclalLog("headers will NOT be checked");
        }

    }

    public void setLogging(int integer) {
        this.logging = integer;
        loclalLog("logging set to: " + logging);
    }

    public int getLogging() {
        return logging;
    }

    private void loclalLog(String string) {
        Commons.log(string, logging);
    }

    public void setDelimiter1(String value) {
        this.delimiter1 = value;
    }

    public void setDelimiter2(String value) {
        this.delimiter2 = value;
    }

    public String writeOpeningElement(OMElement root) {
        StringBuilder sb = new StringBuilder("<");

        {
            String nmsc = root.getQName().getPrefix();
//        if (nmsc==null || nmsc.trim().equals("")){
//            nmsc=root.getQName().getNamespaceURI();
//        }
            if (nmsc == null || nmsc.trim().equals("")) {
                nmsc = "";
            } else {
                nmsc += ":";
            }
            sb.append(nmsc).append(root.getQName().getLocalPart()).append(" ");
        }

        OMNamespace def = root.getDefaultNamespace();
        if (def != null) {
            Object parent = root.getParent();
            if (!(parent instanceof OMElement)) {
                parent = null;
            }
            if (parent != null && ((OMElement) parent).getDefaultNamespace() != null && def.getNamespaceURI().equals(((OMElement) parent).getDefaultNamespace().getNamespaceURI())) {
                //parent have same default uri
            } else {
                sb.append("xmlns='").append(def.getNamespaceURI()).append("'");
            }
        }

        Iterator ii = root.getAllDeclaredNamespaces();
        for (; ii.hasNext();) {
            OMNamespace o = (OMNamespace) ii.next();
            if (o.getPrefix() == null || o.getPrefix().trim().equals("")) {
                continue;
            }
            boolean foriden = false;
            if (root.getParent() != null && root.getParent() instanceof OMElement) {
                foriden = isInParent(o, ((OMElement) root.getParent()));
            }
            if (!foriden) {
                sb.append(" xmlns:").append(o.getPrefix());
                String ch = "'";
                if (o.getNamespaceURI().contains("'")) {
                    ch = "\"";
                }
                sb.append("=").append(ch);
                sb.append(o.getNamespaceURI());
                sb.append(ch);
            }

        }

        Iterator i = root.getAllAttributes();
        for (; i.hasNext();) {
            OMAttribute o = (OMAttribute) i.next();

            String nmsc = o.getQName().getPrefix();
//        if (nmsc==null || nmsc.trim().equals("")){
//            nmsc=root.getQName().getNamespaceURI();
//        }
            if (nmsc == null || nmsc.trim().equals("")) {
                nmsc = "";
            } else {
                nmsc += ":";
            }

            sb.append(nmsc).append(o.getQName().getLocalPart());
            String ch = "'";
            if (o.getAttributeValue().contains("'")) {
                ch = "\"";
            }
            sb.append("=").append(ch);
            sb.append(o.getAttributeValue());
            sb.append(ch).append(" ");

        }

        sb.append(">");

        return sb.toString();
    }

    private boolean isInParent(OMNamespace n1, OMElement oMElement) {
        Iterator ii = oMElement.getAllDeclaredNamespaces();
        for (; ii.hasNext();) {
            OMNamespace n2 = (OMNamespace) ii.next();
            if (n1.getPrefix().equals(n2.getPrefix()) && n1.getNamespaceURI().equals(n2.getNamespaceURI())) {
                return true;
            }
        }
        return false;
    }

    public String writeClosingElement(OMElement root) {
        StringBuilder sb = new StringBuilder("</");
        String p = root.getQName().getPrefix();
        if (p != null && !p.trim().equals("")) {
            sb.append(p).append(":");
        }

        sb.append(root.getQName().getLocalPart());
        sb.append(">");

        return sb.toString();
    }

    private String writeBufferWithParents(List<OMElement> elist) {
        return writeBufferWithParents(elist, null);
    }

    private String writeBufferWithParents(List<OMElement> elist, OMElement root) {
        long contolSize = 0;
        List<OMElement> parentList = null;

        StringBuilder sb = new StringBuilder();

        for (OMElement el : elist) {
            contolSize += SIZER * (el).toString().length();

            List<OMElement> currentParents = getParents(el/*, root*/);
//            String s="";
//            for (int j = 0; j < currentParents.size(); j++) {
//                OMElement oMElement = currentParents.get(j);
//                s=s+j+": "+oMElement.getLocalName()+" ";
//
//            }
//            loclalLog(s);

            if (parentList == null) {
                openEleemtnsList(currentParents, sb);
                parentList = currentParents;
            } else {
//                boolean compared = compareElementListsByName(currentParents, parentList);
//                if (!compared)
                {
                    List<OMElement> discarded = new LinkedList<OMElement>();
//                     System.out.println("XXXXXXXXXXXXXX");
//                     for (int j = 0; j < parentList.list.size(); j++) {
//                        OMElement oMElement = parentList.list.get(j);
//                         System.out.println("pl"+j+" "+oMElement.getLocalName());
//                    }
//                         for (int j = 0; j < currentParents.size(); j++) {
//                        OMElement oMElement = currentParents.get(j);
//                         System.out.println("cl"+j+" "+oMElement.getLocalName());
//                    }
                    int j = parentList.size() - 1;
                    int jj = currentParents.size() - 1;
                    while (true) {
                        if (j < 0) {
                            break;
                        }
                        if (jj < 0) {
                            break;
                        }
                        if (parentList.get(j).getQName().toString().equals(currentParents.get(jj).getQName().toString())) {
                            discarded.add(currentParents.get(jj));
                            parentList.remove(j);
                            currentParents.remove(jj);
                            //j++;
                            //jj++;
                        } else {
                            break;
                        }
                        j--;
                        jj--;

                    }
//                     for (int w = 0; w < discarded.size(); w++) {
//                        OMElement oMElement = discarded.get(w);
//                        System.out.println("dl"+w+" "+oMElement.getLocalName());
//                    }
                    closeEleemtnsList(parentList, sb);
                    parentList = currentParents;
                    openEleemtnsList(parentList, sb);

                    for (int k = discarded.size() - 1; k >= 0; k--) {
                        OMElement e = discarded.get(k);
                        parentList.add(e);
                    }
//                    for (int w = 0; w < parentList.list.size(); w++) {
//                        OMElement oMElement = parentList.list.get(w);
//                         System.out.println("pl"+w+" "+oMElement.getLocalName());
//                    }
                }
            }
            //          loclalLog("writeing "+el.getLocalName());
            sb.append(el.toString());
        }

        if (parentList != null) {
            closeEleemtnsList(parentList, sb);
        }
        loclalLog("buffered: " + contolSize + "b ");
        return sb.toString();
    }
//from element to root, both exclused

    private List<OMElement> getParents(OMElement el, OMElement root) {
        List<OMElement> result = new LinkedList<OMElement>();

        OMElement parent = el;
        while (true) {
            try {
                parent = (OMElement) parent.getParent();
            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
            if (parent.getQName().toString().equals(root.getQName().toString())) {
                break;
            }
            result.add(parent);

        }

        return result;
    }

    //from element to root, root included, eleemnt excluded
    private List<OMElement> getParents(OMElement el) {
        List<OMElement> result = new LinkedList<OMElement>();

        OMElement parent = el;
        while (true) {

            Object oparent = parent.getParent();
            if (oparent instanceof OMElement) {
                parent = (OMElement) oparent;
            } else {
                break;
            }

            result.add(parent);

        }

        return result;
    }

//    private String writeBufferWithParents(List<OMElement> elist, OMElement root) {
//        List<OMElement> parentList = null;
//
//        StringBuilder sb = new StringBuilder();
//
//
//        for (int i = 0; i < elist.size(); i++) {
//            OMElement el = elist.get(i);
//
//            List<OMElement> currentParents = getParents(el, root);
//            if (parentList == null) {
//                openEleemtnsList(currentParents, sb);
//                parentList = currentParents;
//            } else {
//                boolean compared = compareElementListsByName(currentParents, parentList);
//                if (!compared) {
//
//                    closeEleemtnsList(parentList, sb);
//                    parentList = currentParents;
//                    openEleemtnsList(parentList, sb);
//
//
//                }
//            }
//            sb.append(el.toString());
//
//        }
//
//        if (parentList != null) {
//            closeEleemtnsList(parentList, sb);
//        }
//        return sb.toString();
//    }
    private String writeEncodig(String charsetEncoding) {
        return "<?xml encoding='" + charsetEncoding + "' ?>";
    }

    private String writeVersion(String V) {
        return "<?xml version='" + V + "' ?>";
    }

    private String writeEncodigAndVersion(String charsetEncoding, String V) {
        return "<?xml encoding='" + charsetEncoding + "'  version='" + V + "' ?>";
    }

    private String writeProcessingInstruction(OMProcessingInstruction i) {
        return "<?" + i.getTarget() + " " + i.getValue() + "?>";
    }

    public void setSort(int i) {
        this.sort = i;
        switch (sort) {
            case 1:
                loclalLog("sorting set to alhabetical");
                break;
            case 2:
                loclalLog("sorting set to numerical");
                break;
            default:
                loclalLog("sorting disabled");
                break;
        }
    }

    public void setCheckHeaders(boolean b) {
        checkHeaders = true;
    }

    public void setIgnoreUri(String value) {
        ignoreUri = value;
    }

    public void applyAllnamespaces(AXIOMXPath xpathExpression, OMElement root) throws JaxenException {
        loclalLog("gathering namespaces");
        List<OMElement> l = gatherNamespaces(root);

        for (OMElement ee : l) {
            xpathExpression.addNamespaces(ee);

        }
        try {
            Map a = xpathExpression.getNamespaces();
            Set s = a.entrySet();
            for (Object object : s) {
                HashedMap.Entry he = (Entry) object;
                String key = (String) he.getKey();
                String value = (String) he.getValue();
                loclalLog(key + ":" + value);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private List<OMElement> gatherNamespaces(OMElement e) {

        List<OMElement> r = new LinkedList();
        Iterator children = e.getChildElements();

        while (children.hasNext()) {
            Object node = children.next();
            if (node instanceof OMComment) {
            } else if (node instanceof OMElement) {
                r.add(((OMElement) node));
                r.addAll(gatherNamespaces((OMElement) node));
            }
        }
        return r;

    }

    public void applySelectedNamespaces(AXIOMXPath xpathExpression, String ns, OMContainer root, OMFactory factory) throws JaxenException {
        String[] nss = ns.split("\\s+");
        for (int i = 0; i < nss.length; i += 2) {
            String prefix = nss[i];
            String uri = nss[i + 1];

            OMElement e = new OMElementImpl(new QName(uri, "foo", prefix), root, factory) {
            };
            loclalLog("applying namespace " + e.getQName().getPrefix() + ":" + e.getQName().getNamespaceURI());
            xpathExpression.addNamespaces(e);

        }
    }
}
