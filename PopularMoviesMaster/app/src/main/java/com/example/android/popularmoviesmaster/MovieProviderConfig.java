package com.example.android.popularmoviesmaster;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;


@SimpleSQLConfig(
        name = "MovieProvider",
        authority = "com.example.android.popularmoviesmaster",
        database = "movie.db",
        version = 2)

public class MovieProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}