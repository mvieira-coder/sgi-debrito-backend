# SonarQube - comparacao das duas analises

Servidor local: Community Build 26.10.0.133078. Perfil Java: Sonar way core. Mesmas pastas e regras nas duas execucoes: src/main/java e src/test/java. As dependencias e arquivos gerados de relatorios nao foram analisados como codigo da aplicacao.

| Metrica | Primeira | Segunda |
|---|---:|---:|
| Bugs | 0 | 0 |
| Code smells | 6 | 0 |
| Vulnerabilidades | 6 | 6 |
| Hotspots | 0 | 0 |
| Duplicacao (%) | 0.0 | 0.0 |
| Cobertura de linhas (%) | 97.1 | 97.6 |

## O que foi corrigido

Os seis apontamentos java:S6813 foram corrigidos nos tres controllers e nos tres services. Antes, @Autowired estava diretamente no campo. Agora a dependencia e recebida no construtor e guardada em um campo final. @Autowired foi mantido no construtor. Nao houve mudanca nos endpoints ou nas regras do CRUD.

Apos a correcao, os 46 testes passaram: 30 unitarios, 15 de integracao e 1 de inicializacao. O JaCoCo mediu 80 de 82 linhas cobertas (97,6%). A mudanca no percentual tambem reflete as linhas adicionadas nos construtores; nao significa que novos cenarios foram adicionados.

## O que permanece

Permanecem seis alertas java:S4684, nos parametros de entidade dos metodos POST e PUT de cada controller. O Sonar recomenda objetos DTO para limitar os campos recebidos. A atividade foi implementada com entidades diretamente nos CRUDs, conforme o escopo simplificado definido. Nenhum DTO, autenticacao, JWT ou permissao de backend foi adicionado.

Esses alertas NAO foram corrigidos, suprimidos ou marcados como falsos positivos. O POST descarta o ID fornecido e o PUT copia os campos explicitamente, mas isso nao elimina o apontamento estatico. O papel continua sendo um campo comum do CRUD; este projeto nao deve ser apresentado como autenticacao real.

## Quality Gate e evidencias

A segunda analise recebeu Quality Gate OK para o codigo novo em relacao a versao 1-antes. Isso nao significa ausencia de vulnerabilidades no codigo total: as seis acima continuam abertas. O arquivo quality-gate.json preserva as condicoes avaliadas.

As pastas primeira/ e segunda/ contem metricas, problemas, hotspots, status do Quality Gate e o processamento com ID e horario das analises. A primeira evidencia foi commitada antes da correcao. O README de primeira/ lista os apontamentos por arquivo e linha.

Commit das correcoes analisadas: 40c846bde622ad88e39ee60557b6a0cbcc67fe1d

## Como repetir

Use o comando SonarScanner no README principal, depois de executar os testes. O token deve ficar na variavel de ambiente SONAR_TOKEN, nunca em arquivos versionados. As credenciais do servidor local nao fazem parte da entrega.
