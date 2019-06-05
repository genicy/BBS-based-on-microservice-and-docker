package com.xucheng.search.dao;

import com.xucheng.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/22 15:04
 * @Version: 1.0
 */
public interface ArticleDao extends ElasticsearchRepository<Article, String> {

    public Page<Article> findByTitleLikeOrContentLike( String title, String content,Pageable pageable);
}
