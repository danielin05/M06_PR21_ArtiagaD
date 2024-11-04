# Plantilla projecte Java amb Maven DAM2-MP06 #

## Arrencada ràpida ##
Execució ràpida dels diferents exemples i resolusions de problemes

## Windows ##
```bash
.\run.ps1 cat.iesesteveterradas.Main
```

## Linux ##
```bash
run.sh  cat.iesesteveterradas.Main
```

## Compilació i funcionament ##

### Execució senzilla ###

#### Windows ####
```bash
.\run.ps1 <com.project.Main> <param1> <param2> <param3>
run.sh <com.project.Main> <param1> <param2> <param3>
```
#### Linux ####
```bash
.\run.ps1 <com.project.Main> <param1> <param2> <param3>
run.sh <com.project.Main> <param1> <param2> <param3>
```

On:
* <com.project.Main>: és la classe principal que vols executar.
* \<param1>, \<param2>, \<param3>: són els paràmetres que necessites passar a la teva aplicació.


### Execució pas a pas ###

Si prefereixes executar el projecte pas a pas, pots seguir les següents instruccions:

Neteja el projecte per eliminar fitxers anteriors:
```bash
mvn clean
```

Compila el projecte:
```bash
mvn compile test
```

Executa la classe principal:
```bash
mvn exec:java -q -Dexec.mainClass="<com.project.Main>" <param1> <param2> <param3>
```

On:
* <com.project.Main>: és la classe principal que vols executar.
* \<param1>, \<param2>, \<param3>: són els paràmetres que necessites passar a la teva aplicació.


## Prompts IA ##

Aquí teniu un prompt que guia a ChatGPT o Bard per produir millor codi
```
Si us plau, proporciona'm un codi que resolgui la tasca que especificaré a continuació seguint els principis de SOLID i les millors pràctiques de Clean Code i les millors pràctiques professionals en el sector tecnològic en general.

Tingues en compte el següent:

Especificitat: Com més específic siguis amb el que estàs cercant, més fàcil serà per a mi proporcionar una resposta que s'ajusti a les teves necessitats.

Complexitat: Adherir-se estrictament a tots els principis de disseny i bones pràctiques pot resultar en solucions més extenses o complexes, depenent del problema en qüestió. És important equilibrar la necessitat de seguir aquestes pràctiques amb la simplicitat i llegibilitat del codi, especialment en exemples més petits.

Contingut del codi: Alguns principis o pràctiques poden ser més rellevants segons el context. Per exemple, si em demanes un petit fragment de codi, és possible que no tots els principis SOLID siguin aplicables. En aquests casos, em centraré en els aspectes més pertinents de les bones pràctiques.
```