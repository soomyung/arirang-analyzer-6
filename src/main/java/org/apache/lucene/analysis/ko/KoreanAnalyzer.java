package org.apache.lucene.analysis.ko;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.ClassicFilter;


/**
 * A Korean Analyzer
 */
public class KoreanAnalyzer extends Analyzer {
  
 private final CharArraySet stopWords;
	  
  private boolean bigrammable = false;
    
  private boolean hasOrigin = false;
    
  private boolean exactMatch = false;
  private boolean originCNoun = true;
  private boolean queryMode = false;
  private boolean wordSegment = false;
  private boolean decompound = true;
  
  public KoreanAnalyzer() {
	  stopWords = new CharArraySet(0, true);
  }
  
  public KoreanAnalyzer(String[] words, boolean ignoreCase) {
	  stopWords = StopFilter.makeStopSet(words, ignoreCase);
  }
  
  @Override
  protected TokenStreamComponents createComponents(final String fieldName) {
    final KoreanTokenizer src = new KoreanTokenizer();
    TokenStream tok = new LowerCaseFilter(src);
    tok = new ClassicFilter(tok);
    tok = new KoreanFilter(tok, bigrammable, hasOrigin, exactMatch, originCNoun, queryMode, decompound);
    if(wordSegment) tok = new WordSegmentFilter(tok, hasOrigin);
    tok = new HanjaMappingFilter(tok);
    tok = new PunctuationDelimitFilter(tok);
    tok = new StopFilter(tok, stopWords);

    return new TokenStreamComponents(src, tok) {
      @Override
      protected void setReader(final Reader reader)  {
        super.setReader(reader);
      }
    };
	    
  }
    
  /**
   * determine whether the bigram index term is returned or not if a input word is failed to analysis
   * If true is set, the bigram index term is returned. If false is set, the bigram index term is not returned.
   */
  public void setBigrammable(boolean is) {
    bigrammable = is;
  }
  
  /**
   * determin whether the original term is returned or not if a input word is analyzed morphically.
   */
  public void setHasOrigin(boolean has) {
    hasOrigin = has;
  }

  /**
   * determin whether the original compound noun is returned or not if a input word is analyzed morphically.
   */
  public void setOriginCNoun(boolean cnoun) {
    originCNoun = cnoun;
  }
  
  /**
   * determin whether the original compound noun is returned or not if a input word is analyzed morphically.
   */
  public void setExactMatch(boolean exact) {
    exactMatch = exact;
  }
  
  /**
   * determin whether the analyzer is running for a query processing
   */
  public void setQueryMode(boolean mode) {
    queryMode = mode;
  }

  public void setDecompound(boolean is) {
	  this.decompound = is;
  }
  
  /**
   * determin whether word segment analyzer is processing
   */
	public boolean isWordSegment() {
		return wordSegment;
	}
	
	public void setWordSegment(boolean wordSegment) {
		this.wordSegment = wordSegment;
	}
  
}
