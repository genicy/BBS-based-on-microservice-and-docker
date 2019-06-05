package com.xucheng.search.service;

import com.xucheng.search.dao.ArticleDao;
import com.xucheng.search.pojo.Article;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/22 15:07
 * @Version: 1.0
 */
@Service
public class ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public Page<Article> findByKey(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleDao.findByTitleLikeOrContentLike(key, key, pageable);
    }


/*    public List<Article> highLigthQuery(String field, String searchMessage) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery(field, searchMessage))
                .withHighlightFields(new HighlightBuilder.Field(field)).build();
        Page<Article> page = elasticsearchTemplate.queryForPage(searchQuery, Article.class, new SearchResultMapper() {

            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                ArrayList<Article> Articles = new ArrayList<Article>();
                SearchHits hits = response.getHits();
                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    Article Article = new Article();
                    String highLightMessage = searchHit.getHighlightFields().get(field).fragments()[0].toString();
                    Article.setId(searchHit.getId());
                    Article.setTitle(String.valueOf(searchHit.getSource().get("title")));
                    Article.setContent(String.valueOf(searchHit.getSource().get("content")));
                    // 反射调用set方法将高亮内容设置进去
                    try {
                        String setMethodName = field;
                        Class<? extends Article> ArticleClazz = Article.getClass();
                        Method setMethod = ArticleClazz.getMethod(setMethodName, String.class);
                        setMethod.invoke(Article, highLightMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Articles.add(Article);
                }
                if (Articles.size() > 0) {
                    return (AggregatedPage<T>) new PageImpl<T>((List<T>) Articles);
                }
                return null;
            }
        });
        List<Article> Articles = page.getContent();
        System.out.println(Articles.get(0).toString());
        return Articles;
    }*/
    /**
     * 从es检索数据
     *
     * @param content  搜索关键字
     * @param pageNum  页
     * @param pageSzie 条
     * @return
     */
    public AggregatedPage<Article> getArticleListBySrt(String content, int pageNum, int pageSzie) {
        Pageable pageable = PageRequest.of(pageNum, pageSzie);

        String preTag = "<font color='#dd4b39'>";//google的色值
        String postTag = "</font>";

        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(matchQuery("title", content)).
                withQuery(matchQuery("content", content)).
                withHighlightFields(new HighlightBuilder.Field("title").preTags(preTag).postTags(postTag),
                        new HighlightBuilder.Field("content").preTags(preTag).postTags(postTag)).build();
        searchQuery.setPageable(pageable);

        // 不需要高亮直接return ideas
        // AggregatedPage<Article> ideas = elasticsearchTemplate.queryForPage(searchQuery, Article.class);

        // 高亮字段
        AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(searchQuery, Article.class, new SearchResultMapper() {

            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<Article> chunk = new ArrayList<>();
                for (SearchHit searchHit : response.getHits()) {
                    if (response.getHits().getHits().length <= 0) {
                        return null;
                    }
                    Article article = new Article();
                    //name or memoe
                    HighlightField title = searchHit.getHighlightFields().get("title");
                    if (title != null) {
                        article.setTitle(title.fragments()[0].toString());
                    }
                    HighlightField content = searchHit.getHighlightFields().get("content");
                    if (content != null) {
                        article.setContent(content.fragments()[0].toString());
                    }

                    chunk.add(article);
                }
                if (chunk.size() > 0) {
                    System.out.println(new AggregatedPageImpl<>((List<T>) chunk));
                    return new AggregatedPageImpl<>((List<T>) chunk);
                }
                return null;
            }
        });
        return articles;
    }
}
