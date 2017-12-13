package org.marker.mushroom.dao;

import org.marker.mushroom.beans.Article;

public interface IArticleDao extends ISupportDao {

	boolean update(Article entity);

}
