package org.marker.mushroom.utils;

import java.util.Properties;


public class PropertyUtil
{


	private Properties properties;


	public void setProperties(final Properties properties)
	{
		this.properties = properties;
	}

	public String getValue(final String key)
	{
		return properties.getProperty(key);
	}

	public int getIntValue(final String key)
	{
		return Integer.parseInt(properties.getProperty(key));
	}

}
