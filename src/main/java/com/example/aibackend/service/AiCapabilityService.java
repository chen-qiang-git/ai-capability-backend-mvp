package com.example.aibackend.service;

import com.example.aibackend.exception.BusinessException;
import com.example.aibackend.service.dto.AskAnswer;
import com.example.aibackend.service.dto.AskRequest;
import com.example.aibackend.service.dto.SummaryRequest;
import com.example.aibackend.service.dto.SummaryResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AiCapabilityService {

    private static final Logger log = LoggerFactory.getLogger(AiCapabilityService.class);
    private static final Pattern SENTENCE_SPLIT = Pattern.compile("(?<=[.!?。！？])\\s*");
    private static final Set<String> STOP_WORDS = Set.of(
            "the", "a", "an", "is", "are", "was", "were", "to", "of", "and", "or", "in", "on",
            "for", "with", "that", "this", "it", "be", "as", "by", "at", "from", "what", "which",
            "who", "when", "how", "why", "does", "do", "did", "about", "can", "could", "should"
    );

    public SummaryResult summarize(SummaryRequest request) {
        log.info("summarize_started maxSentences={} textLength={}", request.maxSentences(), request.text().length());
        List<String> sentences = splitSentences(request.text());
        int sentenceCount = Math.min(request.maxSentences(), sentences.size());
        String summary = String.join(" ", sentences.subList(0, sentenceCount)).trim();
        List<String> keywords = extractKeywords(request.text(), 5);
        log.info("summarize_completed selectedSentences={} keywords={}", sentenceCount, keywords);
        return new SummaryResult(summary, sentenceCount, keywords);
    }

    public AskAnswer answer(AskRequest request) {
        log.info("qa_started questionLength={} documentLength={}",
                request.question().length(), request.document().length());
        List<String> sentences = splitSentences(request.document());
        if (sentences.isEmpty()) {
            throw new BusinessException("EMPTY_DOCUMENT", "Document must contain at least one sentence");
        }

        String bestSentence = sentences.stream()
                .max(Comparator.comparingInt(sentence -> scoreSentence(sentence, request.question())))
                .orElse(sentences.get(0));

        int score = scoreSentence(bestSentence, request.question());
        if (score == 0) {
            bestSentence = sentences.get(0);
        }

        List<String> evidence = sentences.stream()
                .sorted((left, right) -> Integer.compare(scoreSentence(right, request.question()),
                        scoreSentence(left, request.question())))
                .limit(Math.min(2, sentences.size()))
                .toList();

        String answer = buildAnswer(bestSentence, score);
        log.info("qa_completed score={} evidenceCount={}", score, evidence.size());
        return new AskAnswer(answer, evidence, score > 0 ? "high" : "low");
    }

    private String buildAnswer(String sentence, int score) {
        if (score > 0) {
            return "Based on the provided material, the most relevant answer is: " + sentence;
        }
        return "The document does not directly answer the question, so the response falls back to the opening sentence: "
                + sentence;
    }

    private int scoreSentence(String sentence, String question) {
        Set<String> questionTerms = normalizeTerms(question);
        Set<String> sentenceTerms = normalizeTerms(sentence);
        questionTerms.retainAll(sentenceTerms);
        return questionTerms.size();
    }

    private List<String> splitSentences(String text) {
        return Arrays.stream(SENTENCE_SPLIT.split(text.trim()))
                .map(String::trim)
                .filter(sentence -> !sentence.isBlank())
                .toList();
    }

    private List<String> extractKeywords(String text, int limit) {
        List<String> ranked = new ArrayList<>(normalizeTerms(text));
        ranked.sort(Comparator.naturalOrder());
        return ranked.stream().limit(limit).toList();
    }

    private Set<String> normalizeTerms(String text) {
        String cleaned = text.toLowerCase(Locale.ROOT).replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\u4e00-\\u9fa5]+", " ");
        String[] tokens = cleaned.trim().split("\\s+");
        Set<String> results = new LinkedHashSet<>();
        for (String token : tokens) {
            if (token.isBlank()) {
                continue;
            }
            if (token.length() == 1 && token.chars().allMatch(Character::isLetter)) {
                continue;
            }
            if (STOP_WORDS.contains(token)) {
                continue;
            }
            results.add(token);
        }
        return results;
    }
}
