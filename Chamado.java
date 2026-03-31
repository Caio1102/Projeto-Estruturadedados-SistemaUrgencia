public class Chamado {
    private int id;
    private String bairro;
    private String descricao;
    private int nivelDeUrgencia;
    private String status;

    public Chamado(int id, String bairro, String descricao, int nivelDeUrgencia, String status) {
        this.id = id;
        this.bairro = bairro;
        this.descricao = descricao;
        this.nivelDeUrgencia = nivelDeUrgencia;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNivelDeUrgencia() {
        return nivelDeUrgencia;
    }

    public void setNivelDeUrgencia(int nivelDeUrgencia) {
        this.nivelDeUrgencia = nivelDeUrgencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Chamado [ID=" + id + ", Bairro=" + bairro + ", Descrição=" + descricao
                + ", Urgência=" + nivelDeUrgencia + ", Status=" + status + "]";
    }
}
