package download.base;

public interface IResourceLoadKeeper {
    void start();
    void end();
    void tick(float perc);
}
