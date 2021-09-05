package com.github.zuofengzhang.flake.client.service.impl;

import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com.github.zuofengzhang.flake.client.constraints.FlakeConsts.INDEX_BASE_PATH;

@Component
public class LuceneIndexer {
    public boolean addTask(TaskDto taskDto) throws IOException {
        Directory         directory = FSDirectory.open(Paths.get(INDEX_BASE_PATH));
        Analyzer          analyzer  = new SmartChineseAnalyzer();
        IndexWriterConfig config    = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(directory, config);
//        if (indexWriter.isOpen()) {
//            throw new IllegalStateException("Index is opening.");
//        }
        Document document = taskDtoToDocument(taskDto);
        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();
        return true;
    }

    private Document taskDtoToDocument(TaskDto taskDto) {
        Document doc = new Document();
        //添加字段
        doc.add(new IntField("id", taskDto.getTaskId(), Field.Store.YES));
        //添加内容
        String title = taskDto.getTitle();
        if (StringUtils.isNotBlank(title)) {
            doc.add(new TextField("title", title, Field.Store.YES));
        }
        //添加文件名，并把这个字段存到索引文件里
        String content = taskDto.getContent();
        if (StringUtils.isNotBlank(content)) {
            doc.add(new TextField("tcontent", content, Field.Store.YES));
        }
        return doc;
    }

    public List<Integer> search(String queryStr) throws IOException, ParseException {
        Directory       directory = FSDirectory.open(Paths.get(INDEX_BASE_PATH));
        DirectoryReader reader    = DirectoryReader.open(directory);
        IndexSearcher   searcher  = new IndexSearcher(reader);
        Analyzer        analyzer  = new SmartChineseAnalyzer();
        QueryParser     parser    = new QueryParser("title", analyzer);
        Query           query     = parser.parse(queryStr);

        long    startTime = System.currentTimeMillis();
        TopDocs docs      = searcher.search(query, 10);

        System.out.println("查找" + queryStr + "所用时间：" + (System.currentTimeMillis() - startTime));
        System.out.println("查询到" + docs.totalHits + "条记录");

        //加入高亮显示的
//        QueryScorer         scorer              = new QueryScorer(query);
// 计算查询结果最高的得分
        List<Integer> list = Lists.newArrayList();
        //遍历查询结果
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            String   id  = doc.get("id");
            if (id != null) {
                int i = Integer.parseInt(id);
                list.add(i);
            }
        }
        reader.close();
        return list;
    }

    public static void main(String[] args) throws IOException, ParseException {
        LuceneIndexer luceneIndexer = new LuceneIndexer();
        luceneIndexer.addTask(TaskDto.builder().taskId(1024).title("我是中国人").build());
        List<Integer> list             = luceneIndexer.search("我");
        list.forEach(System.out::println);
    }

}