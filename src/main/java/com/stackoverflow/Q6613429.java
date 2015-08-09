package com.stackoverflow;

import javax.annotation.Nonnull;
import java.util.Date;

public class Q6613429
{
    public static void main(final String[] args)
    {
        final Rights r = PermissionManager.grantUser("me").permissionTo("ALL").item("EVERYTHING").asOf(new Date());
        PermissionManager.apply(r);
    }

    public static class Rights
    {
        private String user;
        private String permission;
        private String item;
        private Date ofDate;

        private Rights() { /* intentionally blank */ }
    }

    public static class PermissionManager
    {
        public static PermissionManager.AssignPermission grantUser(@Nonnull final String user)
        {
            final Rights r = new Rights(); return new AssignPermission() {

                @Override
                public AssignItem permissionTo(@Nonnull String p) {
                    r.permission = p;
                    return new AssignItem()
                {
                    @Override
                    public SetDate item(String i) {
                        r.item = i;
                        return new SetDate()
                    {
                        @Override
                        public Rights asOf(Date d) {
                            r.ofDate = d;
                            return r;
                        }
                    };}
                };}
            };
        }

        public static void apply(@Nonnull final Rights r) { /* do the persistence here */ }

        public interface AssignPermission
        {
            public AssignItem permissionTo(@Nonnull final String p);
        }

        public interface AssignItem
        {
            public SetDate item(String i);
        }

        public interface SetDate
        {
            public Rights asOf(Date d);
        }
    }
}
