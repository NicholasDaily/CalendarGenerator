package com.unitedhuskies.CalendarGenerator;

import java.util.Iterator;
import java.util.ArrayList;

public class HtmlGenerator
{
    private String tagName;
    private boolean selfClosing;
    private ArrayList<String[]> attributeValues;
    private String elementContent;
    private StringBuilder htmlElement;
    private boolean containerElement;
    
    HtmlGenerator(final String tagName) {
        this.tagName = null;
        this.selfClosing = false;
        this.attributeValues = new ArrayList<String[]>();
        this.elementContent = "";
        this.htmlElement = new StringBuilder();
        this.containerElement = false;
        this.tagName = tagName;
    }
    
    HtmlGenerator(final String tagName, final boolean selfClosing) {
        this(tagName);
        this.selfClosing = selfClosing;
    }
    
    HtmlGenerator(final String tagName, final boolean selfClosing, final boolean containerElement) {
        this(tagName, selfClosing);
        this.containerElement = containerElement;
    }
    
    HtmlGenerator(final String tagName, final boolean selfClosing, final String elementContent) {
        this(tagName, selfClosing);
        this.elementContent = elementContent;
    }
    
    HtmlGenerator(final String tagName, final String elementContent, final boolean containerElement) {
        this(tagName, elementContent);
        this.containerElement = containerElement;
    }
    
    HtmlGenerator(final String tagName, final boolean selfClosing, final String elementContent, final ArrayList<String[]> attributeValues) {
        this(tagName, selfClosing, elementContent);
        this.attributeValues = (ArrayList<String[]>)attributeValues.clone();
    }
    
    HtmlGenerator(final String tagName, final String elementContent, final ArrayList<String[]> attributeValues, final boolean containerElement) {
        this(tagName, elementContent, attributeValues);
        this.containerElement = containerElement;
    }
    
    HtmlGenerator(final String tagName, final boolean selfClosing, final ArrayList<String[]> attributeValues) {
        this(tagName, selfClosing);
        this.attributeValues = (ArrayList<String[]>)attributeValues.clone();
    }
    
    HtmlGenerator(final String tagName, final ArrayList<String[]> attributeValues) {
        this(tagName);
        this.attributeValues = (ArrayList<String[]>)attributeValues.clone();
    }
    
    HtmlGenerator(final String tagName, final String elementContent, final ArrayList<String[]> attributeValues) {
        this(tagName, elementContent);
        this.attributeValues = attributeValues;
    }
    
    HtmlGenerator(final String tagName, final String elementContent) {
        this(tagName);
        this.elementContent = elementContent;
    }
    
    public void appendContent(HtmlGenerator x) {
    	this.elementContent += x.returnHtmlLine();
    }
    
    public String returnHtmlLine() {
        this.htmlElement.append("<" + this.tagName);
        if (!this.attributeValues.isEmpty()) {
            this.htmlElement.append(" ");
            for (final String[] x : this.attributeValues) {
                this.htmlElement.append(x[0]);
                if (x.length > 1) {
                    this.htmlElement.append("=\"" + x[1] + "\"");
                }
                this.htmlElement.append(" ");
            }
        }
        this.htmlElement.append(">");
        if (this.selfClosing) {
            return String.valueOf(this.htmlElement.toString()) + "\n";
        }
        if (!this.containerElement) {
            this.htmlElement.append(String.valueOf(this.elementContent) + "</" + this.tagName + ">\n");
        }
        else {
            this.htmlElement.append("\n");
            final String[] lines = this.elementContent.split("\n");
            if (lines.length != 0) {
                for (int i = 0; i < lines.length; ++i) {
                    this.htmlElement.append("\t" + lines[i] + "\n");
                }
            }
            else {
                this.htmlElement.append("\t" + this.elementContent + "\n");
            }
            this.htmlElement.append("</" + this.tagName + ">\n");
        }
        return this.htmlElement.toString();
    }
}