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
│   ├── Animal.java          # Represents a single tile (animal, position)      v
│   ├── Board.java           # Game board logic (tile grid, matching)           v
│   ├── Player.java          # Player data (score, currency, progress)          v
│   └── PathFinder.java      # Logic to find valid paths between tiles          v
├── utils/
│   ├── GameConstants.java   # Constants (tile size, screen dimensions)         v
│   ├── SoundManager.java    # Manages music and sound effects                  v
│   └── SkinManager.java     # Handles skin loading and switching               v
└── ui/
    ├── ButtonFactory.java   # Creates UI buttons (hint, shuffle, undo)         v
    └── HUD.java             # Heads-up display (score, buffers, progress)      v


