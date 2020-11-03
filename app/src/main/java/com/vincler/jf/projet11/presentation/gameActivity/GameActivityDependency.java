package com.vincler.jf.projet11.presentation.gameActivity;

import java.io.Serializable;

public class GameActivityDependency  implements Serializable {
    int gameId;
    String language;

    public GameActivityDependency(int gameId, String language) {
        this.gameId = gameId;
        this.language = language;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
