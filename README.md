# HockeyNite
Serveur de suivi de match de hocket utilisant UDP + service de paris avec TCP

# Requis
Log4j V2.X : http://logging.apache.org/log4j/2.x/download.html

# Installation
Si vous utilisez Eclipse (https://eclipse.org/downloads/)

## Serveur
Creer un nouveau projet java dans votre workspace (l'emplacement par défaut ou celui de votre choix)
Aller ensuite dans les propriétés du projet > Java Build Path > Source
"Link Source" et choisez le dossier "Serveur"
Pour log4j : 
Onglet "Libraries" > "Add External JARs" et ajouter les fichiers de l'archive de log4j :
- log4j-1.2-api-2.4.jar
- log4j-api-2.4.jar
- log4j-core-2.4.jar

### TODO LIST
- [x] Service de match via UDP
- [x] Implémenter une pool de thread
- [ ] S'assurer de la bonne reception des messages
- [ ] Service de pari avec TCP
- [x] Thread de mise a jour des données de match (plus de données différentes nécessaire)
- [x] Thread de mise a jour du temps de match
- [x] Résolution du problème lecteur/ecrivain sur le DAO (en partie fait via semaphore / syncronized)

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
- [ ] Réemission des packets si non reception
- [ ] Implémenter le client Android
