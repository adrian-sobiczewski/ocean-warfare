**1. What technologies have you chosen to implement the backend? Why?**

I used Spring-Boot and Guava because I'm proficient in using them.
Spring-Boot gave me everything regarding web tier out of the box.

**2. How much time did you spend on this task? Could you please divide this number into a few most
     important areas?**
     
Whole task took me around 5 man days.
On 1st day I tried to figure out which methodology use to implement bussiness logic.
I decided to use DDD (Domain Driven Design) and my prototyping started.
It took me 3 days overall to build prototype on linguistic level so interaction between components
reassemble human language. 
Last 2 days was solely implementation - using TDD (Test Driven Development).

**3. How would you modify your application if the next feature to implement would be to allow players
  to place their ships on the gameboard? What new things need to be implemented to make it work?**
  
If Domain Model is 1:1 with reality its really not a problem to add new feature.
I would add another RestController regarding Board.
I would introduce another status: "GAME COMMENCING" to tell player that opponent not put all ships on board.
I think it will be all regardin bigger changes.

**4. Assume that we have to fetch game ID from the external application. What challenges and
potential problems do you see and how would you prepare your application to handle them
properly?**

In synchronizing game state between players.
I would need to implement something which reasamble lock in concurrent programming.
