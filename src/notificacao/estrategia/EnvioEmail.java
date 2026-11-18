package notificacao.estrategia;

import notificacao.adapter.GatewayEmail;
import notificacao.modelo.Destinatario;
import notificacao.modelo.Notificacao;
import notificacao.modelo.ResultadoEnvio;
import notificacao.modelo.TipoCanal;

/** Estratégia concreta: e-mail aceita assunto e corpo HTML longo. */
public class EnvioEmail extends EstrategiaEnvio {
    private final GatewayEmail gateway;
    private final String remetente;

    public EnvioEmail(GatewayEmail gateway, String remetente) {
        this.gateway = gateway;
        this.remetente = remetente;
    }

    @Override
    public TipoCanal getCanal() { return TipoCanal.EMAIL; }

    @Override
    protected boolean validarDestinatario(Destinatario d) {
        return d.getEmail() != null && d.getEmail().matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$");
    }

    @Override
    protected ResultadoEnvio executarEnvio(Notificacao n) {
        String html = "<h1>" + n.getTitulo() + "</h1><p>Olá, " + n.getDestinatario().getNome()
                + "!</p><p>" + n.getMensagem() + "</p>";
        boolean ok = gateway.enviarEmail(remetente, n.getDestinatario().getEmail(), n.getTitulo(), html);
        return ok ? ResultadoEnvio.sucesso("EMAIL entregue para " + n.getDestinatario().getEmail())
                  : ResultadoEnvio.falha("EMAIL recusado pelo provedor");
    }
}
