-------------作业1-----------
select age, avg(rate) as avgrate from (
(select userid, age from hive_sql_test1.t_user) a
left join
(select userid, rate from hive_sql_test1.t_rating where movieid = '2116') b
on a.userid = b.userid )

-------------作业2-----------
select t.sex, t.moviename, t.avgrate, t.num from
(select t1.sex sex, t3.moviename moviename, avg(t2.rate) avgrate, count(t2.rate) num from(
(select userid, sex from hive_sql_test1.t_user where sex = 'M') t1
left join
(select userid, movieid, rate from hive_sql_test1.t_rating) t2
on t1.userid = t2.userid
left join
(select movieid, moviename from hive_sql_test1.t_movie) t3
on t2.movieid = t3.movieid) group by t1.sex, t3.moviename) t where t.num >= 50 order by t.avgrate desc limit 10;

-------------作业3-----------
select 
f.MovieName,g.avg_rate
from
hive_sql_test1.t_movie f
join
(select 
movieid,avg(Rate) avg_rate
from
hive_sql_test1.t_rating
where movieid in
(select 
MovieID
from
(select 
MovieID,row_number()over(order by Rate desc)rm
from
hive_sql_test1.t_rating
where userid in
(select 
userid
from
(select 
userid,cnt,row_number()over(order by cnt desc) rm
from
(select 
a.userid,count(1) cnt
from
(select * from hive_sql_test1.t_user where Sex='F')a
left join
hive_sql_test1.t_rating b
on a.userid=b.userid
group by a.userid)c)d
where rm=1)
)e
where rm<=10)
group by movieid)g
on f.movieid=g.movieid