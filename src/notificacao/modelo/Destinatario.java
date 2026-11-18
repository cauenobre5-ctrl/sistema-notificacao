package notificacao.modelo;

import java.util.ArrayList;
import java.util.List;

/** Pessoa que recebe as notificações, com seus contatos e canais preferidos. */
public class Destinatario {
    private final String id;
    private final String nome;
    private final String email;
    private final String telefone;
    private final String deviceToken;
    private final List<TipoCanal> preferencias = new ArrayList<>();

    public Destinatario(String id, String nome, String email, String telefone, String deviceToken) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.deviceToken = deviceToken;
    }

    public void adicionarPreferencia(TipoCanal canal) {
        if (!preferencias.contains(canal)) {
            preferencias.add(canal);
        }
    }

    public List<TipoCanal> getPreferencias() { return List.copyOf(preferencias); }
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getDeviceToken() { return deviceToken; }
}
