package entities;

public class Philosopher implements Runnable{

    public final int id;
    public final String name;
    public final Fork leftFork;
    public final Fork rightFork;
    private int hunger = 0;

    public Philosopher(int id, String name, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void thinking() throws InterruptedException {
        System.out.printf("Filósofo %s está pensando. . .\n", this.name);
        // Thread.sleep() faz com que a thread pause por um tempo aleatório entre 0 e 2 segundos.
        Thread.sleep((int) (Math.random() * 2000));
    }

    private void eating() throws InterruptedException {
        System.out.printf("Filósofo %s está comendo. . .\n", this.name);
        this.hunger++;
        // Thread.sleep() faz com que a thread pause por um tempo aleatório entre 0 e 2 segundos.
        Thread.sleep((int) (Math.random() * 2000));
    }

    @Override
    public void run() {
        try{

            // While true pois os filósofos precisam esperar ter 2 garfos disponíveis para comer
            while(true){

                // Quando não está comendo, está pensando
                thinking();

                // O synchronized "tranca" o uso do garfo no caso de outra thread tentar utilizá-la.
                synchronized (leftFork){
                    System.out.printf("Filósofo %s pegou o garfo esquerdo %d com êxito!\n", this.name, this.leftFork.getId());

                    // Se ele consegue pegar um garfo, então ele tenta pegar outro garfo para comer.
                    // Se não conseguir pegar, ele volta pro while true para pensar e depois tentar pegar novamente.
                    synchronized (rightFork){
                        System.out.printf("Filósofo %s pegou o garfo direito %d com êxito!\n", this.name, this.rightFork.getId());
                        eating();
                        System.out.printf("Filósofo %s devolveu os garfos.\n", this.name);
                    }
                }

                // Aqui eu coloquei um limite de 5 vezes para ele comer, só pra não rodar infinitamente e poder analisar depois.
                if (hunger == 5){
                    System.out.printf("%s já comeu suficiente! *Saiu da mesa*\n", this.name);
                    return;
                }
            }

        // O catch aqui serve para no caso de outra thread utilizar uma interrupção forçada e lançar uma exceção na função.
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        }
    }
}
