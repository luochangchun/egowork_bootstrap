package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 30 on 2017/12/15.
 */
@Entity("incubator_photo")
public class IncubatorPhoto implements Serializable {

	private Integer id;
	private String uri;
	private Date time;
	private int incubatorId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getIncubatorId() {
		return incubatorId;
	}

	public void setIncubatorId(int incubatorId) {
		this.incubatorId = incubatorId;
	}
}
