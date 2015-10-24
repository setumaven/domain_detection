package com.dpl;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.log4j.Logger;

public class Similarity {
	final static Logger logger = Logger.getLogger(Similarity.class);

	/**
	 * 
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String[] arg) throws Exception {

		Map<String, Double> finalSimilarityMap = new HashMap<String, Double>();
		List<String> docOrder = new ArrayList<String>();
		String istr1 = "culture";// <<<<<<<<<<<< Provide your input string here
		// String istr2 = "ratio";
		Map<String, LearnDocument> ldocMap = new HashMap<String, LearnDocument>();

		File[] inputDocList = Paths.get("./src/main/input").toFile().listFiles();

		for (File doc : inputDocList) {
			LearnDocument ldoc = new LearnDocument(doc.toString());
			ldoc.preFilter();
			ldoc.getTockenMap();
			ldocMap.put(doc.getName(), ldoc);
		}

		logger.info("Number of documents learned:" + ldocMap.size());

		// calculate tfIdf vector
		RealVector wordVec = calculateTfIdfVector(istr1, ldocMap, docOrder);
		wordVec.unitize();

		logger.info("1 has higest similarity score ,0 has lowest similarity score");
		logger.info("For input word:: " + istr1);
		for (int i = 0; i < docOrder.size(); i++) {
			logger.info("Doc name:" + docOrder.get(i) + " has similarity score:" + wordVec.getEntry(i));
			finalSimilarityMap.put(docOrder.get(i), wordVec.getEntry(i));
		}

	}

	/**
	 * 
	 * @param istr
	 * @param ldocMap
	 * @param docOrder
	 * @return
	 */
	private static RealVector calculateTfIdfVector(String istr, Map<String, LearnDocument> ldocMap, List<String> docOrder) {
		RealVector wordVec = new ArrayRealVector(ldocMap.size());
		docOrder.clear();
		int idx = 0;
		for (Entry<String, LearnDocument> documentkey : ldocMap.entrySet()) {
			double tfidf = documentkey.getValue().geTfIdf(istr);
			docOrder.add(documentkey.getKey());
			wordVec.setEntry(idx, tfidf);
			idx++;
		}
		return wordVec;
	}

}
