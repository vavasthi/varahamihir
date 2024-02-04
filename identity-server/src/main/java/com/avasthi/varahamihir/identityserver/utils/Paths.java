package com.avasthi.varahamihir.identityserver.utils;

public class Paths {

    public static final String basePath = "/api";
    public class V1 {
        public static final String Base = basePath + "/v1";
        public static final String fullPath = Base;
        public class Users {

            public static final String Base = "/users";
            public static final String fullPath = Paths.V1.fullPath + Base;
            public static final String GetOne = "{username}";
        }
        public class Auth {

            public static final String Base = "/auth";
            public static final String fullPath = Paths.V1.fullPath + Base;
            public static final String Refresh = "refresh";
        }
        public class Content {

            public static final String Base = "/content";
            public static final String fullPath = Paths.V1.fullPath + Base;
            public static final String contentId = "contentId";
            public static final String filename = "filename";
            public static final String contentPath = "/{" + contentId + "}/{" + filename + "}";
        }
    }
}
