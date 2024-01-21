package com.avasthi.varahamihir.identityserver.utils;

public class Paths {

    public static final String basePath = "/api";
    public class Users {
        public static final String Base = basePath + "/v1";
        public static final String fullPath = Base;
        public class V1 {

            public static final String Base = "/users";
            public static final String fullPath = Users.fullPath + Base;
            public static final String GetOne = "/users/{username}";
        }
    }
}
