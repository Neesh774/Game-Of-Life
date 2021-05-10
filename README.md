# Game-Of-Life
GoL is a Discord Bot written with [JDA](https://github.com/DV8FromTheWorld/JDA) that lets you play [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway's_Game_of_Life), a classic simulation game with dead, and living cells.

## Features
### Random Grids
When you start a game, GoL will automatically create a random grid, guaranteed to create an interesting design.
### Varied Controls
GoL has multiple control options to improve the player's experience, including reactions and text commands.
### Simulataneous Games
Multiple people can play the game in the same channel, or different channels.
### Dead Grid Detection
GoL will automatically end your game if your grid dies, and no cells remain.
### Custom Prefixes
To prevent GoL from conflicting with other bots, admins can choose any prefix to preface GoL's commands. They can even be multiple letters.
### Presets
GoL has a couple of presets that start interesting designs. Players will soon be able to add presets using their current grid.
### Time Out
GoL will automatically end a game if not updated after 5 minutes. This time is configurable between the times of 1 minute and 15 minutes.
### Automatic Updates
There is a toggleable play feature that will automatically update your grid until it dies. Use it with ``p``, ``play``, or react with ⏯.
## Commands
### User
- ``golstart`` can be used to start a game if you aren't already in one.
- ``golstop`` OR ``s`` can be used to stop your active game at any time.
- ``golhelp`` provides some helpful info about the bot.
- ``golabout`` provides some helpful information about Conway's Game of Life.
- ``golping`` sends you a Pong!
- ``golplay`` OR ``p`` will toggle your grid's automatic update feature.
- ``n`` will go to your grid's next generation.
- ``r`` will refresh your grid.
- ``golpresets`` will give you a list of all available presets.
- ``golpreset [presetID]`` will replace your grid with a copy of that preset.

### Admin
- ``golsetprefix [prefix]`` can be used to change the prefix of the bot.
- ``golsize [size]`` can be used to change the size of the grids(Grids can only go up to size 14 because of limitations in Discord Embeds).
- ``goltimeout [time]`` can be used to change the time it takes for a game to time out, in minutes. It has to be in between 1 and 15 minutes.
