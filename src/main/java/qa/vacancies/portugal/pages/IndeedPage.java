package qa.vacancies.portugal.pages;

import com.codeborne.selenide.SelenideElement;
import qa.vacancies.portugal.utils.constants.Constants;
import qa.vacancies.portugal.utils.model.Vacancy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class IndeedPage implements PageObject<IndeedPage> {
    private static final String CLICK_CARD_SELECTOR = ".clickcard";
    private static final String TITLE_SELECTOR = ".jobtitle";
    private static final String COMPANY_SELECTOR = ".company";
    private static final String TITLE_URL_SELECTOR = ".title a";

    @Override
    public IndeedPage openAndSearch(String urlTemplate, Object... params) {
        open(String.format(urlTemplate, params));
        return this;
    }

    @Override
    public List<Vacancy> getVacancies() {
        return $$(CLICK_CARD_SELECTOR)
                .stream()
                .filter(this::containsQuery)
                .map(element -> Vacancy.builder().title(getTitle(element)).company(getCompany(element)).url(getUrl(element)).build())
                .collect(Collectors.toList());
    }

    private boolean containsQuery(SelenideElement element) {
        return Stream
                .of(Constants.QUERIES)
                .anyMatch(query -> element.$(TITLE_SELECTOR).getText().toLowerCase().contains(query));
    }

    private String getTitle(SelenideElement element) {
        return element.$(TITLE_SELECTOR).getText();
    }

    private String getCompany(SelenideElement element) {
        return element.$(COMPANY_SELECTOR).getText();
    }

    private String getUrl(SelenideElement element) {
        return element.$(TITLE_URL_SELECTOR).getAttribute("href");
    }
}
