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
