CREATE DATABASE studyTrack ENCODING 'UTF8'  LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';

CREATE SEQUENCE IF NOT EXISTS studyid_seq
START WITH 1
INCREMENT BY 1
MINVALUE 1
NO MAXVALUE
CACHE 1;

CREATE TABLE public.studyScores
(
  sid integer NOT NULL DEFAULT nextval('studyid_seq'),
  timeTotal numeric,
  timeRecord numeric,
  timeScopeWeight numeric,
  finishedLevel numeric,
  finishedLevelTotal numeric,
  finishedScopeWeight numeric,
  CONSTRAINT spk PRIMARY KEY (sid)
);