package com.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vertigrated.FormattedRuntimeException;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Q26110375
{
    public static void main(final String[] args)
    {
        final User user = new User("Zogbi", "password1", "Tim", "S", "Smith", "Gibbus");
        final Account account = new Account("desc2", user);

        try
        {
            final ObjectMapper m = new ObjectMapper();
            m.enable(SerializationFeature.INDENT_OUTPUT);
            m.writeValue(System.out, account);
        }
        catch (final IOException e)
        {
            throw new FormattedRuntimeException(e, "Could not convert %s to Json because of %s", account.getClass(), e.getMessage());
        }
    }

    private static class User
    {
        @JsonProperty
        private final String username;
        @JsonProperty
        private final String password;
        @JsonProperty
        private final String firstName;
        @JsonProperty
        private final String middleName;
        @JsonProperty
        private final String lastName;
        @JsonProperty
        private final String alias;

        private User(@Nonnull final String username, @Nonnull final String password, @Nonnull final String firstName,
                     @Nonnull final String middleName, @Nonnull final String lastName, @Nonnull final String alias)
        {
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.alias = alias;
        }
    }

    private static class Account
    {
        @JsonProperty
        private final String description;
        @JsonProperty
        private final List<User> users;

        private Account(@Nonnull final String description, @Nonnull final User ... users)
        {
            this.description = description;
            this.users = Arrays.asList(users);
        }
    }
}
