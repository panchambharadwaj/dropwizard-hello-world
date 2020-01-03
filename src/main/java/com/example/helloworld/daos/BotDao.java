package com.example.helloworld.daos;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface BotDao {

  @SqlQuery("select athlete_id from strava_telegram_bot where name = :name")
  int getAthleteIdByName(@Bind("name") String name);

}