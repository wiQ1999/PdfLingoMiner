### PDF Lingo Miner

## 1. Cel główny:
* Utworzenie aplikacji desktopowej do przeszukiwania plików PDF według wskazanej frazy.

## 2. Cele dodatkowe:
* Całościowe pokrycie testami jednostkowymi warstwy biznesowej.
* Udostępnienie rozszerzonego mechanizmu przeszukiwania plików PDF.
* Umożliwienie kopiowania lub eksportu wyszukanych plików PDF do wskazanej lokalizacji.
* Zastosowanie rozwiązań wielowątkowych w celu poprawy wydajności.
* Zastosowanie lokalnego modelu językowego do analizy zawartości PDF.

## 3. Wymagania funkcjonalne:
* Główne:
  * Użytkownik może wskazać lokalizację na dysku do przeszukiwania plików PDF.
  * Użytkownik może przeszukiwać pliki PDF według wpisanej frazy tekstowej.
  * Po sprawdzeniu zawartości pliku PDF wyświetlana jest ilości wystąpień szukanej frazy.
  * Użytkownik może otworzyć plik PDF z listy programu.
* Dodatkowe:
  * Użytkownik może wskazać dodatkowe lokalizacje na dysku do przeszukiwania plików PDF.
  * Użytkownik może przeszukiwać pliki PDF według frazy logicznej.
  * Użytkownik może skopiować lub eksportować wyszukane pliki PDF do wskazanej lokalizacji na dysku.
  * Mechanizm wyszukiwania zawartości plików PDF dodaje krótki opis dotyczący zawartości wyszukanego pliku.

## 4. Wymagania niefunkcjonalne:
* Aplikacja działa na systemie Windows 10 lub nowszym.
* Interfejs użytkownika jest przejrzysty, wszystkie funkcje programu są dostępne bezpośrednio na widoku głównym aplikacji.
* Przeszukiwanie plików PDF realizowane jest wielowątkowo w dopasowaniu do urządzenia użytkownika w celu zwiększenia wydajności.

## 5. Wyłączenia:
* Obsługa plików o innym formacie niż PDF.
* Obsługa plików PDF zabezpieczonych hasłem.
* Uwzględnianie grafik w procesie przeszukiwania plików PDF.
* Modyfikacja lub komentowanie plików PDF.
* Obsługa innych systemów niż Windows.
* Integracja z zewnętrznymi usługami online.

## 6. Ograniczenia:
* Oddanie podstawowej wersji projektu do 12.01.2025.
	
## 7. Technologia, narzędzia, standardy:
* Java w wersji 23, Google Java Style Guide.
* System kontroli wersji GIT, Conventional Commits.
