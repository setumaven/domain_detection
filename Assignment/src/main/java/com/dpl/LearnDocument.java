package com.dpl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LearnDocument {
	Path filePath = null;
	List<String> strLineList = null;
	Map<String, Integer> tockenMap = null;
	Integer totalWords = null;

	LearnDocument(String filePathStr) {
		filePath = Paths.get(filePathStr);

	}

	public void loadFile() throws Exception {
		if (!Files.isReadable(filePath)) {
			throw new Exception("File can not be read! File:" + filePath);
		}
		strLineList = Files.readAllLines(filePath, StandardCharsets.US_ASCII);
		if (strLineList == null) {
			throw new Exception("File data can not be read in String! File:" + filePath);
		}

	}

	public void preFilter() throws Exception {
		if (strLineList == null) {
			loadFile();
		}
		tockenMap = PreFilterUtills.getTokens(strLineList);
	}

	public Map<String, Integer> getTockenMap() {
		return tockenMap;
	}

	public Integer getTotalWords() {
		if (totalWords == null) {
			totalWords = 0;
			for (Entry<String, Integer> key : getTockenMap().entrySet()) {
				totalWords = totalWords + key.getValue();
			}
		}
		return totalWords;
	}

	public double geTfIdf(String inputstr) {

		double matchedCount = getTockenMap().get(inputstr) == null ? 0.1 : getTockenMap().get(inputstr);

		return matchedCount * (Math.log10(getTotalWords() / matchedCount) / Math.log10(2));

	}
}
