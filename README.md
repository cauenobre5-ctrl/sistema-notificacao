# Sistema de Notificação — E-mail, SMS e Push

Prova de conceito (console, Java 17+) do trabalho de Arquitetura/Padrões de Projeto.

- **Padrão implementado:** Strategy (comportamental) — cada canal (E-mail, SMS, Push) é uma estratégia de envio intercambiável.
- **Padrão de apoio:** Adapter (estrutural) — `SendGridAdapter`, `TwilioAdapter` e `FirebaseAdapter` adaptam as APIs dos provedores externos (simulados) às interfaces `GatewayEmail`, `GatewaySms` e `GatewayPush`.

## Estrutura

```
src/notificacao/
├── Main.java                 # demonstração no console
├── modelo/                   # Notificacao, Destinatario, ResultadoEnvio, enums
├── estrategia/               # STRATEGY: EstrategiaEnvio + EnvioEmail/EnvioSms/EnvioPush
├── adapter/                  # ADAPTER: interfaces Gateway* + adapters
├── externo/                  # APIs de terceiros simuladas (Adaptees)
└── servico/                  # ServicoNotificacao (Context) + HistoricoNotificacoes
docs/                         # documentação (HTML -> PDF)
└── uml/                      # diagramas em PlantUML (.puml) e imagens geradas (.svg)
```

Para gerar as imagens dos diagramas: `java -jar plantuml.jar -charset UTF-8 -tsvg docs/uml/*.puml`

## Como executar

Windows (PowerShell):

```powershell
$files = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object FullName
javac -encoding UTF-8 -d out $files
java -cp out notificacao.Main
```

Linux/macOS:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out notificacao.Main
```
