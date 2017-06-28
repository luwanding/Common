package org.ansj.solr;

import com.mlcs.search.mlcsseg.lucene.ReloadableTokenizerFactory;
import com.mlcs.search.mlcsseg.lucene.ReloaderRegister;
import org.ansj.library.UserDefineLibrary;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.util.AttributeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;


public class AnsjTokenizerFactorys extends ReloadableTokenizerFactory {

	private int analysisType = 0;
	private boolean rmPunc = true;
		
	public AnsjTokenizerFactorys(Map<String, String> args) {
		super(args);
		analysisType = getInt(args, "analysisType", 0);
		rmPunc = getBoolean(args, "rmPunc", true);
		System.out.println(":::ansj:construction::::::::::::::::::::::::::" + conf);
	}



	public void inform(ResourceLoader loader) throws IOException {
		System.out.println(":::ansj:::inform::::::::::::::::::::::::" + conf);
		ReloaderRegister.register(this, loader, conf);		
	}

	@Override
	public Tokenizer create(AttributeFactory factory, Reader input) {
		return new AnsjTokenizer(input, analysisType, rmPunc);
	}

	@Override
	public void update(List<InputStream> inputStreams) {
		if (inputStreams!= null){
			//UserDefineLibrary.reloadMainAndAdd(inputStreams);
		}
	}
}
