package cn.itcast;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.lucene.document.Field.Store;

public class CreateIndex {
    @Test
    public  void testCreateIndex() throws  Exception {
        //1.提供文档内容
        Document document = new Document();
        //添加文档字段
        document.add(new StringField("id","1", Field.Store.YES));
        document.add(new TextField("title","谷歌地图之父跳槽facebook, 加入了传智播客，屌爆了啊", Field.Store.NO));
        document.add(new StoredField("url","http://www.jd.com/431"));
        //2.索引器写出
        //2.1 索引的目录
        Directory directory =  FSDirectory.open(new File("c://index/indexwriter"));
        //2.2配置的参数
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,new IKAnalyzer());
        //CREATE 每次創建
            //REATE_OR_APPEND  如果有目錄那麼就追加
            //APPEND  追加
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(directory,config);
        //3.索引器提交关闭
        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();
    }
    @Test
    public  void testCreateIndexs() throws  Exception {
        //1.提供文档内容
        Collection docs = new ArrayList();
        // 创建文档对象
        Document document1 = new Document();
        document1.add(new IntField("id", 1, Field.Store.YES));
        document1.add(new TextField("title", "谷歌地图之父跳槽facebook", Store.YES));
        docs.add(document1);
        // 创建文档对象
        Document document2 = new Document();
        document2.add(new IntField("id", 2, Store.YES));
        document2.add(new TextField("title", "谷歌地图之父加盟FaceBook", Store.YES));
        docs.add(document2);
        // 创建文档对象
        Document document3 = new Document();
        document3.add(new IntField("id", 3, Store.YES));
        document3.add(new TextField("title", "谷歌地图创始人拉斯离开谷歌加盟Facebook", Store.YES));
        docs.add(document3);
        // 创建文档对象
        Document document4 = new Document();
        document4.add(new IntField("id", 4, Store.YES));
        document4.add(new TextField("title", "谷歌地图之父跳槽Facebook与Wave项目取消有关", Store.YES));
        docs.add(document4);
        // 创建文档对象
        Document document5 = new Document();
        document5.add(new IntField("id", 5, Store.YES));
        document5.add(new TextField("title", "谷歌地图之父拉斯加盟社交网站Facebook", Store.YES));
        docs.add(document5);
        //2.索引器写出
        //2.1 索引的目录
        Directory directory =  FSDirectory.open(new File("c://index/indexwriter"));
        //2.2配置的参数
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,new IKAnalyzer());
        //CREATE 每次創建
        //REATE_OR_APPEND  如果有目錄那麼就追加
        //APPEND  追加
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(directory,config);
        //3.索引器提交关闭
        indexWriter.addDocuments(docs);
//        indexWriter.updateDocument();
//        indexWriter.deleteAll();
        indexWriter.commit();
        indexWriter.close();
    }
}
