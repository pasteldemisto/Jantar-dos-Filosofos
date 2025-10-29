package entities;

public class Fork {

    // Garfo é apenas uma classe base para identificar por ID.
    private final int id;

    public Fork(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}