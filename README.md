# HockeyNite
Serveur de suivi de match de hocket utilisant UDP + service de paris avec TCP

# Requis
Log4j V2.X : http://logging.apache.org/log4j/2.x/download.html

# Installation
Si vous utilisez Eclipse (https://eclipse.org/downloads/)

# Implementation
- [x] Protocole	de communication : UDP et TCP
- [x] Définition du format des messages => commun.jar
- 
## Serveur
Creer un nouveau projet java dans votre workspace (l'emplacement par défaut ou celui de votre choix)
Aller ensuite dans les propriétés du projet > Java Build Path > Source
"Link Source" et choisez le dossier "Serveur"
Pour log4j : 
Onglet "Libraries" > "Add External JARs" et ajouter les fichiers de l'archive de log4j :
- log4j-1.2-api-2.4.jar
- log4j-api-2.4.jar
- log4j-core-2.4.jar

### Serveur suivi de matchs
- [x] Service de match via UDP
- [x] Thread-per-request
- [x] Implémenter une pool de thread
- [x] Désérialisation	-	réception	des	requêtes
- [x] Sérialisation	-	envoi	des	réponses
- [ ] Mise-à-jour	aux	deux minutes cf client pour ne pas tuer la batterie
- [x] Mise-à-jour sur	demande	(update)
- [ ] Informations	disponibles	:	équipes,chronomètre,pointage,compteurs,pénalités

### Serveur paris
- [x] Service de pari avec TCP
- [ ] Implémentation de l'approche Thread-per-object
- [ ] Un	tampon	des	requêtes	par	objet	distant	-	ListeDesMatchs, Match, Paris
- [x] Les	paris	sont	acceptés en	1ère et	2e période
- [x] Les	paris	ne sont	pas	acceptés 3e	période
- [ ] Informer	les	parieurs	de	leur	gain	à	la	fin	du	match

### Objets distant
- [ ] ListeDesMatchs,temps,buts et pénalités	-	accès	thread-safe (syncronized + mutex) 
- [x] Thread de mise a jour des données de match (plus de données différentes nécessaire)
- [x] Thread de mise a jour du temps de match
- [x] Résolution du problème lecteur/ecrivain sur le DAO (en partie fait via semaphore / syncronized)
- [x] Match	:	tenir	le décompte	des	sommes	pariées

## Client
Reproduiser la même procedure que précédemment
(Nouveau projet, propriétés)
"Link Source" et choisez le dossier "Client"
Onglet "Libraries" > "Add External JARs" et ajouter les fichiers de l'archive de log4j :
- log4j-1.2-api-2.4.jar
- log4j-api-2.4.jar
- log4j-core-2.4.jar  

Ainsi que Commun.jar (important pour déserialization d'objet et du protocole de communication UDP)

### TODO LIST
- [x] Implémenter le client console
- [x] Affichage de la liste des matchs en cours
- [x] Affichage du details d'un match ( à pofiner )
- [ ] Rafraichissement automatique
- [x] Réemission des packets si non reception
- [x] Pari sur un match
- [ ] Implémenter le client Android
