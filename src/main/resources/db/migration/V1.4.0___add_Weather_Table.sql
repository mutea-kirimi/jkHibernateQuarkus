Begin;

create table current_weather (
    temperature int4 NOT NULL,
    weather_code int4 NOT NULL,
    wind_speed int4 NOT NULL,
    wind_degree int4 NOT NULL,
    wind_dir varchar(10) NOT NULL,
    pressure int4 NOT NULL,
    precip int4 NOT NULL,
    humidity int4 NOT NULL,
    cloudcover int4 NOT NULL,
    feelslike int4 NOT NULL,
    uv_index int4 NOT NULL,

    town_name varchar(30) NOT NULL,
    country varchar(30) NOT NULL,
    region varchar(30) NOT NULL,
    timezone_id varchar(50) NOT NULL,
    utc_offset bigint NOT NULL,
    local_date_time timestamp without time zone NOT NULL,
    lat real NOT NULL,
    lon real NOT NULL,

    type varchar(10) NOT NULL,
    query varchar(64) NOT NULL,
    language varchar(10) NOT NULL,
    unit varchar(10) NOT NULL,

    updated_at timestamp without time zone default (now() at time zone 'utc'),
    id uuid PRIMARY KEY,
    town_id uuid,
    CONSTRAINT current_weather_fk FOREIGN KEY (town_id) REFERENCES towns (id) on DELETE CASCADE

);

CREATE TABLE current_weather_icons (
      weather_id                UUID NOT NULL,
      icon                      VARCHAR(255) NOT NULL,
      PRIMARY KEY (weather_id, icon),
      CONSTRAINT current_weather_icons_fk FOREIGN KEY (weather_id) REFERENCES current_weather(id) ON DELETE CASCADE
);

CREATE TABLE current_weather_descriptions (
       weather_id                UUID NOT NULL,
       description               VARCHAR(255) NOT NULL,
       PRIMARY KEY (weather_id, description),
       CONSTRAINT current_weather_descriptions_fk FOREIGN KEY (weather_id) REFERENCES current_weather(id) ON DELETE CASCADE
);

end;
