import java.io.File;

public class Connection {
    private File f1;
    private File f2;
   // private int hits;

    public Connection(File file1, File file2){
        f1 = file1;
        f2 = file2;
    }

    @Override
    public int hashCode(){
        return f1.hashCode() + f2.hashCode();
    }

    @Override
    public String toString(){
        return f1.getName() + ", " + f2.getName();
    }

    @Override
    public boolean equals(Object o){
        Connection c= (Connection) o;
        return (c.f1.equals(this.f1) && c.f2.equals(this.f2)) || (c.f1.equals(this.f2) && c.f2.equals(this.f1));
    }


}
