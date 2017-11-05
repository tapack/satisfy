package io.tapack.satisfy.props;

public enum WebProperty {

    /**
     * id of AJAX loader on web page
     */
    AJAX_LOADER_ID,
    /**
     * The default starting URL for the application, and base URL for
     * relative paths.
     */
    WEBDRIVER_BASE_URL,
    /**
     * How long should the driver wait for elements not immediately visible,
     * in seconds.
     */
    SATISFY_TIMEOUT,
    /**
     * Property indicates use web driver or not
     */
    WEBDRIVER_REMOTE_URL;

    public String toString() {
        return super.toString().toLowerCase().replaceAll("_", ".");
    }

}
