package com.two_itesting.brogan_personal.tests.test_data;

public record User(
    String username,
    String password,
    String firstName,
    String lastName,
    String streetAddress,
    String townCity,
    String postcode,
    String phoneNumber
) {
    private User(Builder builder) {
        this(
                builder.username,
                builder.password,
                builder.firstName,
                builder.lastName,
                builder.streetAddress,
                builder.townCity,
                builder.postcode,
                builder.phoneNumber
        );
    }

    public static class Builder {

        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String streetAddress;
        private String townCity;
        private String postcode;
        private String phoneNumber;

        public Builder() {

        }

        public Builder(User existingUser) {
            this.username = existingUser.username;
            this.password = existingUser.password;
            this.firstName = existingUser.firstName;
            this.lastName = existingUser.lastName;
            this.streetAddress = existingUser.streetAddress;
            this.townCity = existingUser.townCity;
            this.postcode = existingUser.postcode;
            this.phoneNumber = existingUser.phoneNumber;

        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder streetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
            return this;
        }

        public Builder townCity(String townCity) {
            this.townCity = townCity;
            return this;
        }

        public Builder postcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

    public static void main(String[] args) {
        System.out.println("IN MAIN");
        User user = new Builder().username("Brogusername").password("Brogpassword").postcode("C12 F56").build();
        System.out.println(user);

    }



}
