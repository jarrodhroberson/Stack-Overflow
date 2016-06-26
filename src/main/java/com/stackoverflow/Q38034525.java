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

        System.out.println(new ObjectMapper().writeValueAsString(rc));
        final ObjectMapper om = new ObjectMapper();
        om.addMixIn(RaceCar.class, RestrictedRaceCar.class);
        System.out.println(om.writeValueAsString(rc));

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
