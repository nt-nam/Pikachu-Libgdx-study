Build a simple Pikachu game

Mainly aimed at learning GDX related things

The game needs to complete the following elements:
- Game: connect animals, move tiles, progress, rewards
- Buffer includes: hints, shuffle, undo
- Setting: music, sound
- Features: change skins, buy buffer

├── GameMain.java           # Main game class (extends ApplicationAdapter)
├── screens/
│   ├── GameScreen.java      # Main gameplay screen
│   ├── MenuScreen.java      # Main menu screen
│   ├── SettingsScreen.java  # Settings screen (music, sound)
│   └── ShopScreen.java      # Shop screen for buying buffers/skins
├── model/
│   ├── Tile.java            # Represents a single tile (animal, position)
│   ├── Board.java           # Game board logic (tile grid, matching)
│   ├── Player.java          # Player data (score, currency, progress)
│   └── PathFinder.java      # Logic to find valid paths between tiles
├── utils/
│   ├── GameConstants.java   # Constants (tile size, screen dimensions)
│   ├── SoundManager.java    # Manages music and sound effects
│   └── SkinManager.java     # Handles skin loading and switching
└── ui/
    ├── ButtonFactory.java   # Creates UI buttons (hint, shuffle, undo)
    └── HUD.java            # Heads-up display (score, buffers, progress)