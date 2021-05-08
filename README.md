# Game-Of-Life
GoL is a Discord Bot written with [JDA](https://github.com/DV8FromTheWorld/JDA) that lets yuo play [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway's_Game_of_Life), a classic simulation game with dead, and living cells.

## Features
### Random Grids
When you start a game, GoL will automatically create a random grid, guaranteed to create an interesting design.
### Varied Controls
GoL has multiple control options to improve the player's experience, including reactions and text commands.
### Simulataneous Games
Multiple people can play the game in the same channel, or different channels.
### Custom Prefixes
To prevent GoL from conflicting with other bots, admins can choose any prefix to preface Sokobot's commands. They can even be multiple letters.

## Commands
### User
- ``golstart`` can be used to start a game if you aren't already in one.
- ``golstop`` can be used to stop your active game at any time.
- ``golhelp`` provides some helpful info about the bot.
### Admin
- ``golsetprefix [prefix]`` can be used to change the prefix of the bot.
- ``golsize [size]`` can be used to change the size of the grids(Grids can only go up to size 15 because of limitations in Discord Embeds).
