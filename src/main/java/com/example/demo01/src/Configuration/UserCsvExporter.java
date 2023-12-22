package com.example.demo01.src.Configuration;

import com.example.demo01.src.Pojo.Users;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserCsvExporter extends AbstractExporter {


    public void export(List<Users> listuser, HttpServletResponse response) throws IOException {
        super.setResponseHeader("text/csv", ".csv", response, "users_");
        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"User ID", "E-mail", "First Name", "Last Name", "Roles", "Enabled"};
        String[] fieldMapping = {"id", "Email", "first_name", "last_name", "usersRole", "enabledStatus"};
        csvBeanWriter.writeHeader(csvHeader);
        for (Users user : listuser) {
            csvBeanWriter.write(user, fieldMapping);
        }

        csvBeanWriter.close();
    }
}
