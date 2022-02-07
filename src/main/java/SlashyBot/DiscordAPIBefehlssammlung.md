## Discord API Befehlssammlung

Hier sammle ich ein paar Befehle, Methodenaufrufe und Codeschnipsel um sie eventuell
später wiederzufinden

### Events
Events werden (zum jetztigen Wissensstand) immer ausgelöst, wenn eine Nachricht in einem 
Channel geschrieben wird, welcher der Bot auch lesen kann
Rankommen: durch `ListenerAdapter` (extenden) und seine Methode: 
```java
@Override
public void onMessageReceived(MessageReceivedEvent event){}
```
#### Welche Infos bekomm ich aus Events?
- `event.getMember()`
  - infos über den Verfasser der Nachricht, die das letzte Event ausgelöst hat
  - `event.getMember().getEffectiveName()`
    - gibt den Anzeigename des Verfassers als normalen String
  - `event.getMember().getAsMention()`
    - gibt den Verfasser als Ping
- `event.getAuthor()`
  - änlich wie get Member 
  - `event.getAutor().isBot()` : boolean
    - gibt an, ob es sich bei dem Verfasser um einen Bot handelt

### Channels
Der Channel (in der Regel ein TextChannel Objekt) ist der Channel in dem das Event ausgelöst wurde
Rankommen:
```java
TextChannel channel = event.getTextChannel();
```
#### Was machen mit einem Channel?
- `channel.sendMessage("MessageString").queue();`
  - schreibt eine Nachricht in diesen Channel

### Messaging
Eine Message wird in einem Channel geschrieben und so abgesendet 
```java
channel.sendMessage("MessageString").queue();
```
#### Modifier:
- `sendMessage("MessageString")`
    - `.complete().delete().queueAfter(x, TimeUnit.SECONDS);`
        - lässt die Nachicht nach x Sekunden wieder löschen

### Beispielblöcke:
```java
// Gibt die Uhrzeit aus und pingt den Verfasser
Calendar calendar = Calendar.getInstance();
SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
channel.sendMessage("Guck doch selba auf die Uhr " + event.getMember().getAsMention() + "!").queue();
channel.sendMessage("Aber weils dus bist " + date.format(calendar.getTime())).queue();
```

```java
// Lasst den Bot nicht auf sich selber antworten
if (event.getAuthor().isBot()){
    System.out.println("Der Bot soll sich nicht selber antworten");
}
```