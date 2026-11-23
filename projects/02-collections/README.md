# 02: Generyczne kolekcje

**Etap 2: generyki i kolekcje.** Szczegóły w [PLAN.md](../../PLAN.md#etap-2-generyki-i-kolekcje-1-tydzień).

To moduł biblioteczny (`java-library`), czyli odpowiednik Class Library w .NET.

## Wymagania
- `LruCache<K, V>` w dwóch wersjach: na bazie `LinkedHashMap` (`removeEldestEntry`) oraz ręcznie (HashMap + lista dwukierunkowa).
- Generyczna kolejka priorytetowa implementująca `Iterable<T>` z własnym `Iterator<T>`.
- `static <T extends Comparable<? super T>> T max(Collection<? extends T> c)` z testem pokazującym, czemu wildcardy są potrzebne.
- W `01-todo-cli`: indeks `Map<String, Set<Task>>` po tagach, sortowanie `Comparator` po wielu polach.
  Aby użyć tego modułu w innym, dodaj tam `implementation(project(":collections"))`.

## Kryteria ukończenia
- [ ] Testy obu wersji `LruCache` przechodzą tym samym zestawem testów (`@ParameterizedTest` / klasa abstrakcyjna).
- [ ] Iterator rzuca `ConcurrentModificationException` przy modyfikacji w trakcie iteracji.
- [ ] Rozumiesz i potrafisz wyjaśnić regułę PECS.
