
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry>> map = new HashMap<>();


    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File pdf : pdfsDir.listFiles()) {
            var doc = new PdfDocument(new PdfReader(pdf));
            //Получаем количество страниц в документе
            int pageCount = doc.getNumberOfPages();
            //Проходим циклом по всем страницам
            for (int i = 1; i < pageCount; i++) {
                //Получаем номер страницы
                var page = doc.getPage(i);
                //Получаем текст на этой странице
                var text = PdfTextExtractor.getTextFromPage(page);
                // Разбиваем текст на слова
                var words = text.split("\\P{IsAlphabetic}+");

                Map<String, Integer> freqs = new HashMap<>();

                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }
                for (String word : freqs.keySet()) {
                    PageEntry pageEntry = new PageEntry(pdf.getName(), i, freqs.get(word));
                    if (map.containsKey(word)) {
                        map.get(word).add(pageEntry);
                    } else {
                        map.put(word, new ArrayList<>());
                        map.get(word).add(pageEntry);
                    }
                    map.values().forEach(Collections::sort);
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> result = new ArrayList<>();
        word = word.toLowerCase();
        if (map.containsKey(word)) {
            for (PageEntry pageEntry : map.get(word)) {
                result.add(pageEntry);
            }
        }
        return result;
    }
}
