package com.avasthi.varahamihir.identityserver.utils;

public class Paths {

    public static final int defaultPageNumber = 0;
    public static final int defaultPageSize = 100;
    public static final String basePath = "/api";
    public static final String idPathVariable = "id";
    public static final String idPath = "{"+idPathVariable+"}";
    public static final String pageVariable = "page";
    public static final String pagePath = "{"+pageVariable+"}";
    public static final String sizeVariable = "size";
    public static final String sizePath = "{"+sizeVariable+"}";
    public static final String pageAndSizePath = pagePath + "/" + sizePath;
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
            public static final String fullPathWildcard = Content.fullPath + "/**";
            public static final String multiple = "multiple";
            public static final String contentId = "contentId";
            public static final String filename = "filename";
            public static final String contentPath = "/{" + contentId + "}/{" + filename + "}";
        }
        public class Embed {

            public static final String Base = "/embed";
            public static final String fullPath = Paths.V1.fullPath + Base;
            public static final String fullPathWildcard = Embed.fullPath + "/**";
            public static final String embedId = "embedId";
            public static final String filename = "filename";
            public static final String contentPath = "/{" + embedId + "}";
        }
        public class Recipe {

            public static final String Base = "/recipe";
            public static final String fullPath = Paths.V1.fullPath + Base;
        }
        public class Ingredient {

            public static final String Base = "/ingredient";
            public static final String fullPath = Paths.V1.fullPath + Base;
        }
        public class Unit {

            public static final String Base = "/unit";
            public static final String fullPath = Paths.V1.fullPath + Base;
            public static final String unitType = "unitType";
            public static final String unitTypePath = "{" + unitType + "}";
        }
        public class RDI {

            public static final String Base = "/rdi";
            public static final String fullPath = Paths.V1.fullPath + Base;
        }
        public class Equation {

            public static final String Base = "/equation";
            public static final String fullPath = Paths.V1.fullPath + Base;
            public static final String fullPathWildcard = Equation.fullPath + "/**";
        }
    }
}
