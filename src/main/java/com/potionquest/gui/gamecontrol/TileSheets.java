package com.potionquest.gui.gamecontrol;

import static com.potionquest.gui.gamecontrol.fetchTool.*;
import java.awt.image.BufferedImage;

public class TileSheets {

  //load tile sheets for game elements
  public static final String ITEMS_PATH = "/sword.png";
  public static BufferedImage gameItemsTileSheet;

  public static final String CHARACTERS_PATH = "/CharactersV3.png";
  public static BufferedImage charactersTileSheet;

  public static final String MAP_PATH = "/world01V2.png";
  public static BufferedImage worldMapTileSheet;

  public static final String HEARTS_PATH = "/heart.png";
  public static BufferedImage heartsTileSheet;

  public static final String ARROW_KEYS = "/controlsIcons/arrowsKeys48.png";
  public static BufferedImage arrowKeysTileSheet;

  public static final String B_KEYS = "/controlsIcons/bKey48.png";
  public static BufferedImage bKeysTileSheet;

  public static final String ENTER_KEYS = "/controlsIcons/enterKey48.png";
  public static BufferedImage enterKeysTileSheet;

  public static final String SPACE_KEYS = "/controlsIcons/spaceKey188.png";
  public static BufferedImage spaceKeysTileSheet;

  public static final String Z_KEYS = "/controlsIcons/zKey48.png";
  public static BufferedImage zKeysTileSheet;

  public static final String NPC_DOCTOR = "/npc/doctor60.png";
  public static BufferedImage npcDoctorTileSheet;

  public static final String NPC_HERMIT = "/npc/hermit60.png";
  public static BufferedImage npcHermitTileSheet;

  public static final String NPC_POTION_SELLER = "/npc/potionseller60.png";
  public static BufferedImage npcPotionSellerTileSheet;

  public static final String NPC_SISTER = "/npc/sister60.png";
  public static BufferedImage npcSisterTileSheet;

  public static final String NPC_TOWN_PERSON = "/npc/townperson60.png";
  public static BufferedImage npcTownPersonTileSheet;

  public static final String PLAYER_CHARACTER = "/player/character.png";
  public static BufferedImage playerCharacterTileSheet;

  public static final String PLAYER_CHARACTER_RESIZED = "/player/characterResized.png";
  public static BufferedImage playerCharacterResizedTileSheet;

  public static final String PLAYER_FIGHT = "/player/fight.png";
  public static BufferedImage playerFightTileSheet;

  public static void gameTileSheetInitialization() {
    //load items tile sheet
    gameItemsTileSheet = fetchImage(ITEMS_PATH);

    //load characters tile sheet
    charactersTileSheet = fetchImage(CHARACTERS_PATH);

    //load world map tile sheet
    heartsTileSheet = fetchImage(HEARTS_PATH);

    //load heart and coin tile sheet
    worldMapTileSheet = fetchImage(MAP_PATH);

    arrowKeysTileSheet = fetchImage(ARROW_KEYS);

    bKeysTileSheet = fetchImage(B_KEYS);

    enterKeysTileSheet = fetchImage(ENTER_KEYS);

    spaceKeysTileSheet = fetchImage(SPACE_KEYS);

    zKeysTileSheet = fetchImage(Z_KEYS);

    npcDoctorTileSheet = fetchImage(NPC_DOCTOR);

    npcHermitTileSheet = fetchImage(NPC_HERMIT);

    npcPotionSellerTileSheet = fetchImage(NPC_POTION_SELLER);

    npcSisterTileSheet = fetchImage(NPC_SISTER);

    npcTownPersonTileSheet = fetchImage(NPC_TOWN_PERSON);

    playerCharacterTileSheet = fetchImage(PLAYER_CHARACTER);

    playerCharacterResizedTileSheet = fetchImage(PLAYER_CHARACTER_RESIZED);

    playerFightTileSheet = fetchImage(PLAYER_FIGHT);
    //there are better ways to do this, just put all related pictures on one tile sheet
  }
}
