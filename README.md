# HockeyNite
UDP server to follow hockey games

# Requis
Log4j V2.X : http://logging.apache.org/log4j/2.x/download.html

# Installation
Si vous utilisez Eclipse (https://eclipse.org/downloads/)

## Serveur
Creer un nouveau projet java dans votre workspace (l'emplacement par défaut ou celui de votre choix)
Aller ensuite dans les propriétés du projet > Java Build Path > Source
"Link Source" et choisez le dossier "HockeyNite\Serveur\HockeyNite\src"
Onglet "Libraries" > "Add External JARs" et ajouter les fichiers de l'archive de log4j :
- log4j-1.2-api-2.4.jar
- log4j-api-2.4.jar
- log4j-core-2.4.jar

Exporter ensuite la classe protocle.message.java :
Clic droit sur le projet > Export > Java > Jar file
Selectionner la classe message.java dans protocle.

## Client
Reproduiser la même procedure que précédament
(Nouveau projet, propriétés)
"Link Source" et choisez le dossier "HockeyNite\HockeyNiteClient"
Onglet "Libraries" > "Add External JARs" et ajouter le fichier de la classe Message précédament généré.


# TODO LIST
- Implémenter pull de thread
- Implémenter le client
- Implémenter le client Android
- S'assurer de la bonne reception des messages
- etc ...
