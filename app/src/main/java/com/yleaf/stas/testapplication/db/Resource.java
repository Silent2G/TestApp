package com.yleaf.stas.testapplication.db;

public class Resource {
    public static final String DB_NAME = "com.yleaf.stas.testapplication";
    public static final int DB_VERSION = 1;

    public static final class Book {
        public static final String TABLE_NAME = "book";

        public static final String ID = "id";
        public static final String KIND = "kind";
        public static final String ARTIST_NAME = "artist_name";
        public static final String NAME = "name";
        public static final String ART_WORK_URL = "art_work_url";

        public static final String CREATE_TABLE =
                "CREATE TABLE "
                + TABLE_NAME
                + " ("
                + ID
                + " INTEGER PRIMARY KEY, "
                + KIND
                + " TEXT(20), "
                + ARTIST_NAME
                + " TEXT(100), "
                + NAME
                + " TEXT(300), "
                + ART_WORK_URL
                + " TEXT(300));";
    }

    public static final class Movie {
        public static final String TABLE_NAME = "movie";

        public static final String ID = "id";
        public static final String KIND = "kind";
        public static final String ARTIST_NAME = "artist_name";
        public static final String NAME = "name";
        public static final String ART_WORK_URL = "art_work_url";

        public static final String CREATE_TABLE =
                "CREATE TABLE "
                        + TABLE_NAME
                        + " ("
                        + ID
                        + " INTEGER PRIMARY KEY, "
                        + KIND
                        + " TEXT(20), "
                        + ARTIST_NAME
                        + " TEXT(100), "
                        + NAME
                        + " TEXT(300), "
                        + ART_WORK_URL
                        + " TEXT(300));";
    }

    public static final class Podcast {
        public static final String TABLE_NAME = "podcast";

        public static final String ID = "id";
        public static final String KIND = "kind";
        public static final String ARTIST_NAME = "artist_name";
        public static final String NAME = "name";
        public static final String ART_WORK_URL = "art_work_url";

        public static final String CREATE_TABLE =
                "CREATE TABLE "
                        + TABLE_NAME
                        + " ("
                        + ID
                        + " INTEGER PRIMARY KEY, "
                        + KIND
                        + " TEXT(20), "
                        + ARTIST_NAME
                        + " TEXT(100), "
                        + NAME
                        + " TEXT(300), "
                        + ART_WORK_URL
                        + " TEXT(300));";
    }

    public static final class Favorite {
        public static final String TABLE_NAME = "favorite";

        public static final String ID = "id";
        public static final String KIND = "kind";
        public static final String ARTIST_NAME = "artist_name";
        public static final String NAME = "name";
        public static final String ART_WORK_URL = "art_work_url";

        public static final String CREATE_TABLE =
                "CREATE TABLE "
                        + TABLE_NAME
                        + " ("
                        + ID
                        + " INTEGER PRIMARY KEY, "
                        + KIND
                        + " TEXT(20), "
                        + ARTIST_NAME
                        + " TEXT(100), "
                        + NAME
                        + " TEXT(300), "
                        + ART_WORK_URL
                        + " TEXT(300));";
    }
}
