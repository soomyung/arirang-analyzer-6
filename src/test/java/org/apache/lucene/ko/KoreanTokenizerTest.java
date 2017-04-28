package org.apache.lucene.ko;

import java.io.StringReader;

import org.apache.lucene.analysis.ko.KoreanTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Before;
import org.junit.Test;

public class KoreanTokenizerTest {

	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testTokenize() throws Exception {
		
		String[] lines = new String[]{
			"노벨은"	
		};
		
		KoreanTokenizer tokenizer = new KoreanTokenizer();

        
		for(String line : lines) {
			
            StringReader reader = new StringReader(line);
            tokenizer.setReader(reader);
            tokenizer.reset(); // need to call the 'reset' after setting reader.
            
            CharTermAttribute termAtt = tokenizer.addAttribute(CharTermAttribute.class);

            StringBuffer sb = new StringBuffer();
            while(tokenizer.incrementToken()) {
                if(sb.length()>0) sb.append("/");
                sb.append(termAtt.toString());
            }
            
            System.out.println(sb.toString());
        }
	}
}
