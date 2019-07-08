package cn.itcast;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class QueryIndex {
    @Test
    public  void  testQueryIndex() throws Exception {
        //分词器创建成功
        QueryParser queryParser = new QueryParser("title",new StandardAnalyzer());
        //查询的字符串
        Query query = queryParser.parse("谷歌地图之父拉斯");
        search(query);
    }
    //词条查询
    //精确查询
    @Test
    public  void  testTermQuery() throws IOException {
        Query query = new TermQuery(new Term("title","facebook"));
        search(query);
    }
    //通配符查询
    //?匹配一个字符  *匹配n个字符
    @Test
    public  void  testWildCardQuery() throws IOException {
        Query query = new WildcardQuery(new Term("title","?父*"));
        search(query);
    }
    //模糊查询
    //只能匹配少于2个字符错误的模糊查询
    @Test
    public  void  testFuzzyQuery() throws IOException {
        Query query = new FuzzyQuery(new Term("title","facebook"));
        search(query);
    }
    //范围查询
    @Test
    public  void  testNumericRangeQuery() throws IOException {
        Query query = NumericRangeQuery.newIntRange("id",1,3,false,true);
        search(query);
    }
    //布尔查询
    @Test
    public  void  testBooleanQuery() throws IOException {
        Query query1 = NumericRangeQuery.newIntRange("id",1,3,false,true);
        Query query2 = NumericRangeQuery.newIntRange("id",2,4,true,true);
        BooleanQuery query = new BooleanQuery();
        //Occur.MUST 相当于and
        //SHOULD 相当于 or
        //MUST_NOT 相当于非 不要此结果
        query.add(query1, BooleanClause.Occur.SHOULD);
        query.add(query2, BooleanClause.Occur.SHOULD);
        search(query);
    }


    private void search(Query query) throws IOException {
        //1.需要索引库
        Directory directory =  FSDirectory.open(new File("c://index/indexwriter"));
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建索引对象完成
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //2.需要查询索引器
        TopDocs topDocs = indexSearcher.search(query, 10);
        //取得查询的文档集合
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        System.out.println("查询总条数："+topDocs.totalHits);
        //3.读出文件，进行迭代展示
        for (ScoreDoc scoreDoc : scoreDocs) {
            //读取文件内容
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println("文档id："+document.get("id"));
            System.out.println("文档title："+document.get("title"));
        }
    }
}
