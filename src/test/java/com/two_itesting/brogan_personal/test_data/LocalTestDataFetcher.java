package com.two_itesting.brogan_personal.test_data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.two_itesting.brogan_personal.models.site.Coupon;
import com.two_itesting.brogan_personal.models.site.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LocalTestDataFetcher {

    public static final User DEFAULT_USER = new User.Builder().build();
    private static final ClassLoader LOADER = LocalTestDataFetcher.class.getClassLoader();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String TEST_DATA_DIR = System.getProperty("TEST_DATA_DIR", "data/");

    private static final String USERS_FILE = "users.json";
    private static final String COUPONS_FILE = "coupons.json";

    private static List<User> userList;
    private static List<Coupon> couponList;

    public static List<User> fetchUserList()  {
        if (userList == null) {
            try {
                userList = importUsersFromFile(USERS_FILE);
                userList = injectSensitiveCredentials(userList);
            } catch (IOException e) {
                userList = List.of();
            }
        }
        return userList;
    }

    public static List<Coupon> fetchCouponList() {
        if (couponList == null) {
            try {
                couponList = importCouponsFromFile(COUPONS_FILE);
            } catch (IOException e) {
                couponList = List.of();
            }
        }
        return couponList;
    }

    public static List<List<String>> getStringArgsFromCSVForTest(String filename) throws IOException {
        InputStream testDataInputStream = loadTestDataFile(filename + ".csv");
        Reader testDataInputStreamReader = new InputStreamReader(testDataInputStream);
        CSVFormat format = CSVFormat.RFC4180.builder().setHeader().get();
        CSVParser parser = format.parse(testDataInputStreamReader);
        List<CSVRecord> records = parser.getRecords();
        List<List<String>> allFoundArgs = new ArrayList<>();
        List<String> readInFields;
        for (CSVRecord record : records) { // read each field in as separate argument, each record separate call
            readInFields = record.toList();
            allFoundArgs.add(readInFields);
        }
        return allFoundArgs;
    }

    private static InputStream loadTestDataFile(String resourceFilename) {
        return loadResourceAsStream(TEST_DATA_DIR + resourceFilename);
    }

    public static InputStream loadResourceAsStream(String resource) {
        return LOADER.getResourceAsStream(resource);
    }

    public static List<Arguments> convertNestedListToArgsList(List<List<Object>> nestedArgsList) {
        List<Arguments> allArgs = new ArrayList<>();
        for (List<Object> argList : nestedArgsList) {
            allArgs.add(Arguments.of(argList.toArray()));
        }
        return allArgs;
    }

    public static List<User> importUsersFromFile(String filename) throws IOException {
        InputStream userInputStream = loadTestDataFile(filename);
        return MAPPER.readValue(userInputStream, new TypeReference<List<User>>() {});
    }

    public static List<Coupon> importCouponsFromFile(String filename) throws IOException {
        InputStream couponsInputStream = loadTestDataFile(filename);
        return MAPPER.readValue(couponsInputStream, new TypeReference<List<Coupon>>() {});
    }

    public static List<User> injectSensitiveCredentials(List<User> users) {
        List<User> newUserList = new ArrayList<>();
        User.Builder newUserBuilder;
        for (User user : users) {
            newUserBuilder = new User.Builder(user);
            if (user.username() == null) {
                newUserBuilder.username(System.getProperty("TEST_USERNAME"));
            }
            if (user.password() == null) {
                newUserBuilder.password(System.getProperty("TEST_PASSWORD"));
            }
            newUserList.add(newUserBuilder.build());
        }
        return newUserList;
    }

}
