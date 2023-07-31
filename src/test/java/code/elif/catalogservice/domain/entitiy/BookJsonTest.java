package code.elif.catalogservice.domain.entitiy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BookJsonTest {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = new Book("1234567890", "Title", "Author", new BigDecimal("12.3"));
        var jsonContent = json.write(book);

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathValue("@.price")
                .isEqualTo(book.price().doubleValue());
    }







    @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 12.2
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book("1234567890", "Title", "Author", new BigDecimal("12.2")));
    }
}