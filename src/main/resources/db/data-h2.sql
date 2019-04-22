INSERT INTO statuses (id, status)
VALUES (0, 'planned'),
       (1, 'active'),
       (2, 'paused'),
       (3, 'finished');

INSERT INTO campaigns (name, status)
VALUES ('PEPSI', 0);

INSERT INTO campaigns (name, status, start_date)
VALUES ('COLA', 0, '2019-02-01 00:00:00'),
       ('Adidas', 1, '2019-01-01 13:00:00'),
       ('Shoes', 2, '2018-10-01 00:00:00');

INSERT INTO campaigns (name, status, start_date, end_date)
VALUES ('Social', 3, '2017-07-01 12:30:00', '2018-07-01 12:00:00');

INSERT INTO platforms (id, platform)
VALUES (0, 'web'),
       (1, 'android'),
       (2, 'ios');

INSERT INTO ads (name, asset_url, status, platforms)
VALUES ('ads_name1', 'http://url1.com', 0, (0)),
       ('ads_name2', 'http://url2.com', 1, (0)),
       ('ads_name3', 'http://url3.com', 2, (0)),
       ('ads_name4', 'http://url4.com', 3, (0)),
       ('ads_name5', 'http://url5.com', 0, (0, 1)),
       ('ads_name6', 'http://url6.com', 1, (1, 2)),
       ('ads_name7', 'http://url7.com', 2, (0, 2)),
       ('ads_name8', 'http://url8.com', 3, (0, 1, 2));

INSERT INTO CampaignsAds(campaignID, adsID)
VALUES (1, 1),
       (2, 2),
       (2, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (5, 8);