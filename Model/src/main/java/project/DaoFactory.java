package project;

public abstract class DaoFactory<T> {
    public abstract Dao<T> getFileDao(final String filename);

    public abstract Dao<T> getDbDao(final String databasename);
}
