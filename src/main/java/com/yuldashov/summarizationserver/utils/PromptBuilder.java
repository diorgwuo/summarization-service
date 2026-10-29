package com.yuldashov.summarizationserver.utils;

public class PromptBuilder {
    public static String buildSystemPrompt() {
        return "Ты — ассистент планировщика задач. Составь краткий отчёт по итогам дня. " +
                "Опиши, что сделано, что осталось. Пиши по-русски и кратко.";
    }
}
