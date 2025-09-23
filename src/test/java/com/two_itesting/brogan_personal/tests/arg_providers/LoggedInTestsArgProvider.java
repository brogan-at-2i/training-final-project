package com.two_itesting.brogan_personal.tests.arg_providers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

/**
 * A custom argument provider for the LoggedInTests suite.
 */
public final class LoggedInTestsArgProvider implements ArgumentsProvider {

    public static final String TEST_PROPERTIES_FILENAME = "testing.properties";
    private static final ClassLoader LOADER = LoggedInTestsArgProvider.class.getClassLoader();
    private static Properties properties;

    public LoggedInTestsArgProvider() throws IOException {
        properties = new Properties();
        properties.load(LOADER.getResourceAsStream(TEST_PROPERTIES_FILENAME));
    }

    private static InputStream loadTestDataFile(String resourceFilename) {
        return loadResourceAsStream(properties.getProperty("testInputDir", "") + resourceFilename);
    }

    @Override
    public @NotNull Stream<Arguments> provideArguments(@NotNull ParameterDeclarations paramDecls, @NotNull ExtensionContext context) throws IOException {
        List<List<String>> stringArgsFromFile = getStringArgsFromFileForTest(context); // get whatever args from file
        // then PREPEND username and password as login-specific test
        // and APPEND chosen product ID
        for (List<String> argSet : stringArgsFromFile) {
            argSet.add(0, properties.getProperty("username")); // hard-coded to get username/password
            argSet.add(1, properties.getProperty("password"));
            argSet.addLast(generateProductId());
        }
        return convertNestedListToArgsList(stringArgsFromFile).stream();
    }

    public static List<List<String>> getStringArgsFromFileForTest(ExtensionContext context) throws IOException {
        Optional<Method> maybeTestMethod = context.getTestMethod();
        if (maybeTestMethod.isEmpty()) {
            // then throw new RuntimeError (?)
            throw new RuntimeException("Test method not found");
        }
        if (!"CSV".equals(properties.getProperty("testDataFormat"))) {
            return new ArrayList<>(); // don't know how to deal with non-csv!
        }
        String testMethodName = maybeTestMethod.orElseThrow().getName();
        InputStream testDataInputStream = loadTestDataFile(testMethodName + ".csv"); // CSV format only
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

    private static List<Arguments> convertNestedListToArgsList(List<List<String>> nestedArgsList) {
        List<Arguments> allArgs = new ArrayList<>();
        for (List<String> argList : nestedArgsList) {
            allArgs.add(Arguments.of(argList.toArray()));
        }
        return allArgs;
    }

    private static String generateProductId() {
        String minProductId = properties.getProperty("siteMinProductId"); // range of product ids so can pick randomly
        String maxProductId = properties.getProperty("siteMaxProductId");
        int numMinProductId = Integer.parseInt(minProductId);
        int numMaxProductId = Integer.parseInt(maxProductId);
        int numChosenId = new Random().nextInt(numMinProductId, numMaxProductId + 1); // exclusive bound
        return String.valueOf(numChosenId);
    }

    public static InputStream loadResourceAsStream(String resource) {
        return LOADER.getResourceAsStream(resource);
    }

}
