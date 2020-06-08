package infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("TextDeserializer")
public class TextDeserializerTest {
    final static String textFileLocation = "src/test/java/resources/deserializetext.txt";
    final static List<String> correctWords = Arrays.asList("bijt", "kwijl", "hijgen", "hipheid", "hinkelen", "hoofdpersoon", "exuberant", "ezeldrijver", "parelrand", "uitkeringskas");

    @Test
    @DisplayName("Deserialize")
    public void testDeserialize() throws FileNotFoundException {
        TextDeserializer textDeserializer = new TextDeserializer();
        List<String> deserializedText = textDeserializer.deserialize(textFileLocation);
        assertEquals(correctWords, deserializedText);
    }

    @Test
    @DisplayName("File not Found")
    public void testFileNotFoundException() {
        TextDeserializer textDeserializer = new TextDeserializer();
        assertThrows(FileNotFoundException.class, () -> textDeserializer.deserialize("text.txt"));
    }
}
