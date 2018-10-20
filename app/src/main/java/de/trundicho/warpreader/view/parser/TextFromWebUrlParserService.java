package de.trundicho.warpreader.view.parser;


import me.angrybyte.goose.Article;
import me.angrybyte.goose.Configuration;
import me.angrybyte.goose.ContentExtractor;

@SuppressWarnings("serial")
public class TextFromWebUrlParserService {


    private final ContentExtractor extractor;

    public TextFromWebUrlParserService() {
        Configuration config = new Configuration(null);
        extractor = new ContentExtractor(config);
    }

    public String parseTextFromWebsite(String url) throws IllegalArgumentException {
        Article article = extractor.extractContent(url, false);
        if (article == null) {
            return "Couldn't load the article, is your URL correct, is your Internet working?";
        }
        String details = article.getCleanedArticleText();
        if (details == null || details.isEmpty()) {
            System.err.println("Couldn't load the article text, the page is messy. Trying with page description...");
            return article.getMetaDescription();
        }
        return details;
    }

}
