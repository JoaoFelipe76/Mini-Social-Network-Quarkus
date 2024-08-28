
                           TABLE FOLLOWERS (

                           id bigserial not null primary key,
                           user_id bigint not null references USERS(id),
                           follower_id bigint not null references USERS(id)


                           );