package sustech.cs304.entity;

/**
 * Represents a key-value pair query parameter.
 * Used for storing and retrieving query information.
 */
public class Query {
    private String key;
    private String value;

    /**
     * Constructs a new Query with the specified key and value.
     *
     * @param key   the key of the query parameter
     * @param value the value of the query parameter
     */
    public Query(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key of the query parameter.
     *
     * @return the query key
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value of the query parameter.
     *
     * @return the query value
     */
    public String getValue() {
        return value;
    }
}
