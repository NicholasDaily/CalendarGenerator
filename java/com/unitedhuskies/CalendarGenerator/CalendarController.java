package com.unitedhuskies.CalendarGenerator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.ArrayList;
import java.time.LocalDate;
import com.unitedhuskies.CalendarGenerator.*;

@Controller
public class CalendarController {
	
	@PostMapping("/calendar")
	public ResponseEntity<String> generate(@RequestParam Map<String, String> formData){
		final String[] monthsOfYear = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		final String input = formData.get("date");
		System.out.println("INPUT: " + input);
        final String[] splitInput = input.split("-");
        String tmp1 = splitInput[0];
        String tmp2 = splitInput[1];
        splitInput[0] = tmp2;
        splitInput[1] = tmp1;
        final int monthNum = Integer.parseInt(splitInput[0]);
        final int year = Integer.parseInt(splitInput[1]);
        final LocalDate month = LocalDate.of(year, monthNum, 1);
		String document = "";
		document +="<html>";
		document +="<head>";
		document +="<link type=\"text/css\" rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css2?family=IM+Fell+English:ital@1&display=swap\">";
		document +="<style>";
		document += "table {\n" 
				+ "     border-collapse: collapse;\n"
				+ "     margin-left: auto;\n"
				+ "     margin-right: auto;\n"
				+ "     width: 90%;\n"
				+ "     max-width: 1154.410px;\n"
				+ "}\n"
				+ " td{\n"
				+ "     border: solid 1px black;\n"
				+ "}\n"
				+ " td {\n"
				+ "     width: 150px;\n"
				+ "     height: 150px;\n"
				+ "     padding: 0;\n"
				+ "     position: relative;\n"
				+ "     vertical-align: bottom;\n"
				+ "}\n"
				+ " img {\n"
				+ "     display: block;\n"
				+ "     margin: 0 auto;\n"
				+ "     width: 90%;\n"
				+ "     max-width: 1154.410px;\n"
				+ "}\n"
				+ " #heading {\n"
				+ "     margin-left: auto;\n"
				+ "     margin-right: auto;\n"
				+ "     width: 90%;\n"
				+ "     border-bottom: solid 3px black;\n"
				+ "     margin-bottom: 25px;\n"
				+ "     max-width: 1154.410px;\n"
				+ "}\n"
				+ " #heading h1 {\n"
				+ "     margin-bottom: 0;\n"
				+ "}\n"
				+ " #heading::after {\n"
				+ "     content: \"\";\n"
				+ "     clear: both;\n"
				+ "     display: table;\n"
				+ "}\n"
				+ " #monthName, #year {\n"
				+ "     letter-spacing: 5px;\n"
				+ "}\n"
				+ " #monthName {\n"
				+ "     float: left;\n"
				+ "}\n"
				+ " #year {\n"
				+ "     float: right;\n"
				+ "}\n"
				+ " .dayNumber {\n"
				+ "     position: absolute;\n"
				+ "     top: -20px;\n"
				+ "     left: 5px;\n"
				+ "     font-weight: bold;\n"
				+ "     font-size: 25px;\n"
				+ "     font-family: serif;\n"
				+ "}\n"
				+ " .afterMonth, .beforeMonth {\n"
				+ "     color: rgb(200, 200, 200);\n"
				+ "}\n"
				+ " ";
		document +="</style>";
		document +="</head>";
		document +="<body>";
		final ArrayList<String[]> elementAttributes = new ArrayList<String[]>();
        String innerHtml = "";
        String parentElement = "";
        String fp = formData.get("url");
        elementAttributes.add(new String[] { "src", fp });
        HtmlGenerator element = new HtmlGenerator("img", true, elementAttributes);
        document += element.returnHtmlLine();
        elementAttributes.clear();
        document +="<div id=\"heading\">";
        elementAttributes.add(new String[] { "id", "monthName" });
        element = new HtmlGenerator("h1", monthsOfYear[month.getMonthValue() - 1], elementAttributes);
        document += element.returnHtmlLine();
        elementAttributes.clear();
        elementAttributes.add(new String[] { "id", "year" });
        element = new HtmlGenerator("h1", String.valueOf(month.getYear()), elementAttributes);
        document += element.returnHtmlLine();
        elementAttributes.clear();
        document +="</div>";
        final String[] daysOfWeek = { "S", "M", "T", "W", "T", "F", "S" };
        for (int i = 0; i < 7; ++i) {
            element = new HtmlGenerator("th", daysOfWeek[i]);
            innerHtml += element.returnHtmlLine();
        }
        element = new HtmlGenerator("thead", innerHtml.toString(), true);
        parentElement += element.returnHtmlLine();
        innerHtml = "";
        int dayDate = 1;
        int nextMonthDate = 1;
        int savedYear = 0;
        int savedMonth = 0;
        int savedDay = 0;
        String savedDate = "";
        String[] parsingSaved = { "", "" };
        String[] parsingDate = { "", "", "" };
        
        LocalDate prevMonth;
        if (month.getMonthValue() == 1) {
            prevMonth = LocalDate.of(year - 1, 12, 1);
        }
        else {
            prevMonth = LocalDate.of(year, monthNum - 1, 1);
        }
        LocalDate nextMonth;
        if (month.getMonthValue() == 12) {
            nextMonth = LocalDate.of(year + 1, 1, 1);
        }
        else {
            nextMonth = LocalDate.of(year, monthNum + 1, 1);
        }
        String dayEvent = "";
        int j = 0;
        while (dayDate <= month.lengthOfMonth()) {
            for (int k = 0; k < 7; ++k) {
                if (((j == 0 && month.getDayOfWeek().ordinal() == k - 1) || dayDate > 1 || month.getDayOfWeek().ordinal() == 6) && dayDate <= month.lengthOfMonth()) {
                    dayEvent = "";
                    if (month.getYear() == savedYear && month.getMonthValue() == savedMonth && dayDate == savedDay) {
                        dayEvent = parsingSaved[1];
                        elementAttributes.add(new String[] { "class", "event" });
                        element = new HtmlGenerator("p", dayEvent, elementAttributes);
                        dayEvent = element.returnHtmlLine();
                        elementAttributes.clear();
                    }
                    elementAttributes.add(new String[] { "class", "dayNumber" });
                    element = new HtmlGenerator("p", String.valueOf(dayDate), elementAttributes);
                    element = new HtmlGenerator("td", String.valueOf(element.returnHtmlLine()) + dayEvent, true);
                    elementAttributes.clear();
                    ++dayDate;
                }
                else if (dayDate == 1 && k <= month.getDayOfWeek().ordinal()) {
                    final int dayBeforeMonth = prevMonth.lengthOfMonth() - (month.getDayOfWeek().ordinal() - k);
                    dayEvent = "";
                    if (prevMonth.getYear() == savedYear && prevMonth.getMonthValue() == savedMonth && dayBeforeMonth == savedDay) {
                        dayEvent = parsingSaved[1];
                        elementAttributes.add(new String[] { "class", "event beforeMonth" });
                        element = new HtmlGenerator("p", dayEvent, elementAttributes);
                        dayEvent = element.returnHtmlLine();
                        elementAttributes.clear();
                    }
                    elementAttributes.add(new String[] { "class", "dayNumber beforeMonth" });
                    element = new HtmlGenerator("p", String.valueOf(dayBeforeMonth), elementAttributes);
                    element = new HtmlGenerator("td", String.valueOf(element.returnHtmlLine()) + dayEvent, true);
                    elementAttributes.clear();
                }
                else {
                    dayEvent = "";
                    if (nextMonth.getYear() == savedYear && nextMonth.getMonthValue() == savedMonth && nextMonthDate == savedDay) {
                        dayEvent = parsingSaved[1];
                        elementAttributes.add(new String[] { "class", "event afterMonth" });
                        element = new HtmlGenerator("p", dayEvent, elementAttributes);
                        dayEvent = element.returnHtmlLine();
                        elementAttributes.clear();
                    }
                    elementAttributes.add(new String[] { "class", "dayNumber afterMonth" });
                    element = new HtmlGenerator("p", String.valueOf(nextMonthDate), elementAttributes);
                    ++nextMonthDate;
                    element = new HtmlGenerator("td", String.valueOf(element.returnHtmlLine()) + dayEvent, true);
                    elementAttributes.clear();
                }
                innerHtml += element.returnHtmlLine();
            }
            element = new HtmlGenerator("tr", innerHtml.toString(), true);
            parentElement += element.returnHtmlLine();
            innerHtml = "";
            ++j;
        }
        element = new HtmlGenerator("table", parentElement.toString(), true);
        parentElement = "";
        parentElement += element.returnHtmlLine();
        document += parentElement.toString();
		document +="</body>";
		document +="</html>";
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		return new ResponseEntity<String>(document.toString(), responseHeaders,
		                                  HttpStatus.OK);
	}
}
