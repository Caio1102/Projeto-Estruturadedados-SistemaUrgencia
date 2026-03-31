import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    static ArrayList<Chamado> ocorrencia = new ArrayList<>(50);
    static LinkedList<Chamado> historico = new LinkedList<>();
    static ArrayList<Chamado> ativos = new ArrayList<>(50);

    static Pilha pilha = new Pilha(30);
    static Fila fila = new Fila(30);

    public static void main(String[] args) {
        cadastrarChamadosIniciais();

        System.out.println("\n=== HISTÓRICO INICIAL ===");
        mostrarHistorico();

        System.out.println("\n=== ATENDIMENTO ===");
        atenderProximoChamado();
        atenderProximoChamado();
        atenderProximoChamado();
        atenderProximoChamado();
        atenderProximoChamado();

        System.out.println("\n=== ATIVOS ===");
        mostrarAtivos();

        System.out.println("\n=== FINALIZAÇÃO ===");
        finalizarAtendimento(10);
        finalizarAtendimento(6);
        finalizarAtendimento(3);

        System.out.println("\n=== HISTÓRICO APÓS FINALIZAÇÕES ===");
        mostrarHistorico();

        System.out.println("\n=== RELATÓRIOS ===");
        totalPorBairro();
        mediaUrgencia();
        chamadosPendentes();
        rankingBairros();
    }

    public static void cadastrarChamadosIniciais() {
        adicionarChamado(new Chamado(1, "Centro", "Lixo", 2, "ABERTO"));
        adicionarChamado(new Chamado(2, "Vila Nova", "Buraco", 3, "ABERTO"));
        adicionarChamado(new Chamado(3, "Industrial", "Incêndio", 5, "ABERTO"));
        adicionarChamado(new Chamado(4, "Centro", "Poste apagado", 2, "ABERTO"));
        adicionarChamado(new Chamado(5, "Alvorada", "Sinalização", 3, "ABERTO"));
        adicionarChamado(new Chamado(6, "São José", "Acidente", 5, "ABERTO"));
        adicionarChamado(new Chamado(7, "Centro", "Mato alto", 1, "ABERTO"));
        adicionarChamado(new Chamado(8, "Industrial", "Esgoto", 2, "ABERTO"));
        adicionarChamado(new Chamado(9, "Vila Nova", "Árvore caída", 3, "ABERTO"));
        adicionarChamado(new Chamado(10, "Centro", "Explosão", 4, "ABERTO"));
        adicionarChamado(new Chamado(11, "Alvorada", "Lixo", 2, "ABERTO"));
        adicionarChamado(new Chamado(12, "Centro", "Buraco", 3, "ABERTO"));
        adicionarChamado(new Chamado(13, "São José", "Mato alto", 1, "ABERTO"));
    }

    public static void adicionarChamado(Chamado c) {
        if (c == null) {
            System.out.println("Chamado inválido");
            return;
        }

        if (c.getId() <= 0) {
            System.out.println("ID inválido: " + c.getId());
            return;
        }

        if (idExiste(c.getId())) {
            System.out.println("O chamado '" + c.getDescricao() + "' não foi adicionado | ID duplicado: " + c.getId());
            return;
        }

        if (c.getBairro() == null || c.getBairro().trim().isEmpty()) {
            System.out.println("Bairro inválido");
            return;
        }

        if (c.getDescricao() == null || c.getDescricao().trim().isEmpty()) {
            System.out.println("Descrição inválida");
            return;
        }

        if (c.getNivelDeUrgencia() < 1 || c.getNivelDeUrgencia() > 5) {
            System.out.println("Urgência inválida");
            return;
        }

        if (!statusValido(c.getStatus())) {
            System.out.println("Status inválido");
            return;
        }

        if (c.getNivelDeUrgencia() >= 4 && pilha.isFull()) {
            System.out.println("Não foi possível adicionar o chamado urgente " + c.getId() + " | Pilha cheia");
            return;
        }

        if (c.getNivelDeUrgencia() < 4 && fila.isFull()) {
            System.out.println("Não foi possível adicionar o chamado comum " + c.getId() + " | Fila cheia");
            return;
        }

        ocorrencia.add(c);
        historico.add(c);

        if (c.getNivelDeUrgencia() >= 4) {
            pilha.push(c);
            System.out.println("Pilha: " + c.getId());
        } else {
            fila.enqueue(c);
            System.out.println("Fila: " + c.getId());
        }
    }

    public static boolean idExiste(int id) {
        for (Chamado c : ocorrencia) {
            if (c.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static boolean statusValido(String status) {
        return "ABERTO".equals(status)
                || "EM_ATENDIMENTO".equals(status)
                || "FINALIZADO".equals(status);
    }

    public static void atenderProximoChamado() {
        Chamado c;

        if (!pilha.isEmpty()) {
            c = pilha.pop();
            if (c != null) {
                c.setStatus("EM_ATENDIMENTO");
                atualizarHistorico(c.getId(), "EM_ATENDIMENTO");
                ativos.add(c);
                System.out.println("O chamado urgente " + c.getId() + " está sendo atendido");
            }
            return;
        }

        if (!fila.isEmpty()) {
            c = fila.dequeue();
            if (c != null) {
                c.setStatus("EM_ATENDIMENTO");
                atualizarHistorico(c.getId(), "EM_ATENDIMENTO");
                ativos.add(c);
                System.out.println("O chamado comum " + c.getId() + " está sendo atendido");
            }
            return;
        }

        System.out.println("Não há chamados para atendimento");
    }

    public static void finalizarAtendimento(int id) {
        for (int i = 0; i < ativos.size(); i++) {
            Chamado c = ativos.get(i);
            if (c.getId() == id) {
                c.setStatus("FINALIZADO");
                ativos.remove(i);
                atualizarHistorico(id, "FINALIZADO");
                System.out.println("Chamado " + id + " finalizado");
                return;
            }
        }

        System.out.println("Chamado " + id + " não está na lista de ativos");
    }

    public static void atualizarHistorico(int id, String status) {
        for (Chamado c : historico) {
            if (c.getId() == id) {
                c.setStatus(status);
                return;
            }
        }
    }

    public static void mostrarHistorico() {
        for (Chamado c : historico) {
            System.out.println(c.getId() + " | " + c.getBairro() + " | " + c.getDescricao() + " | " + c.getStatus());
        }
    }

    public static void mostrarAtivos() {
        if (ativos.isEmpty()) {
            System.out.println("Nenhum chamado em atendimento");
            return;
        }

        for (Chamado c : ativos) {
            System.out.println(c.getId() + " | " + c.getBairro() + " | " + c.getStatus());
        }
    }

    public static void totalPorBairro() {
        System.out.println("\nTotal por bairro:");

        ArrayList<String> bairros = new ArrayList<>();
        ArrayList<Integer> contagem = new ArrayList<>();

        for (Chamado c : historico) {
            String bairro = c.getBairro();
            int index = bairros.indexOf(bairro);

            if (index == -1) {
                bairros.add(bairro);
                contagem.add(1);
            } else {
                contagem.set(index, contagem.get(index) + 1);
            }
        }

        for (int i = 0; i < bairros.size(); i++) {
            System.out.println(bairros.get(i) + ": " + contagem.get(i));
        }
    }

    public static void mediaUrgencia() {
        if (historico.isEmpty()) {
            System.out.println("Média de urgência: 0.00");
            return;
        }

        double soma = 0;
        for (Chamado c : historico) {
            soma += c.getNivelDeUrgencia();
        }

        double media = soma / historico.size();
        System.out.printf("Média de urgência: %.2f%n", media);
    }

    public static void chamadosPendentes() {
        System.out.println("\nChamados pendentes:");

        boolean existePendente = false;
        for (Chamado c : historico) {
            if (!"FINALIZADO".equals(c.getStatus())) {
                System.out.println(c.getId() + " | " + c.getBairro() + " | " + c.getStatus());
                existePendente = true;
            }
        }

        if (!existePendente) {
            System.out.println("Não há chamados pendentes");
        }
    }

    public static void rankingBairros() {
        ArrayList<String> bairros = new ArrayList<>();
        ArrayList<Integer> contagem = new ArrayList<>();

        for (Chamado c : historico) {
            String bairro = c.getBairro();
            int index = bairros.indexOf(bairro);

            if (index == -1) {
                bairros.add(bairro);
                contagem.add(1);
            } else {
                contagem.set(index, contagem.get(index) + 1);
            }
        }

        for (int i = 0; i < contagem.size() - 1; i++) {
            int max = i;

            for (int j = i + 1; j < contagem.size(); j++) {
                if (contagem.get(j) > contagem.get(max)) {
                    max = j;
                }
            }

            int tempContagem = contagem.get(i);
            contagem.set(i, contagem.get(max));
            contagem.set(max, tempContagem);

            String tempBairro = bairros.get(i);
            bairros.set(i, bairros.get(max));
            bairros.set(max, tempBairro);
        }

        System.out.println("\nRanking de bairros:");
        for (int i = 0; i < bairros.size(); i++) {
            System.out.println((i + 1) + "º " + bairros.get(i) + " - " + contagem.get(i));
        }
    }
}
