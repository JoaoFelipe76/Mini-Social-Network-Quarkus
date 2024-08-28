                       CREATE TABLE POSTS (
                       id BIGSERIAL NOT NULL PRIMARY KEY,
                       post_text VARCHAR(150) NOT NULL,
                       dateTime timestamp NOT NULL,
                       user_id bigint NOT NULL references USERS(id)
                       );
