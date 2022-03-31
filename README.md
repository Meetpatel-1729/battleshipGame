# battleshipGame
Battleship game which uses the mathematical strategy in a way which reduces shot number to sunk all ships

Here is the image for better understanding of the game:

![imagejdjsjds](https://user-images.githubusercontent.com/70450861/161148009-51a11122-7393-4e25-be86-65e375e3f459.png)


## Implementation
- A bot was already given as a part of the project which places the ships in a 10x10 board (100 locations) anywhere
- A bot places the ship of length 2,3,3,4,5 
- Program has a pre-build method which would return if you have hit the ship or not and also the ship is sunk or not
- The given default program uses the random class to generate random numbers to shoot until all bots are sunk
- In 10000 games it would take 90 shots on average to sink all those ships
- The first strategy was to come up with a mathematical solution that reduces the number of shots to sunk all the ships
- First wrote a program using parity which shots on alternate points such as (0,0), (0,2), and so on
- This reduces the time by 10% which was good compared to the random numbers but not enough
- So Added a new code in which it will shoot on the alternate places and if it hits the ship then it will hit all of his four points (Top, Bottom, Left, Right)
- To reduce more time created a loop if it hits in any of those four directions then it will go into that direction until it sunk the ship or it misses the shot
- This strategy reduced number of shots from 90 to 60 on average

## Next Steps
- Come up with more advanced logic such as it will hit one point and based on that hit/miss it will decide the next point to shoot
- Also, it keeps track of the length of the ship
- If the ship with 2 lengths are sunk already then there is no need to check alternate places
- Instead it will skip 2 locations at a time which will reduce the number of shots to sunk all those ships
- I would love to collaborate with someone who wants to work on this project and come up with more optimal solution
