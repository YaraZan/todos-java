package com.example.demo.Helpers;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Page {

    public static Node PAGE_HEADER;

    public static Node PAGE_BODY;

    public Page(Node header, Node body) {
        PAGE_HEADER = header;
        PAGE_BODY = body;
    }

    public void setPageHeader(Node header) { PAGE_HEADER = header; }

    public Node getPageHeader() { return PAGE_HEADER; }

    public void setPageBody(Node body) { PAGE_BODY = body; }

    public Node getPageBody() { return PAGE_BODY; }
}
