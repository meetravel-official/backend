INSERT INTO `meetravel`.`user` (`USER_ID`, `PASSWORD`, `NAME`, `NICKNAME`, `BIRTH_DATE`, `GENDER`, `PHONE_NUMBER`, `PROFILE_IMAGE_URL`, `TRAVEL_FREQ`, `SCHEDULE_TYPE`, `PLANNING_TYPE`, `MBTI`, `HOBBY`, `INTRO`, `SOCIAL_TYPE`, `CREATE_DTM`, `CREATOR`, `UPDATE_DTM`, `UPDATER`) VALUES
('1@bot', '', '미트래블', '미트래블', '2024-10-02', '여성', '01000000000', 'https://cdn.meetravel.life/profiles/019249b8-dc4c-763c-8e77-0e7040d93eff', '안가요!', '빠듯하게', '즉흥적으로', 'ENFP', NULL, NULL, 'BOT', NOW(), '1@bot', NOW(), '1@bot');

INSERT INTO `meetravel`.`user_roles` (`USER_ID`, `ROLE_ID`, `ROLE`, `CREATE_DTM`, `CREATOR`, `UPDATE_DTM`, `UPDATER`) VALUES
('1@bot', 2, 'ADMIN', NOW(), '1@bot', NOW(), '1@bot');