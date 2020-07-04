import org.apache.commons.lang3.StringUtils;

public class strWork {


    public static void main(String[] args) {
        // write your code here
        String strBase = "xyzzy";
        String strRem = "Y";
        System.out.println(withoutString(strBase, strRem));
    }

    public static String withoutString(String base, String remove) {
       return StringUtils.replaceIgnoreCase(base, remove, "");
       //withoutString("xyzZy", "Y") → "xzz"

    }

}

