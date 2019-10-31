package com.wjj.wm.demo.lucence;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @ClassName Indexer
 * @Description 建立索引
 * @Author weng_jun_ji_
 * @Date 2019/9/30 16:56
 * @Vervsion 1.0
 */
@Slf4j
public class Indexer {

    //写索引实例
    private IndexWriter writer;

    /**
     * @Description:初始化标准分词器和写索引实例
     * @Date&Author 2019/9/30 by weng_jun_ji_
     * @Param [indexDir]
     * @Return
     **/
    public Indexer(String indexDir) throws Exception {
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        //标准分词器,会自动去掉空格,is a the等单词
        Analyzer analyzer = new StandardAnalyzer();
        //将标准分词器 配到 写索引 的配置中
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //实例化 写索引 对象
        writer = new IndexWriter(dir, config);
    }

    /**
     * @Description:索引指定目录下的所有文件
     * @Date&Author 2019/9/30 by weng_jun_ji_
     * @Param [dataDir]
     * @Return
     **/
    private int indexAll(String dataDir) throws Exception {
        //获取该路径下的所有文件
        File file = new File(dataDir);
        File[] files = file.listFiles();
        if (null != files) {
            for (File f : files) {
                indexFile(f);
            }
        }
        return writer.numDocs();
    }

    /**
     * @Description:索引文件
     * @Date&Author 2019/9/30 by weng_jun_ji_
     * @Param [file]
     * @Return
     **/
    private void indexFile(File file) throws Exception {
        log.info("索引文件的路径: {}", file.getCanonicalPath());
        //获取该文件的Document对象
        Document doc = getDocument(file);
        //将doc添加到索引中
        writer.addDocument(doc);
    }

    /**
     * @Description:获取文档Document,文档里再设置每个字段,类似于db中的一行记录
     * @Date&Author 2019/9/30 by weng_jun_ji_
     * @Param [file]
     * @Return
     **/
    private Document getDocument(File file) throws Exception {
        Document doc = new Document();
        //开始添加字段
        //添加内容
        doc.add(new TextField("contents", new FileReader(file)));
        //添加文件名, 并将此字段存到索引文件中
        doc.add(new TextField("fileName", file.getName(), Field.Store.YES));
        //添加文件路径
        doc.add(new TextField("fullPath", file.getCanonicalPath(), Field.Store.YES));
        return doc;
    }

    public static void main(String[] args) {
        //索引保存到的路径
        String indexDir = "E:\\lucence";
        //需要索引的文件数据存放的目录
        String dataDir = "E:\\lucence\\data";
        Indexer indexer = null;
        int indexedNum = 0;
        //记录索引开始时间
        long startTime = System.currentTimeMillis();
        try {
            // 开始构建索引
            indexer = new Indexer(indexDir);
            indexedNum = indexer.indexAll(dataDir);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != indexer) indexer.close();
        }
        //记录索引结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("索引耗时" + (endTime - startTime) + "毫秒");
        System.out.println("共索引了" + indexedNum + "个文件");
    }

    private void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
