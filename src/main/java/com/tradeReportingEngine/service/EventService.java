package main.java.com.tradeReportingEngine.service;

import main.java.com.tradeReportingEngine.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import main.java.com.tradeReportingEngine.repository.EventRepository;
import org.apache.commons.lang3.StringUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void processXmlFile(InputStream xmlInputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlInputStream);

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        // Define XPath expressions to extract data from the XML
        XPathExpression sellerPartyExpression = xpath.compile("/event/seller_party");
        XPathExpression premiumCurrencyExpression = xpath.compile("/event/premium_currency");
        XPathExpression buyerPartyExpression = xpath.compile("/event/buyer_party");

        NodeList nodeList = (NodeList) xpath.evaluate("/events/event", doc, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            String sellerParty = sellerPartyExpression.evaluate(node);
            String premiumCurrency = premiumCurrencyExpression.evaluate(node);
            String buyerParty = buyerPartyExpression.evaluate(node);

            Event event = new Event(sellerParty, premiumCurrency, buyerParty);
            eventRepository.save(event);
        }
    }

    public List<Event> getFilteredEvents() {
        List<Event> allEvents = eventRepository.findAll();

        // Define filtering criteria and perform filtering
        List<Event> filteredEvents = allEvents.stream()
                .filter(event -> (isSellerPartyAndCurrencyMatch(event) || isOtherSellerPartyAndCurrencyMatch(event))
                        && !areSellerAndBuyerAnagrams(event))
                .collect(Collectors.toList());

        return filteredEvents;
    }

    private boolean isSellerPartyAndCurrencyMatch(Event event) {
        return ("EMU_BANK".equals(event.getSellerParty()) && "AUD".equals(event.getPremiumCurrency()))
                || ("BISON_BANK".equals(event.getSellerParty()) && "USD".equals(event.getPremiumCurrency()));
    }

    private boolean isOtherSellerPartyAndCurrencyMatch(Event event) {
        return ("BISON_BANK".equals(event.getSellerParty()) && "AUD".equals(event.getPremiumCurrency()))
                || ("EMU_BANK".equals(event.getSellerParty()) && "USD".equals(event.getPremiumCurrency()));
    }

    private boolean areSellerAndBuyerAnagrams(Event event) {
        String sellerParty = event.getSellerParty();
        String buyerParty = event.getBuyerParty();

        // Remove spaces and convert to lowercase for case-insensitive comparison
        String sanitizedSellerParty = sellerParty.replaceAll("\\s+", "").toLowerCase();
        String sanitizedBuyerParty = buyerParty.replaceAll("\\s+", "").toLowerCase();

        // Check if the sorted character arrays are equal (indicating they are anagrams)
        return areStringsAnagrams(sanitizedSellerParty, sanitizedBuyerParty);
    }

    private boolean areStringsAnagrams(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        char[] charArray1 = str1.toCharArray();
        char[] charArray2 = str2.toCharArray();

        // Sort the character arrays and compare them
        java.util.Arrays.sort(charArray1);
        java.util.Arrays.sort(charArray2);

        return StringUtils.equals(new String(charArray1), new String(charArray2));
    }

}
