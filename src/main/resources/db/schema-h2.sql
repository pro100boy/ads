DROP TABLE IF EXISTS campaigns;
DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS platforms;
DROP TABLE IF EXISTS ads;
DROP TABLE IF EXISTS CampaignsAds;

CREATE TABLE statuses
(
    id     INT          NOT NULL PRIMARY KEY,
    status VARCHAR(255) NOT NULL
);

CREATE TABLE campaigns
(
    id         IDENTITY     NOT NULL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    status     INT          NOT NULL,
    start_date TIMESTAMP,
    end_date   TIMESTAMP
);

CREATE TABLE platforms
(
    id       INT          NOT NULL PRIMARY KEY,
    platform VARCHAR(255) NOT NULL
);

CREATE TABLE ads
(
    id         IDENTITY     NOT NULL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    status     INT          NOT NULL DEFAULT 0,
    platforms  ARRAY,
    asset_url  VARCHAR      NOT NULL
);

CREATE TABLE CampaignsAds
(
    campaignID INT NOT NULL,
    adsID      INT NOT NULL,
    FOREIGN KEY (campaignID) REFERENCES campaigns (id) ON DELETE CASCADE
);
