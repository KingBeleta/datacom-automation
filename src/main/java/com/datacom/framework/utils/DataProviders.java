package com.datacom.framework.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.DataProvider;

import com.datacom.framework.models.UserData;

public class DataProviders {

    private static final String EXCEL_FILE = "src/test/resources/TestData.xlsx";
    private static final String SHEET_NAME = "Sheet1"; 

    @DataProvider(name = "userDataProvider", parallel = true)
    public static Object[][] userDataProvider(Method method) {

        List<UserData> allUsers = ExcelUtils.getUserData(EXCEL_FILE, SHEET_NAME);

        List<UserData> filtered = allUsers.stream()
                .filter(user -> user.getTestName().equalsIgnoreCase(method.getName()))
                .collect(Collectors.toList());

        Object[][] data = new Object[filtered.size()][1];
        for (int i = 0; i < filtered.size(); i++) {
            data[i][0] = filtered.get(i);
        }
        return data;
    }
}
