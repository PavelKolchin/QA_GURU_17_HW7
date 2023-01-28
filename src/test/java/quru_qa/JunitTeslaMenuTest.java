package quru_qa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import quru_qa.data.Model;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.containExactTextsCaseSensitive;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class JunitTeslaMenuTest {

    @BeforeEach
    public void setUp() {
        Configuration.browserSize = "900x1080";
        open("https://www.tesla.com/");
        $("#tds-site-header").find(byText("Menu")).click();

    }

    @ParameterizedTest(name = "Ссылка на раздел {0} в главном меню сайта Tesla должна выводить страницу этого раздела")
    @Tag("Major")
    @ValueSource(
            strings = {"Model S", "Model 3", "Solar Roof", "Insurance"}
    )
    void linkInMenuOnTeslaSholdFollowToModelPage(String strings) {
        $(".tds-modal-content").find(byText(strings)).click();
        $(".tcl-hero__heading").shouldHave(text(strings));
    }



    @ParameterizedTest(name = "На странице модели Tesla {0}, запас хода должен быть {1}")
    @Tag("Minor")
    @CsvSource({
            "Model S, 396",
            "Model 3, 358",
            "Model X, 333"
    })
    void rangeForEachModelShouldBeConfirmed(String model, String range) {
        $(".tds-modal-content").find(byText(model)).click();
        $("div[class='tcl-hero__content-end tds-animate_small--reveal']").
                $(".tcl-badges.tcl-badges--with-animation.tds-colorscheme--dark").shouldHave(text(range));
    }




    static Stream<Arguments> onInventoryPageListOfTrimShouldBeSpecifiedForEachModel() {
        return Stream.of(
                Arguments.of(Model.MODEL_3, List.of("Performance All-Wheel Drive", "Long Range All-Wheel Drive", "Model 3 Rear-Wheel Drive")),
                Arguments.of(Model.MODEL_S, List.of("Model S Plaid", "Model S")),
                Arguments.of(Model.MODEL_X, List.of("Model X Plaid", "Model X"))
        );
    }
    @MethodSource
    @ParameterizedTest(name = "На странице Inventory модели Tesla {0}, должен быть доступны варианты отделки {1}")
    @Tag("Minor")
    void onInventoryPageListOfTrimShouldBeSpecifiedForEachModel(
            Model model,
            List<String> trim
    ) {
        $(".tds-modal-content").find(byText("Existing Inventory")).click();
        $("#inventory-filters-modal").click();
        $(".filter.filter-Model.half-width div").find(byText(model.toString())).click();
        $$(".filter.filter-TRIM.full-width div div").shouldHave(containExactTextsCaseSensitive(trim));
    }
}