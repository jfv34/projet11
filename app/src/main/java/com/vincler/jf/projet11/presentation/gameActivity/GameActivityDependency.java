package com.vincler.jf.projet11.presentation.gameActivity;

import com.vincler.jf.projet11.models.LanguageEnum;

import java.io.Serializable;

public class GameActivityDependency  implements Serializable {
    int gameId;
    LanguageEnum language;

    public GameActivityDependency(int gameId, LanguageEnum language) {
        this.gameId = gameId;
        this.language = language;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
    }
}
