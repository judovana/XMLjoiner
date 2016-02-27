/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmljoiner.impl;

import java.util.List;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;

/**
 *
 * @author jvanek
 */
public class FirstDocKeeper {

    private OMElement root;
    private List<OMElement> footer;
    private OMDocument document;
    private long headerSize;
    private long footerSize;
    private List<OMElement> header;
    private long contentSize;
    private List<OMElement> content;

    void setRoot(OMElement root) {
        this.root = root;
    }

    public OMElement getRoot() {
        return root;
    }

    void setFooter(List<OMElement> footerList) {
        this.footer = footerList;
    }

    public List<OMElement> getFooter() {
        return footer;
    }

    void setDocument(OMDocument doc) {
        document = doc;
    }

    void setHeaderSize(long headerSize) {
        this.headerSize = headerSize;
    }

    void setFooterSize(long footerSize) {
        this.footerSize = footerSize;
    }

    void setContentSize(long contentSize) {
        this.contentSize = contentSize;
    }

    void setHeader(List<OMElement> headerList) {
        this.header = headerList;
    }

    void setContent(List<OMElement> contentList) {
        this.content = contentList;
    }

    public List<OMElement> getContent() {
        return content;
    }

    public long getContentSize() {
        return contentSize;
    }

    public OMDocument getDocument() {
        return document;
    }

    public long getFooterSize() {
        return footerSize;
    }

    public List<OMElement> getHeader() {
        return header;
    }

    public long getHeaderSize() {
        return headerSize;
    }

}
