package com.dpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class PreFilterUtills {
	static String filePathStr = "./src/main/config/stop_word.properties";
	static String[] removefix = { ".", "?", "!", ",", "+", "=", "-", ";", ":", "\"", "[", "]", "{", "}", "(", ")", "*" };

	static Set<String> removeWords = new HashSet<String>();
	static {
		List<String> rmwords = new ArrayList<String>();
		try {
			rmwords = Files.readAllLines(Paths.get(filePathStr), StandardCharsets.US_ASCII);
		} catch (IOException e) {
			// TODO
		}
		removeWords.addAll(rmwords);
	}

	public static Map<String, Integer> getTokens(Collection<String> strLines) {
		Map<String, Integer> wordMapCount = new HashMap<String, Integer>();
		for (String lineStr : strLines) {
			for (String word : StringUtils.split(lineStr)) {
				if (StringUtils.isNotEmpty(word) && StringUtils.isAlpha(word) && !removeWords.contains(word.trim().toLowerCase())) {
					addIntoMapCount(wordMapCount, word.trim().toLowerCase());
				}
			}
		}
		// TODO

		return wordMapCount;
	}

	public static void addIntoMapCount(Map<String, Integer> hm, String word) {
		if (!hm.containsKey(word)) {
			hm.put(word, 1);
		} else {
			hm.put(word, (Integer) hm.get(word) + 1);
		}
	}
}
