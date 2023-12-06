package com.example.demo01.src.Configuration;

import com.example.demo01.src.Pojo.Category;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryCsvExporter extends AbstractExporter {

    public void export(List<Category> categoryList, HttpServletResponse response) throws IOException {
        super.setResponseHeader("text/csv", ".csv", response, "category_");
        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Category ID", "Category Name"};
        String[] fieldMapping = {"id", "name"};
        csvBeanWriter.writeHeader(csvHeader);
        for (Category category : categoryList) {
            csvBeanWriter.write(category, fieldMapping);
        }
        csvBeanWriter.close();

    }
}
