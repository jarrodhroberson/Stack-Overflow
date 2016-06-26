package com.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Q38034525
{
    public static void main(String[] args) throws Exception
    {
        final RaceCar rc = new RaceCar();
        rc.model = "Corvette Stringray";
        rc.topSpeed = 195;

        final ObjectMapper om = new ObjectMapper();
        System.out.println(om.writeValueAsString(rc));
        final ObjectMapper om2 = om.copy().addMixIn(RaceCar.class, RestrictedRaceCar.class);
        System.out.println(om2.writeValueAsString(rc));

    }

    public static abstract class RestrictedRaceCar
    {
        @JsonIgnore
        public Integer topSpeed;
    }

    public static class RaceCar
    {
        public String model;
        public Integer topSpeed;
    }
}
