package com.justedlev.service.airlines.api;

public class PathConstants {

    public static final String ADD_PATH = "/add";
    public static final String GET_PATH = "/get";
    public static final String ALL_PATH = "/all";
    public static final String DELETE_PATH = "/delete";
    public static final String SELL_PATH = "/sell";

    public static final String USERS_PATH = "/users";
    public static final String ROLES_PATH = "/roles";
    public static final String AIRLINES_PATH = "/airlines";
    public static final String AIRCRAFT_PATH = "/aircraft";
    public static final String DESTINATIONS_PATH = "/destinations";

    public static final String ROLES_ADD_PATH = ROLES_PATH + ADD_PATH;
    public static final String ROLES_DELETE_PATH = ROLES_PATH + DELETE_PATH;

    public static final String USERS_ROLES_ADD_PATH = USERS_PATH + ROLES_ADD_PATH;
    public static final String USERS_ROLES_DELETE_PATH = USERS_PATH + ROLES_DELETE_PATH;

    public static final String USERS_ADD_PATH = USERS_PATH + ADD_PATH;
    public static final String USERS_DELETE_PATH = USERS_PATH + DELETE_PATH;

    public static final String DESTINATIONS_ALL_PATH = DESTINATIONS_PATH + ALL_PATH;
    public static final String DESTINATIONS_ADD_PATH = DESTINATIONS_PATH + ADD_PATH;
    public static final String DESTINATIONS_DELETE_PATH = DESTINATIONS_PATH + DELETE_PATH;

    public static final String AIRCRAFT_ADD_PATH = AIRCRAFT_PATH + ADD_PATH;
    public static final String AIRCRAFT_SELL_PATH = AIRCRAFT_PATH + SELL_PATH;
    public static final String AIRCRAFT_DELETE_PATH = AIRCRAFT_PATH + DELETE_PATH;

    public static final String AIRLINES_ADD_PATH = AIRLINES_PATH + ADD_PATH;
    public static final String AIRLINES_DELETE_PATH = AIRLINES_PATH + DELETE_PATH;

    public static final String AIRLINES_DESTINATIONS_PATH = AIRLINES_PATH + DESTINATIONS_PATH;
    public static final String AIRLINES_DESTINATIONS_ADD_PATH = AIRLINES_PATH + DESTINATIONS_ADD_PATH;
    public static final String AIRLINES_DESTINATIONS_ALL_PATH = AIRLINES_PATH + DESTINATIONS_ALL_PATH;
    public static final String AIRLINES_DESTINATIONS_DELETE_PATH = AIRLINES_PATH + DESTINATIONS_DELETE_PATH;

    public static final String AIRLINES_AIRCRAFT_PATH = AIRLINES_PATH + AIRCRAFT_PATH;
    public static final String AIRLINES_AIRCRAFT_ADD_PATH = AIRLINES_PATH + AIRCRAFT_ADD_PATH;
    public static final String AIRLINES_AIRCRAFT_SELL_PATH = AIRLINES_PATH + AIRCRAFT_SELL_PATH;
    public static final String AIRLINES_AIRCRAFT_DELETE_PATH = AIRLINES_PATH + AIRCRAFT_DELETE_PATH;

}
