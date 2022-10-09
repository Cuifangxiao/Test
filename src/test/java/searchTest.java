import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class searchTest {
    search search1 = new search(200,200);
    Window window = new Window();
    @Test
    void search_word() {
        Window.workArea.setText("int int int ");
        search.lookFor_field.setText("int");
        int x = search1.search_word();
        Assertions.assertEquals(3,x);
    }
}