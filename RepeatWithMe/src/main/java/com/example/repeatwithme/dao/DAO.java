package com.example.repeatwithme.dao;
import com.example.repeatwithme.model.*;

import java.sql.*;
import java.util.ArrayList;


public class DAO {

    private String url;
    private  String root;
    private String password;

    public DAO(String url, String root, String password) {
        this.url = url;
        this.root = root;
        this.password = password;
        this.registerDriver();
    }

    public void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato !!!!!!!!!!");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public Connection openConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, root, password);
            if (conn != null) {
                System.out.println("IN");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                System.out.println("OUT");
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUser(User user){
        Connection conn = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                boolean result = st.execute("INSERT INTO user(email,username,password,role) VALUES('"+ user.getEmail()+"', '"+ user.getUsername()+"', '"+ user.getPassword()+"', '"+ user.getRole()+"')");
                st.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public User findUserByEmailPassword(String email, String password){
        System.out.println("in  findUserByEmailPassword");
        Connection conn = null;
        User user = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from user where  email like '"+email+"' and password like '"+password+"';");
                //System.out.println("pre if rsnext");
                if(rs.next()) {
                    //System.out.println("dentro la condizione rsnext");
                    user = new User(rs.getString("idUser"), rs.getString("email"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
                    //System.out.println("asbdibka "+ rs.getString("idUser") +" sabodosa");
                }
                //System.out.println("post if entra");
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return user;
        }
    }

    public ArrayList<Course> findCourseByTeacher(String nome, String cognome){
        Connection conn = null;
        ArrayList<Course> list = new ArrayList<>();
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from course join teaching on (course.idCourse like teaching.idCourse) join teacher on teaching.idTeacher like teaching.idTeacher where teacher.name like '"+ nome +"' and teacher.surname like '"+ cognome +"'");
                while(rs.next()){
                    list.add(new Course(rs.getString("idCourse"), rs.getString("title")));
                }
                rs.close();
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally{
            closeConnection(conn);
            return list;
        }
    }

    public ArrayList<Teacher> findTeacherByCourse(String titolo){
        Connection conn = null;
        ArrayList<Teacher> list = new ArrayList<>();
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from (select * from teacher where visible =1 )teacher join (select * from teaching where visible =1 ) teaching on (teacher.idTeacher like teaching.idTeacher) join course on (teaching.idCourse like course.idCourse) where course.title like '"+ titolo +"'");
                while(rs.next()){
                    list.add(new Teacher(rs.getString("name"), rs.getString("surname")));
                }
                rs.close();
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally{
            closeConnection(conn);
            return list;
        }
    }

    public ArrayList<Teacher> allTeachers(){
        Connection conn = null;
        ArrayList<Teacher> list = new ArrayList<>();
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from teacher where visible = 1");
                while(rs.next()){
                    list.add(new Teacher(rs.getString("name"), rs.getString("surname")));
                }
                rs.close();
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally{
            closeConnection(conn);
            return list;
        }
    }

    public ArrayList<Course> allCourses(){
        Connection conn = null;
        ArrayList<Course> list = new ArrayList<>();
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from course where visible = 1 ");
                while(rs.next()){
                    list.add(new Course(rs.getString("idCourse"), rs.getString("title")));
                }
                rs.close();
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally{
            closeConnection(conn);
            return list;
        }
    }


    /*Stampa le ripetizioni ancora disponibili*/
    public ArrayList<AvailableLesson> availableRepetition(){
        Connection conn = null;
        ArrayList<AvailableLesson> list=new ArrayList<>();;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select teacher.name , teacher.surname , course.title , lp.day  ,lp.hour \n" +
                        "                        from \n" +
                        "                        (select tutte.idteacher , tutte.idcourse , tutte.day, tutte.hour \n" +
                        "                        from ( \n" +
                        "                            select teaching.idteacher , teaching.idcourse , calendar.day, calendar.hour \n" +
                        "                        from (select * from teaching t where t.visible = 1 ) teaching join calendar) tutte \n" +
                        "                        where not exists ( select booking.idteacher , booking.idcourse , booking.dayCalendar, booking.hourCalendar \n" +
                        "                        from booking \n" +
                        "                        where booking.idteacher like tutte.idteacher and booking.idcourse like tutte.idcourse and booking.daycalendar like tutte.day and booking.hourcalendar like tutte.hour and  booking.state not like 'deleted' \n" +
                        "                        ) \n" +
                        "                        )lp join (select * from teacher t where t.visible = 1 ) teacher on (lp.idteacher like teacher.idteacher) join (select * from course t where t.visible = 1 ) course on (lp.idcourse like course.idcourse)");
                while(rs.next()){
                    list.add(new AvailableLesson(rs.getString("name"), rs.getString("surname"), rs.getString("title"), rs.getString("day"), rs.getString("hour")));
                }
                rs.close();
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return list;
        }

    }

    public ArrayList<AvailableLesson> findAvailableRepetitionsByCourseTeacher(String title, String nameTeacher, String surnameTeacher){
        Connection conn = null;
        ArrayList<AvailableLesson> list = new ArrayList<>();
        try{
            conn = openConnection();
            if(conn != null) {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select teacher.name , teacher.surname , course.title , lp.day  ,lp.hour  from ( select tutte.idteacher , tutte.idcourse , tutte.day, tutte.hour from ( select teaching.idteacher , teaching.idcourse , calendar.day, calendar.hour from teaching join calendar) tutte where not exists ( select booking.idteacher , booking.idcourse , booking.dayCalendar, booking.hourCalendar from booking where booking.idteacher like tutte.idteacher and booking.idcourse like tutte.idcourse and booking.daycalendar like tutte.day and booking.hourcalendar like tutte.hour and booking.state not like 'deleted') ) lp join teacher on (teacher.name like '"+nameTeacher+"' and teacher.surname like '"+surnameTeacher+"' and lp.idteacher like teacher.idteacher ) join course on (course.title like '"+title+"' and lp.idcourse like course.idcourse)");
                while (rs.next()) {
                    list.add(new AvailableLesson(rs.getString("name"), rs.getString("surname"), rs.getString("title"), rs.getString("day"), rs.getString("hour")));
                }
                rs.close();
                st.close();
            }
            } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return list;
        }
    }

