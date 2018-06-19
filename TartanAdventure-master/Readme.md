
## Tartan Adventure Game
### Escape Rooms
The popularity of escape rooms continues to grow as events to promote collaboration, 
team building, and entertainment. What started in the physical world is starting to 
see similar popularity in the cyber world as many consumer software products, such as 
Amazon’s Alexa assistant begin include escape room games. Given these market trends, 
your company, Tartan Inc. sees an opportunity to enter in to this market space.

Assume that you and your team are working for Tartan Inc. Your company specializes 
in computer games and business is good. Tartan Inc. has recently acquired a startup, 
AdHoc Games, that was working on a new computer escape room game. Management wants to
improve and release AdHoc Game’s software, but there are concerns that the existing 
system is low quality and too rudimentary when compared to modern computer games. 
Before the game is made available it must be enhanced to be more compelling and 
thoroughly evaluated to ensure that it meets stated requirements and Tartan Inc.’s 
quality standards. Your team’s goal is to complete the system and to conduct this 
evaluation. The computer game market is very competitive and time is short so you 
must be purposeful and prudent with the decisions that you make.

### Learning Goals
We will use the Tartan Adventure game as a context for our project. Please note that purpose of this project is not to make you a game design or Java development expert, but rather the context is being used as a model problem for you to practice software testing and quality assurance/control techniques and methods presented in the course. Development is necessary to fully explore quality assurance and control techniques, but programming is not the main focus. There are many dimensions to this project; the major themes include the following:

* Learning how to evaluate the quality of an existing software system.
* Becoming proficient at refactoring software to be higher quality, easier to maintain, and more testable.
* Developing a plan to define and assess the quality goals for a software system.
* Applying principled verification techniques to detect defects and verify quality 
goals.

### Tartan Adventure Platform
The Tartan Adventure Platform software is a framework for defining new escape 
room style games. The system includes the capabilities to create new games with 
different types of action, places, and goals. AdHoc’s original idea was to simplify 
the definition of new games for customers. Ultimately, this will allow players to 
receive new games with minimal software updates. Unfortunately, at the time of 
acquisition, evaluation of these features was incomplete.

The game itself is inspired by a classic popular adventure game 
first implemented in the Emacs editor named Dunnet. Games created on the 
Tartan Adventure Platform involved players navigating to different rooms via 
text-based commands; along the way players can score points by achieving goals. 
Your company, Tartan Inc. believes this approach to gaming is compelling and wants 
to extend and expand the Tartan Adventure Platform to be more feature rich and 
compelling for game players. Your team is tasked with evaluating the 
newly acquired platform and then extending it to be more interesting.

## New Work
There is much to do to prepare the game for release to the public. 
The current state of the software is largely unknown and suspected of being of 
poor quality. Your team is tasked with evaluating, updating, and extending the game. 
Specifically, you must complete tasks listed below. 

### Refactor to support evaluation
The game’s design is very difficult to evaluate, let alone test effectively. 
One of your first tasks is to refactor the source code to make it easier to evaluate. 
There are various goals for this refactoring, but the primary aim is to better enable 
testing for functional correctness. In particular, senior engineers have noted the relative lack of any unit or integration level tests for the platform. Former AdHoc Inc. engineers claim the software “just works”; however, your team rightly believes the lack of testing is a significant risk. Therefore, you should reorganize the existing source code and create a set of unit and integration tests. Be sure to update existing design documentation justifying changes. The resulting test suites must achieve Tartan Inc.’s organizational standards for code coverage: 80% statement coverage, 75% branch coverage , and 90% mutation coverage . We also expect all functional and architectural requirements will be evaluated to ensure they are appropriately implemented. 

### Extend the game
The current game is functional, but not terribly interesting. Tartan Inc. 
has decided that you will extend the game to include new, compelling features. 
These features are described in some detail below and are the responsibility of 
your team to complete in a timely manner. As with the existing platform, all new 
features must be adequately tested in accordance with company standards.

#### 1: Load/Save a Game 
For this extension you should allow a player to periodically save the state of their game. The player should be able to completely save their game state and quit the game. Then, at a later time, the player should be able to reload their saved game and continue play as before. Note that the state of the saved game must be perfectly preserved to satisfy this requirement. A user should be able to save and re-load a game at any point during exploration. There should only be one saved game per player and the player should be given the opportunity to save their game when they quit. 

#### 2: Networked Game Play mode
Management feels the isolated command line interface is too antiquated for 
today’s networked world, so they would like you to convert the game to operate 
over the internet. All game elements and rules should remain the same. The end 
result of this feature should be that players are able to play the adventure game 
of chess over the internet as though they were physically at the same terminal. 
Note that operating on the internet changes the security threats facing the system; 
thus, management expects that this feature will be implemented in a secure way. They 
are especially concerned with a bad actor interfering with an ongoing game. That is, 
the integrity of a game and the personal information of a player must be protected at
 all costs.

#### 3: Simplify Game Configuration
Currently, configuring a new game requires that designers manually edit the 
game source code. This is a complex, error prone process that requires developers 
to intimately know the details of the game platform. You are to make it so that 
new game configurations can be installed without recompilation or editing of core 
Tartan Adventure source code. Designers should be able to specify games independent 
of the platform. When the core Tartan Adventure platform is launched it should scan 
for, and then automatically load specific games. Note that when a new game is loaded 
it still must be evaluated to detect configuration errors.
