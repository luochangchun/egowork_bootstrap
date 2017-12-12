package org.marker.mushroom.beans;

import java.io.Serializable;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 文章对象
 * 
 * @author marker
 */
@Entity("dictionaries")
public class Dictionaries implements Serializable
{

	private static final long serialVersionUID = 4016075914908473684L;

	/** 字典Id **/
	private int id;
	/** 对应字段名称 **/
	private String name;
	/** 对应字段值 **/
	private String value;
	/** 实体类型 **/
	private String type;


	public int getId()
	{
		return id;
	}

	public void setId(final int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(final String value)
	{
		this.value = value;
	}

	public String getType()
	{
		return type;
	}

	public void setType(final String type)
	{
		this.type = type;
	}
}