    public void insertBooking(Booking b){
        /*
        * Controllare se esiste il teaching
        * Controllare se il docente è libero in quell'orario per un qualsiasi suo corso
        * */
        Connection conn = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                boolean result = st.execute("INSERT INTO booking(idUser, idTeacher,idCourse,dayCalendar,hourCalendar, state) VALUES('"+b.getIdUser()+"','"+b.getIdTeacher()+"','"+b.getIdCourse()+"','"+b.getDayCalender()+"','"+b.getHourCalender()+"', '"+b.getState()+"')");
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public void removeBooking(Booking b){
        Connection conn = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                boolean result = st.execute("DELETE FROM booking WHERE IDbooking LIKE '"+ b.getIdBooking() +"';");
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    /*Segna una ripetizione come 'effettuata' ma in modo generico può settare ogni tipo di stato*/
    public void changeStateBooking(Booking b , String state){
        System.out.println("Entro in changeStateBooking");
        Connection conn = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                boolean result = st.execute("update booking set state = '"+state+"' where booking.idBooking like '"+b.getIdBooking()+"';" );
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    /*stampa tutte le mie prenotazioni (utente)*/
    public ArrayList<MyOwnBooking> printMyOwnBookings(User u){
        Connection conn = null;
        ArrayList<MyOwnBooking> list = null;
        try{
            conn = openConnection();
            if(conn != null){
                System.out.println(u.toString());
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select teacher.name as name, teacher.surname as surname, course.title as title, booking.dayCalendar as day , booking.hourCalendar as hour, booking.state as state " +
                        "from booking join teacher on (booking.idTeacher like teacher.idTeacher) join course on (booking.idCourse like course.idCourse)  where idUser like '"+u.getIdUser()+"'");
                list = new ArrayList<>();
                while(rs.next()){
                    list.add(new MyOwnBooking(rs.getString("name"), rs.getString("surname"), rs.getString("title"), rs.getString("day"), rs.getString("hour"), rs.getString("state")));
                    //System.out.println(rs.getString("name") + " " + rs.getString("surname") + " " + rs.getString("title") + " " + rs.getString("day") + " " + rs.getString("hour") + " " + rs.getString("state"));
                }
                rs.close();
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return list;
        }
    }

    /*Stampa tutte le prenotazioni di tutti gli utenti (admin)*/
    public ArrayList<AdminAllBooking> printBookings(){
        Connection conn = null;
        ArrayList<AdminAllBooking> list = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select user.email as email,user.username as username, teacher.name as name, teacher.surname as surname, course.title as title, booking.dayCalendar as day , booking.hourCalendar as hour, booking.state as state " +
                        "from booking join teacher on (booking.idTeacher like teacher.idTeacher) join course on (booking.idCourse like course.idCourse) join user on (booking.idUser like user.idUser) ");
                list = new ArrayList<>();
                while (rs.next()){
                    list.add(new AdminAllBooking(rs.getString("email"),rs.getString("username"), rs.getString("name"), rs.getString("surname"), rs.getString("title"), rs.getString("day"), rs.getString("hour"), rs.getString("state")));
                    //System.out.println(rs.getString("idBooking") + " " + rs.getString("idUser") + " " + rs.getString("idTeacher") + " " + rs.getString("idCourse") + " " + rs.getString("dayCalendar") + " " + rs.getString("hourCalendar") + " " + rs.getString("state"));
                }
                rs.close();
                st.close();
            }
        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }finally {
            closeConnection(conn);
            return list;
        }
    }

