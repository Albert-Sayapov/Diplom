import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry o) {
        return o.count - this.count;
    }

    @Override
    public String toString() {
        Map result = new LinkedHashMap();
        result.put("pdfName", pdfName);
        result.put("page", page);
        result.put("count", count);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String strResult = gson.toJson(result);
        return strResult;
    }
}
