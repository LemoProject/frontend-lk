package de.lemo.dms.db;

import java.util.HashMap;

/**
 * The Class DBConfigObject. This class has been created to give users the possibility to save and submit parameters for
 * the database connection.
 */
public class DBConfigObject {

    private HashMap<String, String> properties = new HashMap<String, String>();

    /**
     * Adds a key-value-pair to the property list.
     * 
     * @param property
     *            name of the database addressed parameter
     * @param value
     *            designated value of the parameter
     */
    public void addProperty(String property, String value)
    {
        this.properties.put(property, value);
    }

    /**
     * Returns all properties as key-value-pairs in a HashMap.
     * 
     * @return A HashMap holding the properties.
     */
    public HashMap<String, String> getProperties()
    {
        return this.properties;
    }

    /**
     * Deletes all properties.
     */
    public void clearProperties()
    {
        this.properties.clear();
    }

    /**
     * Returns the value of the given property.
     * 
     * @param property
     * @return the value saved for the property. If the property is unknown, it will return NULL
     */
    public String getPropertyValue(String property)
    {
        return this.properties.get(property);
    }

    public void deleteProperty(String property)
    {
        this.properties.remove(property);
    }

}
