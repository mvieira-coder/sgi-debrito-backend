# SonarQube - primeira analise

SonarQube Community Build 26.10.0.133078; perfil Java Sonar way core.
Codigo analisado: src/main/java; testes: src/test/java. Relatorios gerados por ferramentas nao sao codigo da aplicacao e ficam fora desse escopo.

Commit de referencia: b778592d809cc1a3b8da5acc439893230d34a1ad

Resultado: 0 bugs, 6 code smells, 6 vulnerabilidades, 0 hotspots, 0% de duplicacao e 97,1% de cobertura.

## Problemas encontrados

| Regra | Arquivo | Linha | Explicacao |
|---|---|---:|---|
| java:S6813 | src/main/java/com/uniamerica/sgidebrito/controller/ClienteController.java | 17 | Injecao no campo: a dependencia fica implicita. Correcao prevista: construtor com @Autowired. |
| java:S4684 | src/main/java/com/uniamerica/sgidebrito/controller/ClienteController.java | 31 | Entidade JPA recebida no POST/PUT: alerta de atribuicao indevida de campos. A recomendacao e usar DTO, fora do escopo simplificado definido para esta atividade. |
| java:S4684 | src/main/java/com/uniamerica/sgidebrito/controller/ClienteController.java | 36 | Entidade JPA recebida no POST/PUT: alerta de atribuicao indevida de campos. A recomendacao e usar DTO, fora do escopo simplificado definido para esta atividade. |
| java:S6813 | src/main/java/com/uniamerica/sgidebrito/controller/FuncionarioController.java | 17 | Injecao no campo: a dependencia fica implicita. Correcao prevista: construtor com @Autowired. |
| java:S4684 | src/main/java/com/uniamerica/sgidebrito/controller/FuncionarioController.java | 31 | Entidade JPA recebida no POST/PUT: alerta de atribuicao indevida de campos. A recomendacao e usar DTO, fora do escopo simplificado definido para esta atividade. |
| java:S4684 | src/main/java/com/uniamerica/sgidebrito/controller/FuncionarioController.java | 36 | Entidade JPA recebida no POST/PUT: alerta de atribuicao indevida de campos. A recomendacao e usar DTO, fora do escopo simplificado definido para esta atividade. |
| java:S6813 | src/main/java/com/uniamerica/sgidebrito/controller/UsuarioController.java | 17 | Injecao no campo: a dependencia fica implicita. Correcao prevista: construtor com @Autowired. |
| java:S4684 | src/main/java/com/uniamerica/sgidebrito/controller/UsuarioController.java | 31 | Entidade JPA recebida no POST/PUT: alerta de atribuicao indevida de campos. A recomendacao e usar DTO, fora do escopo simplificado definido para esta atividade. |
| java:S4684 | src/main/java/com/uniamerica/sgidebrito/controller/UsuarioController.java | 36 | Entidade JPA recebida no POST/PUT: alerta de atribuicao indevida de campos. A recomendacao e usar DTO, fora do escopo simplificado definido para esta atividade. |
| java:S6813 | src/main/java/com/uniamerica/sgidebrito/service/ClienteService.java | 15 | Injecao no campo: a dependencia fica implicita. Correcao prevista: construtor com @Autowired. |
| java:S6813 | src/main/java/com/uniamerica/sgidebrito/service/FuncionarioService.java | 15 | Injecao no campo: a dependencia fica implicita. Correcao prevista: construtor com @Autowired. |
| java:S6813 | src/main/java/com/uniamerica/sgidebrito/service/UsuarioService.java | 15 | Injecao no campo: a dependencia fica implicita. Correcao prevista: construtor com @Autowired. |

A severidade e a classificacao sao as informadas pelo SonarQube. Os alertas nao equivalem a uma exploracao demonstrada. Os JSONs nesta pasta preservam os dados completos e o identificador da analise. Nenhum apontamento foi marcado como falso positivo ou suprimido.
