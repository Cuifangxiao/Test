import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class WindowTest {
    @Test
    void openFile() {
        Window window = Mockito.mock(Window.class);
        window.open();
        Mockito.verify(window).open();
    }

    @Test
    void savetest() {
       Window window = Mockito.mock(Window.class);
       window.saveAstxt();
       Mockito.verify(window).saveAstxt();
    }

    @Test
    void saveAspdf() throws Exception {
        Window window = Mockito.mock(Window.class);
        window.saveAspdf();
        Mockito.verify(window).saveAspdf();
    }
}