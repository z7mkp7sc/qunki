package com.wjj.wm.demo.lucence;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @ClassName Searcher
 * @Description 检索程序
 * @Author weng_jun_ji_
 * @Date 2019/9/30 17:34
 * @Vervsion 1.0
 */
@Slf4j
public class Searcher {

    /**
     * @Description:检索程序
     * @Date&Author 2019/9/30 by weng_jun_ji_
     * @Param [
     * indexDir 索引所在的位置,
     * qStr 待查的字符串
     * ]
     * @Return
     **/
    public static void search(String indexDir, String qStr) throws Exception {
        //获取要查询的路径(索引所在的位置)
        FSDirectory dir = FSDirectory.open(Paths.get(indexDir));
        DirectoryReader reader = DirectoryReader.open(dir);
        //构建IndexSearcher
        IndexSearcher searcher = new IndexSearcher(reader);
        //标准分词器,会自动去掉空格,is the a等单词
        Analyzer analyzer = new StandardAnalyzer();
        //查询解析器(classic包)
        QueryParser parser = new QueryParser("contents", analyzer);
        //获取查询对象
        Query query = parser.parse(qStr);

        long start = System.currentTimeMillis();
        //开始查询, 查询前10条数据, 并将记录保存到 docs中
        TopDocs docs = searcher.search(query, 10);
        long end = System.currentTimeMillis();
        log.info("匹配 {} 共耗时 {} 毫秒...", qStr, (end - start));
        log.info("查询到的记录数: {}", docs.totalHits);

        //取出查询结果
        ScoreDoc[] scoreDocs = docs.scoreDocs;
        if (null != scoreDocs) {
            if (scoreDocs.length > 0) {
                for (ScoreDoc sD : scoreDocs) {
                    int docId = sD.doc;//相当于docId,根据此docId来获取文档
                    Document document = searcher.doc(docId);
                    log.info(document.get("fullPath"));
                }
            }
        }

        reader.close();
    }


    public static void main(String[] args) {

        String indexDir = "E:\\lucence";
        //查询的字符串
        String qStr = "";

        Scanner sc = new Scanner(System.in);
        System.out.println("输入要查找的字符串: ");

        if (sc.hasNext()) {
            qStr = sc.nextLine();
            try {
                search(indexDir, qStr);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        sc.close();
    }
}
