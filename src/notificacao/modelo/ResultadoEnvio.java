package notificacao.modelo;

import java.time.LocalDateTime;

/** Resultado de uma tentativa de envio. */
public class ResultadoEnvio {
    private final boolean sucesso;
    private final boolean definitivo;
    private final String detalhe;
    private final LocalDateTime dataHora;

    private ResultadoEnvio(boolean sucesso, boolean definitivo, String detalhe) {
        this.sucesso = sucesso;
        this.definitivo = definitivo;
        this.detalhe = detalhe;
        this.dataHora = LocalDateTime.now();
    }

    public static ResultadoEnvio sucesso(String detalhe) { return new ResultadoEnvio(true, true, detalhe); }

    /** Falha temporária (ex.: provedor fora do ar) — vale tentar novamente. */
    public static ResultadoEnvio falha(String detalhe) { return new ResultadoEnvio(false, false, detalhe); }

    /** Falha definitiva (ex.: contato inválido) — não adianta tentar novamente. */
    public static ResultadoEnvio falhaDefinitiva(String detalhe) { return new ResultadoEnvio(false, true, detalhe); }

    public boolean isSucesso() { return sucesso; }
    public boolean isDefinitivo() { return definitivo; }
    public String getDetalhe() { return detalhe; }
    public LocalDateTime getDataHora() { return dataHora; }

    @Override
    public String toString() { return (sucesso ? "OK   " : "ERRO ") + detalhe; }
}
