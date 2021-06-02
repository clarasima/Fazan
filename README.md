# Fazan
**Proiect: Fazan**
**Realizat de: Sima Clara (2A5)**

Codul a fost scris in java.

**Colectii**
Utilizatorii si jocurile au fost retinute in colectii: list(linked list) si map(hash-map).

A fost folosit **modelul Client-Server**
![image](https://user-images.githubusercontent.com/79227862/120462475-a75e6900-c3a3-11eb-85c4-7236250aa9a5.png)

1. Serverul

- ofera servicii de retea
- ruleaza la un port specific
- se descurca cu mai multi clienti care joaca mai multe jocuri in acelasi timp

2. Clientul

- initiaza conversatia cu serverul
- stie adresa IP si portul serverului
- sends requests and receive responses

Programarea concurenta a fost realizata prin **multi-threading**. Fiecare client avand un thread propriu.

Comunicarea a fost facuta printr-un **TCP**(Transport Control Protocol) prin Socket si ServerSocket.
![image](https://user-images.githubusercontent.com/79227862/120462207-5fd7dd00-c3a3-11eb-9253-f6032dab7c43.png)


Cuvintele limbii romane au fost retinute intr-un **trie** realizat dupa site-ul https://www.geeksforgeeks.org/trie-insert-and-search/. Acest mod de a retine cuvintele este eficient si din punctul de vedere al stocarii deoarece ocupa mult mai putina memorie, dar si al timpului pentru cautare al cuvintelor.

**Despre joc:**

- serverul asteapta clientii
- clientii intra si se inregistreaza cu un nume: "register nume"
- clientii se logheaza: "login nume"
- clientii vor sa inceapa un joc cu n persoane: "play n"
- clientii asteapta pana se alatura destule persoane ca sa se poata juca jocul cu n persoane
- daca intra un jucator dar el vrea sa joace un joc cu alt numar de persoane, asteapta si el pentru a juca jocul pe care si-l doreste
- cand se aduna n persoane jocul incepe: fiecare isi asteapta randul si trimit un cuvant care ori incepe cu o litera rand ori cu ulimele 2 cuvinte ale cuvantului anterior.
- cuvintele sunt cautate in trie pentru vedea daca exista apoi se verifica intr-un map daca nu a fost deja folosit in acelasi joc.
- dupa 5 greseli un jucator pierde
- dupa ce pierd n-1 jucatori, se anunta castigarul ca a castigat
- dupa ce termin jocul utilizatorii pot sa dea exit sau sa mai joace un joc
- daca un jucator nu se joaca si nu spune nimic timp de un minut acesta va fi scos cand va incerca sa mai spuna ceva