    public void insertCourse(Course c){
        Connection conn = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                boolean result = st.execute("INSERT INTO course(title) VALUES('"+ c.getTitolo() +"')");
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public void removeCourse(Course c){
        Connection conn = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                boolean result = st.execute("update course set visible='0' WHERE title LIKE ('"+ c.getTitolo()+"')");
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }


    public void insertTeacher(Teacher t){
        Connection conn = null ;
        try{
            conn = openConnection();
            if (conn != null) {
                Statement st = conn.createStatement();
                boolean result = st.execute("insert into teacher (name, surname) values ('" + t.getName() + "' , '" + t.getSurname() + "'  ); ");
                st.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    public void  removeTheacher(Teacher t) {
        Connection conn = null;
        try {
            conn = openConnection();
            if (conn != null) {
                Statement st = conn.createStatement();
                boolean result = st.execute("update teacher set visible = '0' where name like'" + t.getName() + "' and surname like '" + t.getSurname() + "' ;");
                st.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }



    public Teacher findTeacherByName(Teacher t){
        Connection conn = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                System.out.println(t.getName() + " " + t.getSurname());
                ResultSet rs = st.executeQuery("SELECT teacher.* FROM teacher WHERE name LIKE '" + t.getName() + "' AND surname LIKE '" + t.getSurname() + "';");
                rs.next();
                t.setIdTeacher(rs.getString("idTeacher"));
                st.close();
                rs.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return t;
        }
    }

    public Course findCourseByTitle(Course c){
        System.out.println("Entro in findCourseByTitle()");
        Connection conn = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                System.out.println("Stampo i dati: " + c.getTitolo());
                ResultSet rs = st.executeQuery("SELECT course.* FROM course WHERE title LIKE '" + c.getTitolo() + "';");
                rs.next();
                c.setIdCourse(rs.getString("idCourse"));
                st.close();
                rs.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return c;
        }
    }

    /*trova la prenotazione per l'utente, la lezione prenotata e il suo stato*/
    public Booking findBookingByUserTeacherLessonStateDayHour(Booking b){
        System.out.println("entro nel metodo findBookingByUserTeacherLessonStateDayHour()");
        Connection conn = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                System.out.println("Stampo i dati: " + b.getIdUser() + " " + b.getIdTeacher() + " " + b.getIdCourse() + " " + b.getDayCalender() + " " + b.getHourCalender() + " "+ b.getState());
                ResultSet rs = st.executeQuery("SELECT booking.* FROM booking where idUser like '"+ b.getIdUser() +"' and idTeacher like '"+b.getIdTeacher()+"' and idCourse like '"+b.getIdCourse()+"'and dayCalendar like '"+b.getDayCalender()+"'and hourCalendar like '"+b.getHourCalender()+"' and state like '"+b.getState()+"'");
                rs.next();
                b.setIdBooking(rs.getString("idBooking"));
                st.close();
                rs.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return b;
        }
    }

    public void insertTeaching(Teaching t){
        Connection conn = null ;
        try{
            conn = openConnection();
            if (conn != null) {
                Statement st = conn.createStatement();
                boolean result = st.execute("INSERT INTO teaching VALUES('"+ t.getIdTeacher() +"', '"+ t.getIdCourse()+"', 1)");
                st.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    public void  removeTheaching(Teaching t) {
        Connection conn = null;
        try {
            conn = openConnection();
            if (conn != null) {
                Statement st = conn.createStatement();
                boolean result = st.execute("update teaching set visible = '0' where idTeacher like '" + t.getIdTeacher() + "' and idCourse like'" + t.getIdCourse() + "';");
                st.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public User findUserByEmail(String email){
        Connection conn = null;
        User user = null;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from user where  email like '"+email+"';");
                //System.out.println("pre if rsnext");
                if(rs.next()) {
                    //System.out.println("dentro la condizione rsnext");
                    user = new User(rs.getString("idUser"), rs.getString("email"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
                    //System.out.println("asbdibka "+ rs.getString("idUser") +" sabodosa");
                }
                //System.out.println("post if entra");
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return user;
        }
    }

    public boolean existsTeaching(String idTeacher, String idCourse){
        Connection conn = null;
        boolean exists = false;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from teaching where idTeacher like "+ idTeacher +" and idCourse like "+ idCourse +"");
                if(rs.next()){
                    exists = true;
                }
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }

    public boolean existsTeacher(Teacher t){
        Connection conn = null;
        boolean exists = false;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from teacher where name like '"+t.getName()+"' and surname like '"+t.getSurname()+"'");
                if(rs.next()){
                    exists = true;
                }
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }

    public boolean existsCourse(Course c){
        Connection conn = null;
        boolean exists = false;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from course where title like '"+c.getTitolo()+"'");
                if(rs.next()){
                    exists = true;
                }
                st.close();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }

    public boolean isTeacherFree(String idTeacher, String day, String hour){
        Connection conn = null;
        boolean exists = true;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from booking where idTeacher like "+ idTeacher + " and dayCalendar like '"+ day +"' and hourCalendar like '"+ hour +"' and booking.state not like 'deleted' ");
                if(rs.next()){
                    exists = false;
                }
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }

    public boolean isUserFree(String idUser, String day, String hour){
        Connection conn = null;
        boolean exists = true;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from booking where idUser like "+ idUser + " and dayCalendar like '"+ day +"' and hourCalendar like '"+ hour +"' and state like 'active'");
                if(rs.next()){
                    exists = false;
                }
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }

    public boolean isTeacherVisible( Teacher t) {
        Connection conn = null;
        boolean exists = false;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from teacher where name like '"+ t.getName() + "' and surname like '"+t.getSurname()+"' and visible = 1");
                if(rs.next()){
                    exists = true;
                }
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }

    public boolean ChangeTeacherVisible(Teacher t, int i) {
        Connection conn = null;
        boolean exists = true;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                 exists = st.execute("update teacher set visible ="+i+" where name like '"+ t.getName() + "' and surname like '"+t.getSurname()+"'");

                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }

    public boolean isCourseVisible(Course c) {
        Connection conn = null;
        boolean exists = false;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from course where title like '"+ c.getTitolo() + "' and visible = 1");
                if(rs.next()){
                    exists = true;
                }
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }

    public boolean ChangeCourseVisible(Course c, int i) {
        Connection conn = null;
        boolean exists = true;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                exists = st.execute("update course set visible ="+i+" where title like '"+c.getTitolo()+"'");
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }

    public boolean isTeachingVisible(String idTeacher, String idCourse) {
        Connection conn = null;
        boolean exists = false;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from teaching where idTeacher like '"+ idTeacher + "'and idCourse like '"+idCourse+"' and visible = 1");
                if(rs.next()){
                    exists = true;
                }
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }

    public boolean ChangeTeachingVisible(String idTeacher, String idCourse, int i) {
        Connection conn = null;
        boolean exists = true;
        try{
            conn = openConnection();
            if(conn != null){
                Statement st = conn.createStatement();
                exists = st.execute("update teaching set visible ="+i+" where idTeacher like '"+idTeacher+"' and idCourse like '"+idCourse+"'");
                st.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
            return exists;
        }
    }
}
