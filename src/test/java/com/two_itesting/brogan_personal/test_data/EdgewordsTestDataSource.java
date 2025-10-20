package com.two_itesting.brogan_personal.test_data;

import com.two_itesting.brogan_personal.models.User;
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
import java.util.stream.Stream;

public class EdgewordsTestDataSource {

    private static final ClassLoader LOADER = EdgewordsTestDataSource.class.getClassLoader();

    private static final String TEST_USERNAME = System.getProperty("TEST_USERNAME");
    private static final String TEST_PASSWORD = System.getProperty("TEST_PASSWORD");

    private static final String TEST_DATA_DIR = System.getProperty("TEST_DATA_DIR", "data/");

    public static final User DEFAULT_USER = new User.Builder()
            .username(TEST_USERNAME)
            .password(TEST_PASSWORD)
            .build();


    public static Stream<Arguments> provideForTestCouponsApplied() throws IOException {
        List<List<String>> stringArgsFromFile = getStringArgsFromCSVForTest("testCouponsApplied");
        List<Arguments> listOfArguments = new ArrayList<>();
        for (List<String> argSet : stringArgsFromFile) {
            List<Object> argsAsList = new ArrayList<>(argSet);
            argsAsList.addFirst(DEFAULT_USER);
            argsAsList.addLast(EdgewordsProductListFetcher.getRandomProductName());
            Arguments args = Arguments.of(argsAsList.toArray());
            listOfArguments.add(args);
        }
        return listOfArguments.stream();
    }

    public static Stream<Arguments> provideForTestPlacedOrderIsTracked() throws IOException {
        List<List<String>> stringArgsFromFile = getStringArgsFromCSVForTest("testPlacedOrderIsTracked");
        List<Arguments> listOfArguments = new ArrayList<>();
        for (List<String> argSet : stringArgsFromFile) { // line in CSV = sep test instance
            List<Object> argsAsList = new ArrayList<>();
            User.Builder userBuilder = new User.Builder(DEFAULT_USER);
            User user = userBuilder
                    .firstName(argSet.get(0))
                    .lastName(argSet.get(1))
                    .streetAddress(argSet.get(2))
                    .townCity(argSet.get(3))
                    .postcode(argSet.get(4))
                    .phoneNumber(argSet.get(5))
                    .build();
            argsAsList.addFirst(user); // prepend args from file with this user
            argsAsList.addLast(EdgewordsProductListFetcher.getRandomProductName()); // and append product
            Arguments args = Arguments.of(argsAsList.toArray());
            listOfArguments.add(args);
        }
        return listOfArguments.stream();
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

    private static List<Arguments> convertNestedListToArgsList(List<List<String>> nestedArgsList) {
        List<Arguments> allArgs = new ArrayList<>();
        for (List<String> argList : nestedArgsList) {
            allArgs.add(Arguments.of(argList.toArray()));
        }
        return allArgs;
    }

}
