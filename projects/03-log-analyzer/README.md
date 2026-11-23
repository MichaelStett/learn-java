# 03: Analizator logów / transakcji

**Etap 3: lambdy, interfejsy funkcyjne, Stream API vs LINQ.** Szczegóły w [PLAN.md](../../PLAN.md#etap-3-lambdy-interfejsy-funkcyjne-i-streams-kontra-linq-15-tygodnia).

## Wymagania
- Wczytywanie dużego CSV przez `Files.lines()` (pamiętaj o try-with-resources, bo strumień trzyma otwarty plik).
- Przykładowe dane trzymaj w `src/main/resources/sample-data/` lub wygeneruj je osobnym programem (np. 1 mln wierszy).
- Raporty: top N klientów, sumy per miesiąc (`Collectors.groupingBy` + `BigDecimal` w `reduce`), anomalie, histogram kodów HTTP.
- Wybór raportu: `Map<String, Function<Stream<Row>, Report>>`.
- Powiadomienia o wynikach przez wzorzec Listener (zamiast `event` z C#).

## Kryteria ukończenia
- [ ] Żadnej pętli `for` w logice raportów, tylko Stream API.
- [ ] Obsłużony problem checked exceptions w lambdach (np. `UncheckedIOException`).
- [ ] Testy raportów na małych, ręcznie przygotowanych danych.
