package com.stackoverflow;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.UUID;

public class Q23633894
{
    public static class Person
    {
        private final String firstName;
        private final String lastName;
        private final Date birthday;
        private final UUID key;
        private final String strRep;

        public Person(@Nonnull final String firstName, @Nonnull final String lastName, @Nonnull final Date birthday)
        {
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthday = birthday;
            this.strRep = String.format("%s%s%d", firstName, lastName, birthday.getTime());
            this.key = UUID.nameUUIDFromBytes(this.strRep.getBytes());
        }

        @Nonnull
        public UUID getKey()
        {
            return this.key;
        }

        // Other getter/setters omitted for brevity

        @Override
        @Nonnull
        public String toString()
        {
            return this.strRep;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }
            final Person person = (Person) o;
            return key.equals(person.key);
        }

        @Override
        public int hashCode()
        {
            return key.hashCode();
        }
    }
}
