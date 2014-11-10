

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
	
   String indexDir = "/home/prabhjot/Desktop/minor/cacm";
   String dataDir = "/home/prabhjot/Desktop/minor/cacm";
   Searcher searcher;

   public static void main(String[] args) {
      LuceneTester tester;
      try {
         tester = new LuceneTester();
         tester.searchUsingFuzzyQuery("cord3.txt");
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }
   private void searchUsingFuzzyQuery(String searchQuery)
      throws IOException, ParseException{
      searcher = new Searcher(indexDir);
      long startTime = System.currentTimeMillis();
      //create a term to search file name
      Term term = new Term(LuceneConstants.FILE_NAME, searchQuery);
      //create the term query object
      Query query = new FuzzyQuery(term);
      //do the search
      TopDocs hits = searcher.search(query);
      long endTime = System.currentTimeMillis();

      System.out.println(hits.totalHits +
         " documents found. Time :" + (endTime - startTime) + "ms");
      for(ScoreDoc scoreDoc : hits.scoreDocs) {
         Document doc = searcher.getDocument(scoreDoc);
         System.out.print("Score: "+ scoreDoc.score + " ");
         System.out.println("File: "+ doc.get(LuceneConstants.FILE_PATH));
      }
      searcher.close();
   }
}