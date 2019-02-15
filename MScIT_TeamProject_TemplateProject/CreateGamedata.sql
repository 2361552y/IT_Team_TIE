CREATE TABLE GAMEDATA
(
GameId	int Not null,
NumberOfRounds	int,
WinnerId	int,
NumberOfDraws	int,
NumberOfPlayers	int,
Player1Wins	int,
Player2Wins	int,
Player3Wins	int,
Player4Wins	int,
Player5Wins	int,
PRIMARY KEY(GameId)
);

CREATE SEQUENCE gamedata_gameid_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
 
alter table gamedata alter column gameid set default nextval('gamedata_gameid_seq');