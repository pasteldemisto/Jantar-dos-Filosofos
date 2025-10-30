import entities.*;

void main() {

    // Instância dois vetores: um de filósofos e um de garfos
    Philosopher[] philosophers = new Philosopher[5];
    Fork[] forks = new Fork[5];

    String[] names = {"Platão", "Aristóteles", "Sócrates", "Descarters", "Kant"};

    // Vão ser 5 garfos, cada garfo com ID de 0 a 4.
    for(int i = 0; i < philosophers.length; i++){
        forks[i] = new Fork(i);
    }

    // A ideia geral é que eu já vou atribuir dois garfos para todos os filósofos, no caso,
    // esquerdo e direito, mas que o uso do objeto compartilhado será "trancado" quando o outro
    // filósofo estiver usando.

    for(int i = 0; i < philosophers.length; i++){

        // Na primeira iteração, garfo esquerdo vai ser 0 e garfo direito vai ser 1
        // Na segunda iteração, garfo esquerdo vai ser 1 e garfo direito vai ser 2
        // Na terceira iteração, garfo esquerdo vai ser 2 e garfo direito vai ser 3
        // Por aí vai
        Fork left = forks[i];
        Fork right = forks[(i + 1) % philosophers.length];

        // Pra evitar deadlock, o último filósofo, ao invés de pegar o garfo esquerdo primeiro, vai pegar o direito.
        // Assim, eles não ficam presos no ciclo.
        if (i == philosophers.length-1){
            philosophers[i] = new Philosopher(i, names[i], right, left);
        }else{
            philosophers[i] = new Philosopher(i, names[i], left, right);
        }

        // Cria uma thread para cada filósofo, cada um com os seus respectivos while.
        // Como todos vão usar os mesmos 5 objetos garfos, o synchronized vai bloquear o uso enquanto um filósofo já estiver usando.
        // Lembrando que cada filósofo usa os dois garfo entre 0 e 2 segundos. Se eles pegam um garfo e não pegam o outro, eles esperam o segundo garfo.
        // É importante notar que a thread continua a rodar mesmo após sair do for, por causa do while de cada filósofo.
        new Thread(philosophers[i]).start();
    }

    System.out.println("Fim do for");
}
