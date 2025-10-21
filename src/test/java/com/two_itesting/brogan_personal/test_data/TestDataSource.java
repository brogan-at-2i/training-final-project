package com.two_itesting.brogan_personal.test_data;

import com.two_itesting.brogan_personal.models.site.Coupon;
import com.two_itesting.brogan_personal.models.site.Product;
import com.two_itesting.brogan_personal.models.site.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.cartesianProduct;

public class TestDataSource {

    public static Stream<Arguments> provideForTestCouponsApplied() throws IOException {
        List<User> usersUnderTest = LocalTestDataFetcher.fetchUserList();
        List<Coupon> couponsUnderTest = LocalTestDataFetcher.fetchCouponList();
        List<List<Object>> eachUserWithEachCoupon = cartesianProduct(usersUnderTest, couponsUnderTest);
        List<List<Object>> paramsAsLists = appendProductToEachCall(eachUserWithEachCoupon);
        return LocalTestDataFetcher.convertNestedListToArgsList(paramsAsLists).stream();
    }

    public static Stream<Arguments> provideForTestPlacedOrderIsTracked() throws IOException {
        List<User> usersUnderTest = LocalTestDataFetcher.fetchUserList();
        List<List<Object>> paramsAsLists = List.of(appendProductToList(usersUnderTest));
        return LocalTestDataFetcher.convertNestedListToArgsList(paramsAsLists).stream();
    }

    private static List<List<Object>> appendProductToEachCall(List<? extends List<?>> nestedParams) {
        List<List<Object>> newAllParamsList = new ArrayList<>();
        for (List<?> paramList : nestedParams) {
            newAllParamsList.add(appendProductToList(paramList));
        }
        return newAllParamsList;
    }

    private static List<Object> appendProductToList(List<?> paramList) {
        List<Object> newParamList;
        newParamList = new ArrayList<>(paramList);
        newParamList.addLast(ProductListFetcher.getRandomProduct());
        return newParamList;
    }

}
