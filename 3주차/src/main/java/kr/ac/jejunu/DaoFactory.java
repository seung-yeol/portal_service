package kr.ac.jejunu;

public class DaoFactory {
    public static DaoFactory INSTANCE;

    public static DaoFactory getInstance() {
        if (INSTANCE == null){
            INSTANCE = new DaoFactory();
        }
        return INSTANCE;
    }

    public UserDao getUserDao(){
        return new UserDao(getConnectionMaker());
    }

    private ConnectionMaker getConnectionMaker() {
        return new JejuConnectionMaker();
    }
}
