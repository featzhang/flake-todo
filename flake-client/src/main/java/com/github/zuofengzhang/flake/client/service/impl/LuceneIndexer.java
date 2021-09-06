package com.github.zuofengzhang.flake.client.service.impl;

import com.github.zuofengzhang.flake.client.entity.StoreStatus;
import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com.github.zuofengzhang.flake.client.constraints.FlakeConsts.INDEX_BASE_PATH;

@Component
public class LuceneIndexer {

    public static final String FIELD_ID           = "id";
    public static final String FIELD_TITLE        = "title";
    public static final String FIELD_CONTENT      = "t_content";
    public static final String FIELD_STORE_STATUS = "store_status";

    private static IndexWriter INDEX_WRITER;

    public static void main(String[] args) throws IOException, ParseException {
        LuceneIndexer luceneIndexer = new LuceneIndexer();
        luceneIndexer.updateTask(TaskDto.builder().taskId(1024).title("我是中国人，我深深的爱着我的祖国！").storeStatus(StoreStatus.YES).build());
        luceneIndexer.updateTask(TaskDto.builder().taskId(1025).title("他在哪里").storeStatus(StoreStatus.NO).build());
        luceneIndexer.updateTask(TaskDto.builder().taskId(1026).title("他在哪里").storeStatus(StoreStatus.YES).build());
        List<Integer> list = luceneIndexer.search("他 我", false);
        list.forEach(System.out::println);
    }

    public boolean updateTask(TaskDto taskDto) throws IOException {
        IndexWriter indexWriter = createIndexWriter();

        Document document = taskDtoToDocument(taskDto);
        Term     idTerm   = new Term(FIELD_ID, String.valueOf(taskDto.getTaskId()));
        indexWriter.updateDocument(idTerm, document);
        indexWriter.commit();
        indexWriter.close();

        System.out.println("is open: " + indexWriter.isOpen());

        return true;
    }

    private IndexWriter createIndexWriter() throws IOException {
        if (INDEX_WRITER != null && INDEX_WRITER.isOpen()) {
            return INDEX_WRITER;
        }
        Directory         directory = FSDirectory.open(Paths.get(INDEX_BASE_PATH));
        Analyzer          analyzer  = new SmartChineseAnalyzer();
        IndexWriterConfig config    = new IndexWriterConfig(analyzer);

        INDEX_WRITER = new IndexWriter(directory, config);
        return INDEX_WRITER;
    }

    public boolean addTask(TaskDto taskDto) throws IOException {
        IndexWriter indexWriter = createIndexWriter();

        Document document = taskDtoToDocument(taskDto);
        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();

        return true;
    }

    private Document taskDtoToDocument(TaskDto taskDto) {
        Document doc = new Document();
        //添加字段
        doc.add(new StringField(FIELD_ID, String.valueOf(taskDto.getTaskId()), Field.Store.YES));
        //添加内容
        String title = taskDto.getTitle();
        if (StringUtils.isNotBlank(title)) {
            doc.add(new TextField(FIELD_TITLE, title, Field.Store.YES));
        }
        //添加文件名，并把这个字段存到索引文件里
        String content = taskDto.getContent();
        if (StringUtils.isNotBlank(content)) {
            doc.add(new TextField(FIELD_CONTENT, content, Field.Store.YES));
        }

        doc.add(new TextField(FIELD_STORE_STATUS, String.valueOf(taskDto.getStoreStatus().getCode()), Field.Store.YES));

        return doc;
    }

    private IndexSearcher createIndexSearcher() throws IOException {
        Directory       directory = FSDirectory.open(Paths.get(INDEX_BASE_PATH));
        DirectoryReader reader    = DirectoryReader.open(directory);
        return new IndexSearcher(reader);
    }

    public List<Integer> search(String queryStr, boolean includeDeleted) throws IOException, ParseException {

        IndexSearcher searcher = createIndexSearcher();

        String[]              fields = {FIELD_TITLE, FIELD_CONTENT};
        BooleanClause.Occur[] occ    = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};

        Analyzer analyzer = new SmartChineseAnalyzer();

        Query query = MultiFieldQueryParser.parse(queryStr, fields, occ, analyzer);

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
            String   id  = doc.get(FIELD_ID);
            if (!includeDeleted) {
                String storeStatus = doc.get(FIELD_STORE_STATUS);
                if (String.valueOf(StoreStatus.NO.getCode()).equals(storeStatus)) {
                    // filter deleted task
                    continue;
                }
            }
            if (id != null) {
                int i = Integer.parseInt(id);
                list.add(i);
            }
        }

        searcher.getIndexReader().close();

        return list;
    }

}