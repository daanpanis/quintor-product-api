package nl.quintor.dpanis.productapi.constants;

public class AuthenticationConstants {

    public static final String ROLE_USER = "user";
    public static final String ROLE_MODERATOR = "moderator";
    public static final String ROLE_ADMINISTRATOR = "administrator";

    // Product management privileges
    public static final String PRIVILEGE_PRODUCT_CREATE = "product.create";
    public static final String PRIVILEGE_PRODUCT_UPDATE = "product.update";
    public static final String PRIVILEGE_PRODUCT_GET = "product.get";
    public static final String PRIVILEGE_PRODUCT_LIST = "product.list";
    public static final String PRIVILEGE_PRODUCT_DELETE = "product.delete";

    // Category management privileges
    public static final String PRIVILEGE_CATEGORY_CREATE = "category.create";
    public static final String PRIVILEGE_CATEGORY_UPDATE = "category.update";
    public static final String PRIVILEGE_CATEGORY_GET = "category.get";
    public static final String PRIVILEGE_CATEGORY_LIST = "category.list";
    public static final String PRIVILEGE_CATEGORY_DELETE = "category.delete";

    // Cart management privilege
    public static final String PRIVILEGE_CART_MANAGE = "cart.manage";

    // User management privilege
    public static final String PRIVILEGE_USER_CHANGE_ROLE = "user.role.change";

    // Role management
    public static final String PRIVILEGE_ROLE_CREATE = "role.create";
    public static final String PRIVILEGE_ROLE_UPDATE = "role.update";
    public static final String PRIVILEGE_ROLE_GET = "role.get";
    public static final String PRIVILEGE_ROLE_LIST = "role.list";
    public static final String PRIVILEGE_ROLE_DELETE = "role.delete";

    // Privilege management
    public static final String PRIVILEGE_PRIVILEGE_LIST = "privilege.list";

    public static String[] ALL_PRIVILEGES = new String[]{
            PRIVILEGE_PRODUCT_CREATE,
            PRIVILEGE_PRODUCT_UPDATE,
            PRIVILEGE_PRODUCT_GET,
            PRIVILEGE_PRODUCT_LIST,
            PRIVILEGE_PRODUCT_DELETE,
            PRIVILEGE_CATEGORY_CREATE,
            PRIVILEGE_CATEGORY_UPDATE,
            PRIVILEGE_CATEGORY_GET,
            PRIVILEGE_CATEGORY_LIST,
            PRIVILEGE_CATEGORY_DELETE,
            PRIVILEGE_CART_MANAGE,
            PRIVILEGE_USER_CHANGE_ROLE,
            PRIVILEGE_ROLE_CREATE,
            PRIVILEGE_ROLE_UPDATE,
            PRIVILEGE_ROLE_GET,
            PRIVILEGE_ROLE_LIST,
            PRIVILEGE_ROLE_DELETE,
            PRIVILEGE_PRIVILEGE_LIST
    };

    private AuthenticationConstants() {
    }
}
