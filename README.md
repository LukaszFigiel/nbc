Projekt przygotowany do zbudowania i osadzenia na serwerze WildFly. 
Wymagane jest utworzenie DataSoiurce pod adresem java:jboss/datasources/InvestingProfileDS. SQL tworzący strukturę 
oraz zasilający profile inwestowania w plisu database.sql.
 
Założenie: profile sprzedaży można dodolnie modyfikować i dodawać - w tym celu wystawiono usługę REST.
Cała funkcjonalność wystawiona w WebService pod adresem: http://[host:port]/service/InvestmentFundCalculator/InvestmentFundCalculator?wsdl

Projekt podzielony na dwie części:
- domain - odpowiedzialna za domenę danych, komunikację z bazą
- service - logika biznesowa i usługi

Usługa InvestmentFundCalculator przyjmuje na wejściu kwotę do podziału, nazwę profilu inwestowania 
(jak wspomniano nie można je dowolnie dodawać, także nie została zapisana w formie stałych), listę wybranych funduszy inwestycyjnych, gdzie dla każdego podajemy:
- id 
- typ (zesłownikowane, zakładam, że nie zmienia się to zbyt często)
- nazwa
Usługa po rozdzieleniu kwot zwraca informację o:
- nieprzydzielonej kwocie
- dane funduszy rozszerzone o kwotę i przydział procentowy kwoty.