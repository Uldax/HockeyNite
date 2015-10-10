# HockeyNite
UDP server to follow hockey games

# Requis
Log4j V2.X : http://logging.apache.org/log4j/2.x/download.html

# Installation
Si vous utilisez Eclipse (https://eclipse.org/downloads/)

## Serveur
Creer un nouveau projet java dans votre workspace (l'emplacement par défaut ou celui de votre choix)
Aller ensuite dans les propriétés du projet > Java Build Path > Source
"Link Source" et choisez le dossier "HockeyNite\Serveur"
Onglet "Libraries" > "Add External JARs" et ajouter les fichiers de l'archive de log4j :
- log4j-1.2-api-2.4.jar
- log4j-api-2.4.jar
- log4j-core-2.4.jar

### TODO LIST
- [x] Service de match via UDP
- [x] Implémenter une pool de thread
- [ ] S'assurer de la bonne reception des messages
- [ ] Service de pari avec TCP
- [ ] Thread de mise a jour des données de match
- [ ] Thread de mise a jour du temps de match
- [ ] Résolution du problème lecteur/ecrivain sur le DAO

## Client
Reproduiser la même procedure que précédament
(Nouveau projet, propriétés)
"Link Source" et choisez le dossier "HockeyNite\Client"
Onglet "Libraries" > "Add External JARs" et ajouter les fichiers de l'archive de log4j :
- log4j-1.2-api-2.4.jar
- log4j-api-2.4.jar
- log4j-core-2.4.jar
Ainsi que Commun.jar


### TODO LIST
- [ ] Implémenter le client console
- [ ] Implémenter le client Android


