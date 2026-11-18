package notificacao.estrategia;

import notificacao.adapter.GatewaySms;
import notificacao.modelo.Destinatario;
import notificacao.modelo.Notificacao;
import notificacao.modelo.ResultadoEnvio;
import notificacao.modelo.TipoCanal;

/** Estratégia concreta: SMS é texto puro limitado a 160 caracteres. */
public class EnvioSms extends EstrategiaEnvio {
    public static final int LIMITE_CARACTERES = 160;
    private final GatewaySms gateway;

    public EnvioSms(GatewaySms gateway) {
        this.gateway = gateway;
    }

    @Override
    public TipoCanal getCanal() { return TipoCanal.SMS; }

    @Override
    protected boolean validarDestinatario(Destinatario d) {
        return d.getTelefone() != null && d.getTelefone().matches("^\\+\\d{12,13}$");
    }

    @Override
    protected ResultadoEnvio executarEnvio(Notificacao n) {
        String texto = n.getTitulo() + ": " + n.getMensagem();
        if (texto.length() > LIMITE_CARACTERES) {
            texto = texto.substring(0, LIMITE_CARACTERES - 3) + "...";
        }
        boolean ok = gateway.enviarSms(n.getDestinatario().getTelefone(), texto);
        return ok ? ResultadoEnvio.sucesso("SMS entregue para " + n.getDestinatario().getTelefone())
                  : ResultadoEnvio.falha("SMS falhou no provedor");
    }
}
