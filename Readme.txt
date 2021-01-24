Proiect POO etapa 2

Nume: Olteanu Cristiana-Stefania
Seria: CD
Grupa: 322CD

===============================================================================
Entitati

    Action folder - contine fiecare actiune pe care o poate realiza
                    un distributor, consumator sau producator si clasa unde este
                    implementata simularea propriu zisa.
           *Action - contine implementarea simularii.

           *ConsumerActions - fiecare actiune realizabila de un consumator
           *DistributorActions - ficare actiune realizabila de un distributor.
           *ProducersActions - fiecare actiune realizabila de un distributor.

    Checker folder - contine checkerul temei

    Entities folder - contine un enum cu fiecare tip de energie al unui producator

    Input folder - contine clasele ce stocheaza informatiile cu privire la
                   producatori, distribuitori si consumatori, precum si clase
                   ce se ocupa de parsarea fisierelor JSON.

          *ConsumerData - toate informatiile cu privire la consumatori
          *DistributorData - toate informatiile cu privire la distributori
          *ProducerData - toate informatiile cu privire la producatori.
          *Entity - interfata pentru creerea entitatilor(consumator etc.)
          *EntityFactory - Factory Design pattern pentru entitati.
                         - Clasa va hotara ce tip se creeaza.
          *Input - Clasa ce contine toate informatiile despre entitati.
          *InputLoader - Clasa ce se ocupa cu parsarea fisierelor Json.

    Observer Folder - contine clasa si interfata necesara pentru
                      observer design pattern

             *change - interfata ce se ocupa de
             *EnergyChange - clasa intermediara drept Subiect care ține o structură de date cu
             energia oferită de fiecare productor.

    Output folder - contine clase ce se ocupa de scrierea fisierelor JSON.

           *Constants - Constante folosite dealungul programului
           *ContractData - Clasa ce contine informatii despre contractele unui producator
           *MonthlyStats - Clasa ce contine informatii despre lista lunara de distributori
                            a unui producator
           *Parsing - interfata necesara pentru FactoryDesignPattern
           *ParsingFactory - Factory ce va instantia fie un Writer, fie un InputLoader.
           *Writer - clasa ce se ocupa de scrierea de fisiere JSON.

    Strategies Folder - Contine diferitele strategii pe care le poate avea un distributor.

           *EnergyChoiceStrategyType - Enum cu diferite tipuri de strategii
           *GreenStrategy - strategie prin care un distributor va prioritiza la alegere
                            producatori ce ofera energie verde.
           *PriceStrategy -  strategie prin care un distributor va prioritiza la alegere
                             producatori ce ofera cea mai ieftina energie.
           *QuantityStrategy - strategie prin care un distributor va prioritiza la alegere
                               producatori ce ofera cel mai mare output de energie.
           *ProducerStrategy - interfata necesara pentru Strategy Command Pattern.
           *StrategyFactory - Factory Design Pattern pentru strategi.

    Updates Folder - Contine Diferite clase ce se ocupa cu updatarea entitatilor in fiecare luna.

           *DistributorChanges - contine informatii cu privire la schimbarea
                                 costului de infrastructura al unui distributor.
           *MonthlyUpdate - Contine metode ce updateaza informatiile entitatilor.
           *ProducerChanges - contine informatii cu privire la schimbarea
                              energiei per distributor al unui producator.
           *UpdateData - stocheaza informatii cu privire la update-urile lunare ale entitatilor.

    *Main - Punctul de pornire al programului.
    *Test - Clasa folosita de checker.
