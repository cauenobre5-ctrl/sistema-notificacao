package notificacao.estrategia;

import notificacao.adapter.GatewayPush;
import notificacao.modelo.Destinatario;
import notificacao.modelo.Notificacao;
import notificacao.modelo.ResultadoEnvio;
import notificacao.modelo.TipoCanal;

/** Estratégia concreta: push precisa de token do dispositivo e título curto. */
public class EnvioPush extends EstrategiaEnvio {
    public static final int LIMITE_TITULO = 50;
    private final GatewayPush gateway;

    public EnvioPush(GatewayPush gateway) {
        this.gateway = gateway;
    }

    @Override
    public TipoCanal getCanal() { return TipoCanal.PUSH; }

    @Override
    protected boolean validarDestinatario(Destinatario d) {
        return d.getDeviceToken() != null && !d.getDeviceToken().isBlank();
    }

    @Override
    protected ResultadoEnvio executarEnvio(Notificacao n) {
        String titulo = n.getTitulo().length() > LIMITE_TITULO
                ? n.getTitulo().substring(0, LIMITE_TITULO) : n.getTitulo();
        boolean ok = gateway.enviarPush(n.getDestinatario().getDeviceToken(), titulo, n.getMensagem());
        return ok ? ResultadoEnvio.sucesso("PUSH entregue ao dispositivo de " + n.getDestinatario().getNome())
                  : ResultadoEnvio.falha("PUSH rejeitado pelo provedor (token inválido ou expirado)");
    }
}
