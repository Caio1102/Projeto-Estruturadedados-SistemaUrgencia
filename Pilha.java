public class Pilha {
    private Chamado[] criticos; // criticos vetor com objetos do tipo Chamado
    private int topo; // indice

    public Pilha(int capacidade) {
        this.criticos = new Chamado[capacidade];
        this.topo = -1;
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == criticos.length - 1;
    }

    // alteração no tratamento do primeiro if
    public void push(Chamado critico) {
        if (critico == null) {
            System.out.println("Chamado inválido");
            return;
        }
        if (isFull()) {
            System.out.println("Críticos está cheio");
            return;
        }
        criticos[++topo] = critico;
    }

    // remove do topo e retorna qual foi removido
    public Chamado pop() {
        if (isEmpty()) {
            System.out.println("Críticos está vazio");
            return null;
        }

        Chamado removido = criticos[topo];
        criticos[topo] = null;
        topo--;
        return removido;
    }

    public Chamado top() {
        if (isEmpty()) {
            System.out.println("Críticos está vazio");
            return null;
        }
        return criticos[topo];
    }

    // verificar o tamanho da pilha
    public int sizeElements() {
        return topo + 1;
    }
}
