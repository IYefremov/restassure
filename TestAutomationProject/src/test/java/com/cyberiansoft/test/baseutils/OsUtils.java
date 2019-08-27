package com.cyberiansoft.test.baseutils;

public class OsUtils {
	private static String OS = null;

	public static String getOsName() {
		if (OS == null) {
			OS = System.getProperty("os.name");
		}
		return OS;
	}

	public static boolean isWindows() {
		return getOsName().startsWith("Windows");
	}

	public static boolean isMacOS() {
		return getOsName().startsWith("Mac");
	}

	//TODO test *nix systems
	public static String getOSSafePath(final String originalPath) {
		if (isMacOS())
			return String.format("%s%s%s", System.getProperty("user.dir"), "/../", originalPath);
		else
			return originalPath;

	}
}