package notificacao.modelo;

import java.time.LocalDateTime;
import java.util.UUID;

/** Mensagem a ser entregue a um destinatário por um canal específico. */
public class Notificacao {
    private final String id;
    private final Destinatario destinatario;
    private final String titulo;
    private final String mensagem;
    private final TipoCanal canal;
    private final LocalDateTime criadaEm;
    private StatusNotificacao status;
    private int tentativas;

    public Notificacao(Destinatario destinatario, String titulo, String mensagem, TipoCanal canal) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.destinatario = destinatario;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.canal = canal;
        this.criadaEm = LocalDateTime.now();
        this.status = StatusNotificacao.PENDENTE;
        this.tentativas = 0;
    }

    public void incrementarTentativa() { tentativas++; }
    public void marcarComoEnviada() { status = StatusNotificacao.ENVIADA; }
    public void marcarComoFalha() { status = StatusNotificacao.FALHOU; }

    public String getId() { return id; }
    public Destinatario getDestinatario() { return destinatario; }
    public String getTitulo() { return titulo; }
    public String getMensagem() { return mensagem; }
    public TipoCanal getCanal() { return canal; }
    public LocalDateTime getCriadaEm() { return criadaEm; }
    public StatusNotificacao getStatus() { return status; }
    public int getTentativas() { return tentativas; }

    @Override
    public String toString() {
        return String.format("[%s] %-5s -> %-6s | %-7s | tentativas=%d | \"%s\"",
                id, canal, destinatario.getNome(), status, tentativas, titulo);
    }
}
