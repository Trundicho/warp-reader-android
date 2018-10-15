package de.trundicho.warpreader.view.parser;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeHTMLParser;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("serial")
public class TextFromWebUrlParserService {

	public TextFromWebUrlParserService() {
		// Workaround for classnotfound issue on jetty. Why - don't know!?!
		System.out.println(BoilerpipeHTMLParser.class.getSimpleName());
	}

	public String parseTextFromWebsite(String input) throws IllegalArgumentException {
		HTMLDocument htmlDoc;
		try {
			htmlDoc = HTMLFetcher.fetch(new URL(input));
			TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
			return CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BoilerpipeProcessingException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return null;
	}

}
